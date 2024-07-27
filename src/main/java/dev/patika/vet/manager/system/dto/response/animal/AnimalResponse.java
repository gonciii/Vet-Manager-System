package dev.patika.vet.manager.system.dto.response.animal;


import dev.patika.vet.manager.system.entities.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AnimalResponse {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private Animal.Gender gender;
    private String colour;
    private LocalDate dateOfBirth;
    private Long customerId;
    private String customerName;


}
