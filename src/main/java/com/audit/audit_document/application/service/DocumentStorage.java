package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/*
 * Resolves stored files from the configured directories, so the
 * application keeps working when the project folder is moved.
 * The absolute path saved in the database is only a fallback.
 */
@Component
public class DocumentStorage {

    @Value("${app.storage.templates-dir:storage/templates}")
    private String templatesDirectory;

    @Value("${app.storage.generated-dir:storage/generated}")
    private String generatedDirectory;

    public Path templatePath(DocumentTemplate template) {

        return resolve(
                templatesDirectory,
                template.getNomFichierStockage(),
                template.getCheminFichier());
    }

    public Path generatedPath(DocumentGenere document) {

        return resolve(
                generatedDirectory,
                Paths.get(document.getCheminFichier()).getFileName().toString(),
                document.getCheminFichier());
    }

    public Path saveGenerated(byte[] content) throws IOException {

        Path directory = directory(generatedDirectory);

        Files.createDirectories(directory);

        Path target = directory.resolve(UUID.randomUUID() + ".docx");

        Files.write(target, content);

        return target;
    }

    public void deleteQuietly(Path path) {

        try {
            Files.deleteIfExists(path);

        } catch (IOException e) {
            // An orphan file must not block the deletion of its database record.
        }
    }

    private Path resolve(
            String configuredDirectory,
            String fileName,
            String storedPath) {

        Path candidate = directory(configuredDirectory).resolve(fileName);

        return Files.exists(candidate)
                ? candidate
                : Paths.get(storedPath);
    }

    private Path directory(String configuredDirectory) {

        return Paths.get(configuredDirectory)
                .toAbsolutePath()
                .normalize();
    }
}
