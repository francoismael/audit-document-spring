package com.audit.audit_document.infrastructure.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * Runs an external program that turns document.docx into document.pdf,
 * in a temporary folder deleted afterwards. Shared by the conversion engines.
 */
final class PdfConversion {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PdfConversion.class);

    private static final String CONVERSION_FAILED =
            "La conversion du document en PDF a échoué.";

    private PdfConversion() {
    }

    // The command receives the temporary folder, which holds document.docx and must receive document.pdf.
    static byte[] run(
            String engine,
            byte[] docxContent,
            long timeoutSeconds,
            Function<Path, List<String>> command) {

        Path workDirectory = null;

        try {
            workDirectory = Files.createTempDirectory("audit-pdf-");

            Files.write(workDirectory.resolve("document.docx"), docxContent);

            Path log = workDirectory.resolve("conversion.log");

            Process process =
                    new ProcessBuilder(command.apply(workDirectory))
                            .redirectErrorStream(true)
                            .redirectOutput(log.toFile())
                            .start();

            if (!process.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {

                process.destroyForcibly();

                throw new RuntimeException(
                        "La conversion du document en PDF a pris trop de temps.");
            }

            Path output = workDirectory.resolve("document.pdf");

            if (process.exitValue() != 0 || !Files.exists(output)) {

                LOGGER.error(
                        "{} conversion failed (exit code {}): {}",
                        engine,
                        process.exitValue(),
                        readLog(log));

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

    private static String readLog(Path log) {

        try {
            return new String(Files.readAllBytes(log), StandardCharsets.UTF_8);

        } catch (IOException e) {
            return "(no log)";
        }
    }

    private static void deleteQuietly(Path directory) {

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
