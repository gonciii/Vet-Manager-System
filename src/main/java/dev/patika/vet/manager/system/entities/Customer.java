package dev.patika.vet.manager.system.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "customers")
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_mail",unique = true)
    private String mail;

    @Column(name = "customer_address")
    private String address;

    @Column(name = "customer_city")
    private String city;

    // bir müşterinin birden fazla hayvanı olabilir !
    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)  // animal entity'si ile ilişki kuruldu !
    private List<Animal> animals;


}
