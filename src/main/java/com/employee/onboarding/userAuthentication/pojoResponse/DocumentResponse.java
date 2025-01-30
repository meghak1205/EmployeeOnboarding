package com.employee.onboarding.userAuthentication.pojoResponse;

import java.time.LocalDateTime;

import com.employee.onboarding.userAuthentication.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DocumentResponse {
    private Long documentId;
    //private Long userId;
    private String documentName;
    private String documentPath;
    private LocalDateTime uploadedAt;
    
    public DocumentResponse(Document document) {
        this.documentId = document.getDocumentId();
        this.documentName = document.getDocumentName();
        this.documentPath = document.getDocumentPath();
        this.uploadedAt = document.getUploadedAt();
    }
}
