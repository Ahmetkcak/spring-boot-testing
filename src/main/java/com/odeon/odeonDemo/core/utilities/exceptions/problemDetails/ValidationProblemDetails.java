package com.odeon.odeonDemo.core.utilities.exceptions.problemDetails;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationProblemDetails extends ProblemDetails{
    public ValidationProblemDetails(){
        setTitle("Validation Rule Violation");
        setDetail("Validation Problem");
        setType("https://odeon.com/validation-error");
        setStatus("400");
    }
    private Map<String, String> errors;
}
