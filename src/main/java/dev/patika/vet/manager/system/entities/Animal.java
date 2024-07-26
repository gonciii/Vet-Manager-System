package dev.patika.vet.manager.system.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "animals")  // veri tabanında "animals" tablosu oluştur !
public class Animal {

    @Id  // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID -->IDENTITY !
    @Column(name = "animal_id")
    private long id;

    @Column(name = "animal_name")
    private String name;

    @Column(name = "animal_species")
    private String species;

    @Column(name = "animal_breed")
    private String breed;

    @Column(name = "animal_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "animal_colour")
    private String colour;

    @Column(name = "animal_dateOfBirth")
    private LocalDate dateOfBirth;

    public enum Gender{
        MALE,
        FEMALE
    }


    // bir müşterinin birden fazla hayvanı olabilir ama bir hayvanın bir sahibi olur !
    @ManyToOne
    @JoinColumn(name = "customer_id") // customer_id ile ilişki kur !
    private Customer customer;

    // bir hayvanın birden fazla aşısı olabilir !
    @OneToMany(mappedBy = "animal")
    private List<Vaccine> vaccines;

    // bir hayvanın birden fazla randevusu olabilir.
    @OneToMany(mappedBy = "animal")
    private List<Appointment> appointments;








}
