package com.employee.onboarding.userAuthentication.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.onboarding.userAuthentication.entity.Document;
import com.employee.onboarding.userAuthentication.entity.User;
import com.employee.onboarding.userAuthentication.pojoRequest.DocumentRequest;
import com.employee.onboarding.userAuthentication.pojoResponse.DocumentResponse;
import com.employee.onboarding.userAuthentication.repository.DocumentRepo;
import com.employee.onboarding.userAuthentication.repository.UserRepo;
import com.employee.onboarding.userAuthentication.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	private DocumentRepo documentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public DocumentResponse uploadDocument(DocumentRequest request) {
		User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Document document = new Document();
        document.setUser(user);
        document.setDocumentName(request.getDocumentName());
        document.setDocumentPath(request.getDocumentPath());
        document.setUploadedAt(LocalDateTime.now());
        Document savedDoc = documentRepo.save(document);
        return new DocumentResponse(savedDoc);
	}

	
	@Override
	public List<DocumentResponse> getDocumentsByUserId(Long userId) {
		User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Document> documents = documentRepo.findByUser(user);
        return documents.stream().map(DocumentResponse::new).collect(Collectors.toList());
	}

	@Override
	public DocumentResponse getDocumentById(Long documentId) {
		Document document = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return new DocumentResponse(document);
	}
	
}
