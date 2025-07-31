package com.personal.patient_service.mappers;

import java.time.LocalDate;

import com.personal.patient_service.dtos.PatientRequestDTO;
import com.personal.patient_service.dtos.PatientResponseDTO;
import com.personal.patient_service.models.Patient;

public class PatientMapper {

    public static PatientResponseDTO toDto(Patient patient) {
        if (patient == null) return null;

        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId().toString());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setAddress(patient.getAddress());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        return dto;
    }

    public static Patient toModel(PatientRequestDTO dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));
        return patient;
    }
}
