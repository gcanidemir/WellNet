package com.personal.patient_service.exceptions;

public class PatientNotFoundException extends RuntimeException {
    
    public PatientNotFoundException(String message) {
        super(message);
    }

}
