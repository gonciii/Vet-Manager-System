package dev.patika.vet.manager.system.dto.request.appointment;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

 // tarih saat formatı ----------> 2024-09-01T12:00:00


public class AppointmentSaveRequest {
    @NotNull(message = "Randevu tarih-saat alanı boş olamaz.")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Hayvan ID'si boş olamaz.")
    private Long animalId;

    @NotNull(message = "Doktor ID'si boş olamaz.")
    private Long doctorId;
}
