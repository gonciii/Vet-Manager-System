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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Column(name = "animal_name", nullable = false)
    private String name;

    @Column(name = "animal_species", nullable = false)
    private String species;

    @Column(name = "animal_breed")
    private String breed;

    @Column(name = "animal_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "animal_colour")
    private String colour;

    @Column(name = "animal_date_of_birth")
    private LocalDate dateOfBirth;

    public enum Gender {
        MALE,
        FEMALE
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Vaccine> vaccines;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Appointment> appointments;







}
