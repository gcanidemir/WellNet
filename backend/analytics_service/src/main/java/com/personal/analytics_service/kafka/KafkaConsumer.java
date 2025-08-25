package com.personal.analytics_service.kafka;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patients", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());
        } catch (Exception e) {
            log.error("Error deserializing event: {}", e.getMessage());
        }

    }
}
