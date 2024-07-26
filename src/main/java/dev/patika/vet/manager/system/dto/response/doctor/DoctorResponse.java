package dev.patika.vet.manager.system.dto.response.doctor;


import dev.patika.vet.manager.system.dto.response.appointment.AppointmentResponse;
import dev.patika.vet.manager.system.dto.response.availabledate.AvailableDateResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorResponse {

    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;



}
