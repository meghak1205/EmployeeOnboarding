package com.employee.onboarding.userAuthentication.pojoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    private Long userId;
    private String documentName;
    private String documentPath;
}
