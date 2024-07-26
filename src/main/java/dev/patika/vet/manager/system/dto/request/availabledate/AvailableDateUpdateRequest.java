package dev.patika.vet.manager.system.dto.request.availabledate;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class AvailableDateUpdateRequest {

    @NotNull(message = "ID alanı boş olamaz.")
    @Positive(message = "ID pozitif sayı olmalı.")
    private Long id;

    @NotNull(message = "Tarih boş olamaz.")
    private LocalDate availableDate;

    @NotNull(message = "Doktor ID alanı boş olamaz.")
    private LocalDate doctorId;




}
