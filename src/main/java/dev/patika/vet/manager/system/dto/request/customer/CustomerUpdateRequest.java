package dev.patika.vet.manager.system.dto.request.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerUpdateRequest {

    @NotNull(message = "Müşteri ID alanı boş olamaz.")
    @Positive(message = "ID değeri pozitif sayı olmak zorunda.")
    private Long id;

    @NotNull(message = "Müşteri ismi boş olamaz.")
    private String name;

    @NotNull(message = "Müşteri iletişim numarası boş olamaz.")
    private String phone;

    @Email
    @NotBlank(message = "Müşteri e-posta boş olamaz.")
    private String mail;

    private String address;

    private String city;

}
