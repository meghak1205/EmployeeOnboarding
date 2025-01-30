package com.employee.onboarding.userAuthentication.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.onboarding.userAuthentication.entity.Document;
import com.employee.onboarding.userAuthentication.entity.User;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
    //List<Document> findByUserId(Long userId);
	  List<Document> findByUser(User user);
    
}
