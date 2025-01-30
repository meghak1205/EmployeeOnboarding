package com.employee.onboarding.userAuthentication.service;

import java.util.List;
import com.employee.onboarding.userAuthentication.pojoRequest.DocumentRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.DocumentResponse;

public interface DocumentService {
    
   public DocumentResponse uploadDocument(DocumentRequest documentRequest);
    
   public List<DocumentResponse> getDocumentsByUserId(Long userId);
    
   public DocumentResponse getDocumentById(Long documentId);
}

