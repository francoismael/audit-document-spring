package com.audit.audit_document.infrastructure.pdf;

import com.audit.audit_document.application.port.PdfConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
public class LibreOfficePdfConverter implements PdfConverter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LibreOfficePdfConverter.class);

    private static final String CONVERSION_FAILED =
            "La conversion du document en PDF a échoué.";

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

        Path workDirectory = null;

        try {
            workDirectory = Files.createTempDirectory("audit-pdf-");

            Path input = workDirectory.resolve("document.docx");
            Files.write(input, docxContent);

            Process process =
                    new ProcessBuilder(buildCommand(input, workDirectory))
                            .redirectErrorStream(true)
                            .redirectOutput(workDirectory.resolve("soffice.log").toFile())
                            .start();

            if (!process.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {

                process.destroyForcibly();

                throw new RuntimeException(
                        "La conversion du document en PDF a pris trop de temps.");
            }

            Path output = workDirectory.resolve("document.pdf");

            if (process.exitValue() != 0 || !Files.exists(output)) {

                LOGGER.error(
                        "LibreOffice conversion failed (exit code {}): {}",
                        process.exitValue(),
                        readLog(workDirectory));

                throw new RuntimeException(CONVERSION_FAILED);
            }

            return Files.readAllBytes(output);

        } catch (IOException e) {

            throw new RuntimeException(CONVERSION_FAILED, e);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(CONVERSION_FAILED, e);

        } finally {

            deleteQuietly(workDirectory);
        }
    }

    private List<String> buildCommand(
            Path input,
            Path outputDirectory) {

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
                outputDirectory.toString(),
                input.toString());
    }

    private String readLog(Path workDirectory) {

        try {
            return new String(
                    Files.readAllBytes(workDirectory.resolve("soffice.log")),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            return "(no log)";
        }
    }

    private void deleteQuietly(Path directory) {

        if (directory == null) {
            return;
        }

        try (Stream<Path> paths = Files.walk(directory)) {

            paths.sorted(Comparator.reverseOrder())
                    .forEach(path -> path.toFile().delete());

        } catch (IOException e) {
            LOGGER.warn("Could not delete temporary directory {}", directory);
        }
    }
}
