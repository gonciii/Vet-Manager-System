package dev.patika.vet.manager.system.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private long id;

    @Column(name = "vaccines_name")
    private String name;

    @Column(name = "vaccines_code",nullable = false)
    private String code;

    @Column(name = "vaccines_protectionStartDate")
    private LocalDate protectionStartDate;

    @Column(name = "vaccines_protectionFinishDate")
    private LocalDate protectionFinishDate;

    // birden fazla aşı --> bir hayvan
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
