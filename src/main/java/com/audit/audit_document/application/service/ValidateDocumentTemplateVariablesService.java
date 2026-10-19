package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.DocumentTemplateValidationResponse;
import com.audit.audit_document.application.usecases.ValidateDocumentTemplateVariablesUseCase;
import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.domain.repository.DocumentTemplateRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateDocumentTemplateVariablesService
        implements ValidateDocumentTemplateVariablesUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTemplateRepository
            documentTemplateRepository;

    private final DocumentFieldRepository
            documentFieldRepository;

    private final DocumentStorage documentStorage;

    private static final Pattern VARIABLE_PATTERN =
            Pattern.compile(
                    "\\{\\{\\s*([A-Za-z0-9_]+)\\s*\\}\\}"
            );

    public ValidateDocumentTemplateVariablesService(
            DocumentTypeRepository documentTypeRepository,
            DocumentTemplateRepository documentTemplateRepository,
            DocumentFieldRepository documentFieldRepository,
            DocumentStorage documentStorage) {

        this.documentTypeRepository =
                documentTypeRepository;

        this.documentTemplateRepository =
                documentTemplateRepository;

        this.documentFieldRepository =
                documentFieldRepository;

        this.documentStorage =
                documentStorage;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentTemplateValidationResponse execute(
            Long documentTypeId) {

        if (documentTypeId == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du type de document est obligatoire");
        }

        /*
         * Check that the document type exists.
         */
        if (!documentTypeRepository
                .findById(documentTypeId)
                .isPresent()) {

            throw new ResourceNotFoundException(
                    "Type de document introuvable");
        }

        /*
         * Get the active template.
         */
        DocumentTemplate template =
                documentTemplateRepository
                        .findActiveByDocumentTypeId(
                                documentTypeId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Aucun template actif pour ce type de document"));

        /*
         * Get all configured fields.
         */
        List<DocumentField> fields =
                documentFieldRepository
                        .findByDocumentTypeId(
                                documentTypeId);

        fields.sort(
                Comparator.comparing(
                        DocumentField::getOrdre));

        /*
         * Extract variables from the DOCX.
         */
        Set<String> variables =
                extractVariablesFromDocx(
                        documentStorage.templatePath(template).toString());

        /*
         * Get field names from the database.
         */
        List<String> fieldsInDatabase =
                new ArrayList<>();

        for (DocumentField field : fields) {

            fieldsInDatabase.add(
                    field.getNom());
        }

        /*
         * Create a set for fast comparison.
         */
        Set<String> fieldNames =
                new HashSet<>(
                        fieldsInDatabase);

        /*
         * Fields configured in the database
         * but missing from the DOCX.
         */
        List<String> missingFields =
                new ArrayList<>();

        for (String fieldName : fieldsInDatabase) {

            if (!variables.contains(fieldName)) {

                missingFields.add(fieldName);
            }
        }

        /*
         * Variables present in the DOCX
         * but not configured in the database.
         */
        List<String> unknownVariables =
                new ArrayList<>();

        for (String variable : variables) {

            if (!fieldNames.contains(variable)) {

                unknownVariables.add(variable);
            }
        }

        /*
         * The configuration is valid only when
         * both lists are empty.
         */
        boolean valid =
                missingFields.isEmpty()
                        && unknownVariables.isEmpty();

        DocumentTemplateValidationResponse response =
                new DocumentTemplateValidationResponse();

        response.setDocumentTypeId(
                documentTypeId);

        response.setTemplateId(
                template.getId());

        response.setTemplateName(
                template.getNomFichierOriginal());

        response.setVariablesInTemplate(
                new ArrayList<>(variables));

        response.setFieldsInDatabase(
                fieldsInDatabase);

        response.setMissingFields(
                missingFields);

        response.setUnknownVariables(
                unknownVariables);

        response.setValid(valid);

        return response;
    }

    /*
     * ============================================================
     * EXTRACT VARIABLES FROM DOCX
     * ============================================================
     */

    private Set<String> extractVariablesFromDocx(
            String filePath) {

        Path path =
                Paths.get(filePath);

        if (!Files.exists(path)) {

            throw new RuntimeException(
                    "Le fichier template est introuvable : "
                            + path.getFileName());
        }

        Set<String> variables =
                new LinkedHashSet<>();

        try (InputStream inputStream =
                     Files.newInputStream(path);

             XWPFDocument document =
                     new XWPFDocument(inputStream)) {

            /*
             * Paragraphs outside tables.
             */
            for (XWPFParagraph paragraph
                    : document.getParagraphs()) {

                extractVariablesFromText(
                        paragraph.getText(),
                        variables);
            }

            /*
             * Tables.
             */
            for (XWPFTable table
                    : document.getTables()) {

                extractVariablesFromTable(
                        table,
                        variables);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur lors de la lecture du template DOCX",
                    e);
        }

        return variables;
    }

    /*
     * ============================================================
     * EXTRACT VARIABLES FROM TABLE
     * ============================================================
     */

    private void extractVariablesFromTable(
            XWPFTable table,
            Set<String> variables) {

        for (XWPFTableRow row
                : table.getRows()) {

            for (XWPFTableCell cell
                    : row.getTableCells()) {

                for (XWPFParagraph paragraph
                        : cell.getParagraphs()) {

                    extractVariablesFromText(
                            paragraph.getText(),
                            variables);
                }

                for (XWPFTable nestedTable
                        : cell.getTables()) {

                    extractVariablesFromTable(
                            nestedTable,
                            variables);
                }
            }
        }
    }


    private void extractVariablesFromText(
            String text,
            Set<String> variables) {

        if (text == null
                || text.isEmpty()) {

            return;
        }

        Matcher matcher =
                VARIABLE_PATTERN.matcher(text);

        while (matcher.find()) {

            String variable =
                    matcher.group(1);

            variables.add(variable);
        }
    }
}