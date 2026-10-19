package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentTemplateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadDocumentTemplateUseCase {

    DocumentTemplateResponse execute(
            Long documentTypeId,
            MultipartFile file);
}