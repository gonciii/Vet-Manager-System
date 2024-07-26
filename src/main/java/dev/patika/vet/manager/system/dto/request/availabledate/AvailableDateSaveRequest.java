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

public class AvailableDateSaveRequest {

    @NotNull(message = "Tarih alanı boş olamaz.")
    private LocalDate availableDate;

    @NotNull(message = "Doktor Id'si boş olamaz.")
    @Positive
    private Long doctorId;
}
