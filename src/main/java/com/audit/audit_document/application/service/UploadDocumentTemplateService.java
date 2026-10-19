package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.DocumentTemplateResponse;
import com.audit.audit_document.application.usecases.UploadDocumentTemplateUseCase;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentTemplateRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UploadDocumentTemplateService
        implements UploadDocumentTemplateUseCase {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTemplateRepository templateRepository;

    @Value("${app.storage.templates-dir:storage/templates}")
    private String templatesDirectory;

    public UploadDocumentTemplateService(
            DocumentTypeRepository documentTypeRepository,
            DocumentTemplateRepository templateRepository) {

        this.documentTypeRepository =
                documentTypeRepository;

        this.templateRepository =
                templateRepository;
    }

    @Override
    @Transactional
    public DocumentTemplateResponse execute(
            Long documentTypeId,
            MultipartFile file) {

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "Le fichier template est obligatoire");
        }

        String originalFilename =
                file.getOriginalFilename();

        if (originalFilename == null
                || !originalFilename
                .toLowerCase()
                .endsWith(".docx")) {

            throw new IllegalArgumentException(
                    "Le template doit être un fichier .docx");
        }

        DocumentType documentType =
                documentTypeRepository
                        .findById(documentTypeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        if (!Boolean.TRUE.equals(
                documentType.getActif())) {

            throw new IllegalArgumentException(
                    "Le type de document est désactivé");
        }

        try {

            Path directory =
                    Paths.get(templatesDirectory)
                            .toAbsolutePath()
                            .normalize();

            Files.createDirectories(directory);

            String storedFilename =
                    UUID.randomUUID()
                            + ".docx";

            Path target =
                    directory.resolve(
                            storedFilename);

            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

            /*
             * Deactivate the previous active template.
             */
            templateRepository
                    .deactivateByDocumentTypeId(
                            documentTypeId);

            int nextVersion =
                    templateRepository
                            .findByDocumentTypeId(
                                    documentTypeId)
                            .stream()
                            .mapToInt(
                                    DocumentTemplate::getVersion)
                            .max()
                            .orElse(0)
                            + 1;

            DocumentTemplate template =
                    new DocumentTemplate();

            template.setDocumentType(
                    documentType);

            template.setNomFichierOriginal(
                    originalFilename);

            template.setNomFichierStockage(
                    storedFilename);

            template.setCheminFichier(
                    target.toString());

            template.setTypeMime(
                    file.getContentType());

            template.setTaille(
                    file.getSize());

            template.setVersion(
                    nextVersion);

            template.setActif(true);

            template.setDateUpload(
                    LocalDateTime.now());

            DocumentTemplate saved =
                    templateRepository.save(
                            template);

            return toResponse(saved);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur lors du stockage du template",
                    e);
        }
    }

    private DocumentTemplateResponse toResponse(
            DocumentTemplate template) {

        DocumentTemplateResponse response =
                new DocumentTemplateResponse();

        response.setId(
                template.getId());

        if (template.getDocumentType() != null) {

            response.setDocumentTypeId(
                    template.getDocumentType().getId());

            response.setDocumentTypeNom(
                    template.getDocumentType().getNom());
        }

        response.setNomFichierOriginal(
                template.getNomFichierOriginal());

        response.setNomFichierStockage(
                template.getNomFichierStockage());

        response.setCheminFichier(
                template.getCheminFichier());

        response.setTypeMime(
                template.getTypeMime());

        response.setTaille(
                template.getTaille());

        response.setVersion(
                template.getVersion());

        response.setActif(
                template.getActif());

        response.setDateUpload(
                template.getDateUpload());

        return response;
    }
}