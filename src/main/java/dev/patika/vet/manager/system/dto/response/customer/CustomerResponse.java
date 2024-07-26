package dev.patika.vet.manager.system.dto.response.customer;

import dev.patika.vet.manager.system.dto.response.animal.AnimalResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerResponse {

    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String city;


}
