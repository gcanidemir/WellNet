package com.personal.patient_service.dtos;

import com.personal.patient_service.dtos.validator.CreatePatientValidationGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class PatientRequestDTO {

    @NotBlank(message = "Name cannot be blank.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Address cannot be blank.")
    private String address;

    @NotBlank(message = "Date of birth cannot be blank.")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date cannot be null.")
    private String registeredDate;


}
