package dev.patika.vet.manager.system.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentUpdateRequest {


    @NotNull(message = "ID alanı boş olamaz.")
    @Positive
    private Long id;

    @NotNull(message = "Randevu tarih-saat alanı boş olamaz.")
    private LocalDateTime appointmentDate;

    @NotNull(message = "Hayvan ID'si boş olamaz.")
    private Long animalId;

    @NotNull(message = "Doktor ID'si boş olamaz.")
    private Long doctorId;
}
