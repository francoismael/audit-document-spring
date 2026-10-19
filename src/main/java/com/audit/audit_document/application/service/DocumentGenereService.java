package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.DocumentGenereResponse;
import com.audit.audit_document.application.dto.GeneratedDocument;
import com.audit.audit_document.application.usecases.DeleteDocumentGenereUseCase;
import com.audit.audit_document.application.usecases.DownloadDocumentGenereUseCase;
import com.audit.audit_document.application.usecases.GetDocumentGenereByIdUseCase;
import com.audit.audit_document.application.usecases.GetDocumentsGeneresUseCase;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentGenereService
        implements GetDocumentsGeneresUseCase,
                   GetDocumentGenereByIdUseCase,
                   DownloadDocumentGenereUseCase,
                   DeleteDocumentGenereUseCase {

    private final DocumentGenereRepository documentGenereRepository;

    private final DocumentGenereMapper mapper;

    private final DocumentStorage documentStorage;

    public DocumentGenereService(
            DocumentGenereRepository documentGenereRepository,
            DocumentGenereMapper mapper,
            DocumentStorage documentStorage) {

        this.documentGenereRepository = documentGenereRepository;
        this.mapper = mapper;
        this.documentStorage = documentStorage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentGenereResponse> getAll(
            Long documentTypeId,
            Long missionId) {

        List<DocumentGenere> documents;

        if (documentTypeId != null) {
            documents = documentGenereRepository.findByDocumentTypeId(documentTypeId);

        } else if (missionId != null) {
            documents = documentGenereRepository.findByMissionId(missionId);

        } else {
            documents = documentGenereRepository.findAll();
        }

        List<DocumentGenereResponse> responses = new ArrayList<>();

        for (DocumentGenere document : documents) {

            boolean missionMatches =
                    missionId == null
                            || (document.getMission() != null
                                    && missionId.equals(document.getMission().getId()));

            if (missionMatches) {
                responses.add(mapper.toResponse(document));
            }
        }

        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentGenereResponse getById(Long id) {

        return mapper.toResponse(findDocument(id));
    }

    @Override
    @Transactional(readOnly = true)
    public GeneratedDocument download(Long id) {

        DocumentGenere document = findDocument(id);

        Path path = documentStorage.generatedPath(document);

        if (!Files.exists(path)) {

            throw new RuntimeException(
                    "Le fichier du document est introuvable sur le serveur");
        }

        try {
            return new GeneratedDocument(
                    document.getId(),
                    document.getNomFichier(),
                    Files.readAllBytes(path));

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur lors de la lecture du document",
                    e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {

        DocumentGenere document = findDocument(id);

        documentGenereRepository.deleteById(id);

        documentStorage.deleteQuietly(documentStorage.generatedPath(document));
    }

    private DocumentGenere findDocument(Long id) {

        return documentGenereRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Document généré introuvable : " + id));
    }
}
