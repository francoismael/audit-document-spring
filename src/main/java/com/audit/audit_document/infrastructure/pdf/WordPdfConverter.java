package com.audit.audit_document.infrastructure.pdf;

import com.audit.audit_document.application.port.PdfConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/*
 * Conversion with Microsoft Word, driven through PowerShell: the PDF is exactly the layout of Word.
 * Needs Windows and Word on the computer that runs the backend.
 * Chosen with app.pdf.engine=word.
 */
@Component
@ConditionalOnProperty(name = "app.pdf.engine", havingValue = "word")
public class WordPdfConverter implements PdfConverter {

    // wdExportFormatPDF; wdDoNotSaveChanges = 0.
    private static final String SCRIPT =
            "$ErrorActionPreference = 'Stop'%n"
                    + "$word = New-Object -ComObject Word.Application%n"
                    + "try {%n"
                    + "  $word.Visible = $false%n"
                    + "  $word.DisplayAlerts = 0%n"
                    + "  $document = $word.Documents.Open(%s, $false, $true, $false)%n"
                    + "  try { $document.ExportAsFixedFormat(%s, 17) } finally { $document.Close(0) }%n"
                    + "} finally {%n"
                    + "  $word.Quit()%n"
                    + "  [void][Runtime.InteropServices.Marshal]::ReleaseComObject($word)%n"
                    + "}%n";

    @Value("${app.pdf.powershell-path:powershell.exe}")
    private String powershellPath;

    @Value("${app.pdf.timeout-seconds:120}")
    private long timeoutSeconds;

    // One Word at a time: Word does not like being driven by several conversions at once.
    @Override
    public synchronized byte[] convertDocxToPdf(byte[] docxContent) {

        return PdfConversion.run("Word", docxContent, timeoutSeconds, this::buildCommand);
    }

    private List<String> buildCommand(Path workDirectory) {

        String script = String.format(
                SCRIPT,
                quote(workDirectory.resolve("document.docx")),
                quote(workDirectory.resolve("document.pdf")));

        // -EncodedCommand: the script and its paths are passed without any quoting problem.
        String encoded = Base64.getEncoder()
                .encodeToString(script.getBytes(StandardCharsets.UTF_16LE));

        return Arrays.asList(
                powershellPath,
                "-NoProfile",
                "-NonInteractive",
                "-ExecutionPolicy",
                "Bypass",
                "-EncodedCommand",
                encoded);
    }

    // A PowerShell string literal: 'C:\...\document.docx', quotes doubled.
    private static String quote(Path path) {

        return "'" + path.toAbsolutePath().toString().replace("'", "''") + "'";
    }
}
