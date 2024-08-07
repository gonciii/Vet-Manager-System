package dev.patika.vet.manager.system.dto.request.animal;


import dev.patika.vet.manager.system.entities.Animal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnimalUpdateRequest {


    @NotNull(message = "Hayvan ID boş olamaz!")
    @Positive(message = "ID değeri pozitif sayı olmak zorunda.")
    private Long id;

    @NotBlank(message = "Hayvan Adı boş olamaz!")
    private String name;

    @NotBlank(message = "Hayvan Türü boş olamaz!")
    private String species;

    private String breed;

    private Animal.Gender gender;

    private String colour;

    @NotNull(message = "Doğum Tarihi boş olamaz!")
    private LocalDate dateOfBirth;

    @NotNull(message = "Müşteri ID boş olamaz.")
    private Long customerId;


}
