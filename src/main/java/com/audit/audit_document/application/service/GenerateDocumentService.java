package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.GeneratedDocument;
import com.audit.audit_document.application.usecases.GenerateDocumentUseCase;
import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.DocumentTemplateRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GenerateDocumentService
        implements GenerateDocumentUseCase {

    private static final Pattern VARIABLE_PATTERN =
            Pattern.compile(
                    "\\{\\{\\s*([A-Za-z0-9_]+)\\s*\\}\\}"
            );

    private static final DateTimeFormatter DISPLAY_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter FILE_DATE =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTemplateRepository
            documentTemplateRepository;

    private final DocumentFieldRepository
            documentFieldRepository;

    private final DocumentGenereRepository
            documentGenereRepository;

    private final MissionRepository missionRepository;

    private final ObjectMapper objectMapper;

    private final DocumentStorage documentStorage;

    public GenerateDocumentService(
            DocumentTypeRepository documentTypeRepository,
            DocumentTemplateRepository documentTemplateRepository,
            DocumentFieldRepository documentFieldRepository,
            DocumentGenereRepository documentGenereRepository,
            MissionRepository missionRepository,
            ObjectMapper objectMapper,
            DocumentStorage documentStorage) {

        this.documentTypeRepository =
                documentTypeRepository;

        this.documentTemplateRepository =
                documentTemplateRepository;

        this.documentFieldRepository =
                documentFieldRepository;

        this.documentGenereRepository =
                documentGenereRepository;

        this.missionRepository =
                missionRepository;

        this.objectMapper =
                objectMapper;

        this.documentStorage =
                documentStorage;
    }

    @Override
    @Transactional
    public GeneratedDocument generate(
            Long documentTypeId,
            Map<String, Object> values,
            Long missionId) {

        DocumentType documentType =
                documentTypeRepository
                        .findById(documentTypeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        if (!Boolean.TRUE.equals(documentType.getActif())) {

            throw new IllegalArgumentException(
                    "Ce type de document est désactivé");
        }

        Mission mission =
                missionId == null
                        ? null
                        : missionRepository
                                .findById(missionId)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException(
                                                "Mission introuvable : "
                                                        + missionId));

        Map<String, Object> input =
                values != null
                        ? values
                        : Collections.<String, Object>emptyMap();

        DocumentTemplate template =
                documentTemplateRepository
                        .findActiveByDocumentTypeId(
                                documentTypeId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Aucun template actif pour ce type de document"));

        Map<String, String> replacements =
                buildReplacements(
                        documentFieldRepository
                                .findByDocumentTypeId(documentTypeId),
                        input);

        Path path =
                documentStorage.templatePath(template);

        if (!Files.exists(path)) {

            throw new RuntimeException(
                    "Le fichier template est introuvable : "
                            + template.getNomFichierOriginal());
        }

        try (InputStream inputStream =
                     Files.newInputStream(path);

             XWPFDocument document =
                     new XWPFDocument(inputStream);

             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            replaceInBody(document, replacements);

            for (XWPFHeader header : document.getHeaderList()) {

                replaceInBody(header, replacements);
            }

            for (XWPFFooter footer : document.getFooterList()) {

                replaceInBody(footer, replacements);
            }

            document.write(outputStream);

            byte[] content = outputStream.toByteArray();
            String fileName = buildFileName(documentType);

            DocumentGenere documentGenere =
                    saveHistory(
                            documentType,
                            mission,
                            template,
                            fileName,
                            content,
                            input);

            return new GeneratedDocument(
                    documentGenere.getId(),
                    fileName,
                    content);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur lors de la génération du document",
                    e);
        }
    }

    private DocumentGenere saveHistory(
            DocumentType documentType,
            Mission mission,
            DocumentTemplate template,
            String fileName,
            byte[] content,
            Map<String, Object> values) throws IOException {

        Path target = documentStorage.saveGenerated(content);

        DocumentGenere documentGenere = new DocumentGenere();

        documentGenere.setDocumentType(documentType);
        documentGenere.setMission(mission);
        documentGenere.setNomFichier(fileName);
        documentGenere.setCheminFichier(target.toString());
        documentGenere.setTaille((long) content.length);
        documentGenere.setValeurs(toJson(values));
        documentGenere.setTemplateVersion(template.getVersion());
        documentGenere.setDateGeneration(LocalDateTime.now());

        return documentGenereRepository.save(documentGenere);
    }

    private String toJson(Map<String, Object> values) {

        try {
            return objectMapper.writeValueAsString(values);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Impossible d'enregistrer les valeurs du document",
                    e);
        }
    }

    private Map<String, String> buildReplacements(
            List<DocumentField> fields,
            Map<String, Object> values) {

        Map<String, String> replacements =
                new HashMap<>();

        for (DocumentField field : fields) {

            String value =
                    formatValue(
                            field,
                            values.get(field.getNom()));

            if (Boolean.TRUE.equals(field.getObligatoire())
                    && value.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Le champ « "
                                + field.getLibelle()
                                + " » est obligatoire");
            }

            replacements.put(field.getNom(), value);
        }

        return replacements;
    }

    private String formatValue(
            DocumentField field,
            Object value) {

        if (value == null) {

            return "";
        }

        if (value instanceof Boolean) {

            return (Boolean) value ? "Oui" : "Non";
        }

        String text =
                String.valueOf(value)
                        .replace("\r\n", "\n");

        if ("DATE".equalsIgnoreCase(field.getType())
                && !text.isEmpty()) {

            try {

                return LocalDate.parse(text)
                        .format(DISPLAY_DATE);

            } catch (DateTimeParseException e) {

                return text;
            }
        }

        return text;
    }

    private void replaceInBody(
            IBody body,
            Map<String, String> replacements) {

        for (XWPFParagraph paragraph : body.getParagraphs()) {

            replaceInParagraph(paragraph, replacements);
        }

        for (XWPFTable table : body.getTables()) {

            for (XWPFTableRow row : table.getRows()) {

                for (XWPFTableCell cell : row.getTableCells()) {

                    replaceInBody(cell, replacements);
                }
            }
        }
    }

    /*
     * Word often splits a {{name}} variable across several runs.
     * The paragraph text is rebuilt, then only the runs holding
     * the variable are merged, so the formatting of the first
     * run is kept.
     */
    private void replaceInParagraph(
            XWPFParagraph paragraph,
            Map<String, String> replacements) {

        int searchFrom = 0;

        while (true) {

            List<XWPFRun> runs = paragraph.getRuns();
            List<Integer> runStarts = new ArrayList<>();
            StringBuilder text = new StringBuilder();

            for (XWPFRun run : runs) {

                runStarts.add(text.length());
                text.append(run.text());
            }

            Matcher matcher =
                    VARIABLE_PATTERN.matcher(text);

            if (!matcher.find(searchFrom)) {

                return;
            }

            int startRun =
                    runIndexAt(runStarts, matcher.start());

            int endRun =
                    runIndexAt(runStarts, matcher.end() - 1);

            int endRunEnd =
                    endRun + 1 < runStarts.size()
                            ? runStarts.get(endRun + 1)
                            : text.length();

            String value =
                    replacements.getOrDefault(
                            matcher.group(1),
                            "");

            String newText =
                    text.substring(runStarts.get(startRun), matcher.start())
                            + value
                            + text.substring(matcher.end(), endRunEnd);

            XWPFRun target = runs.get(startRun);

            for (int i = endRun; i > startRun; i--) {

                paragraph.removeRun(i);
            }

            writeText(target, newText);

            searchFrom = matcher.start() + value.length();
        }
    }

    private int runIndexAt(
            List<Integer> runStarts,
            int position) {

        int index = 0;

        for (int i = 0; i < runStarts.size(); i++) {

            if (runStarts.get(i) <= position) {

                index = i;
            }
        }

        return index;
    }

    private void writeText(
            XWPFRun run,
            String text) {

        CTR ctr = run.getCTR();

        while (ctr.sizeOfTArray() > 0) {
            ctr.removeT(0);
        }

        while (ctr.sizeOfTabArray() > 0) {
            ctr.removeTab(0);
        }

        while (ctr.sizeOfBrArray() > 0) {
            ctr.removeBr(0);
        }

        while (ctr.sizeOfCrArray() > 0) {
            ctr.removeCr(0);
        }

        String[] lines = text.split("\n", -1);

        for (int i = 0; i < lines.length; i++) {

            if (i > 0) {
                run.addBreak();
            }

            String[] parts = lines[i].split("\t", -1);

            for (int j = 0; j < parts.length; j++) {

                if (j > 0) {
                    run.addTab();
                }

                if (!parts[j].isEmpty()) {

                    run.setText(
                            parts[j],
                            ctr.sizeOfTArray());
                }
            }
        }
    }

    private String buildFileName(
            DocumentType documentType) {

        String baseName =
                documentType.getCode() != null
                        ? documentType.getCode()
                        : documentType.getNom();

        return baseName.replaceAll("[^A-Za-z0-9_-]", "_")
                + "_"
                + LocalDate.now().format(FILE_DATE)
                + ".docx";
    }
}
