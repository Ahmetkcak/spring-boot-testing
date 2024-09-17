package com.odeon.odeonDemo.core.utilities.exceptions.problemDetails;

public class BusinessProblemDetails extends ProblemDetails{
    public BusinessProblemDetails(){
        setTitle("Business Rule Violation");
        setType("https://odeon.com/business-rule-violation");
        setStatus("400");
    }
}
