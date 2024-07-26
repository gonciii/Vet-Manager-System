package dev.patika.vet.manager.system.dto.request.animal;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor


// DATA TRANSFER OBJECT (DTO)
public class AnimalSaveRequest {


    @NotBlank(message = "Hayvan Adı boş olamaz!")
    private String name;

    @NotBlank(message = "Hayvan Türü boş olamaz!")
    private String species;

    private String breed;

    @NotBlank(message = "Cinsiyet boş olamaz!")
    private String gender;

    private String colour;

    @NotNull(message = "Doğum Tarihi boş olamaz!")
    @Past(message = "Doğum Tarihi geçmişte olmalıdır!")
    private LocalDate dateOfBirth;

    @NotNull(message = "Müşteri ID boş olamaz!")
    private Long customerId;
}
