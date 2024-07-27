package dev.patika.vet.manager.system.dto.response.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentResponse {

    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
    private String message;
}
