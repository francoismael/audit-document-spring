package com.audit.audit_document.infrastructure.pdf;

import com.audit.audit_document.application.port.PdfConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/*
 * Conversion with LibreOffice (headless): works on any system, but the layout
 * can differ a little from Word (fonts, images, justification).
 * Chosen with app.pdf.engine=libreoffice, or when the engine is not set.
 */
@Component
@ConditionalOnProperty(name = "app.pdf.engine", havingValue = "libreoffice", matchIfMissing = true)
public class LibreOfficePdfConverter implements PdfConverter {

    @Value("${app.pdf.soffice-path}")
    private String sofficePath;

    @Value("${app.pdf.profile-dir:storage/libreoffice-profile}")
    private String profileDirectory;

    @Value("${app.pdf.timeout-seconds:120}")
    private long timeoutSeconds;

    /*
     * LibreOffice cannot run two conversions at the same time with the same
     * profile, so conversions are processed one after the other.
     */
    @Override
    public synchronized byte[] convertDocxToPdf(byte[] docxContent) {

        return PdfConversion.run("LibreOffice", docxContent, timeoutSeconds, this::buildCommand);
    }

    private List<String> buildCommand(Path workDirectory) {

        Path profile =
                Paths.get(profileDirectory)
                        .toAbsolutePath()
                        .normalize();

        return Arrays.asList(
                sofficePath,
                "-env:UserInstallation=" + profile.toUri(),
                "--headless",
                "--norestore",
                "--nologo",
                "--nolockcheck",
                "--convert-to",
                "pdf",
                "--outdir",
                workDirectory.toString(),
                workDirectory.resolve("document.docx").toString());
    }
}
