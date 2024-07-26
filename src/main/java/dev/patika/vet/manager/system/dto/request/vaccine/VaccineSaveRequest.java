package dev.patika.vet.manager.system.dto.request.vaccine;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VaccineSaveRequest {

    @NotNull(message = "Aşı adı boş olamazç.")
    private String name;

    @NotNull(message = "Aşı kodu boş olamaz.")
    private String code;

    @NotNull(message = "Aşı koruma başlangıç tarihi boş olamaz.")
    private LocalDate protectionStartDate;

    @NotNull(message = "Aşı koruma bitiş tarihi boş olamaz.")
    private LocalDate protectionFinishDate;

    private Long animalId;
}
