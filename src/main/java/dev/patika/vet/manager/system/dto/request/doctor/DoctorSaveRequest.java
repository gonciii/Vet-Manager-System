package dev.patika.vet.manager.system.dto.request.doctor;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorSaveRequest {
    @NotNull(message = "Doktor adı boş olamaz.")
    private String name;

    @NotNull(message = "Doktor telefon numarası boş olamaz.")
    private String phone;

    @Email
    @NotBlank(message = "E-posta boş olamaz.")
    private String mail;

    private String address;
    private String city;
}
