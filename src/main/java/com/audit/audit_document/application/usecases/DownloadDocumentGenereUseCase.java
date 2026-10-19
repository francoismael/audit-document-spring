package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.GeneratedDocument;

public interface DownloadDocumentGenereUseCase {

    GeneratedDocument download(Long id);
}
