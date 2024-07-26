package dev.patika.vet.manager.system.dto.response.availabledate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AvailableDateResponse {

    private Long id;
    private LocalDate availableDate;
    private Long doctorId;
}
