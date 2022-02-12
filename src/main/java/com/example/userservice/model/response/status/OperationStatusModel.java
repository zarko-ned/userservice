package com.example.userservice.model.response.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatusModel {
    private String operationResult;
    private String operationName;
}
