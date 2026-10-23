package com.audit.audit_document.application.port;

/*
 * Converts a filled Word document into a PDF.
 * The implementation lives in the infrastructure layer (LibreOffice today).
 */
public interface PdfConverter {

    byte[] convertDocxToPdf(byte[] docxContent);
}
