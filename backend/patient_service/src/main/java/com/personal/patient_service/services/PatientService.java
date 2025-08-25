package com.personal.patient_service.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.personal.patient_service.dtos.PatientRequestDTO;
import com.personal.patient_service.dtos.PatientResponseDTO;
import com.personal.patient_service.exceptions.EmailAlreadyExistsException;
import com.personal.patient_service.exceptions.PatientNotFoundException;
import com.personal.patient_service.grpc.BillingServiceGrpcClient;
import com.personal.patient_service.kafka.KafkaProducer;
import com.personal.patient_service.mappers.PatientMapper;
import com.personal.patient_service.models.Patient;
import com.personal.patient_service.repositories.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::toDto)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with this email already exists");
        }

        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(),
                patient.getEmail());
        kafkaProducer.sendEvent(patient);

        return PatientMapper.toDto(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id + "."));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Patient with this email already exists");
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        return PatientMapper.toDto(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
