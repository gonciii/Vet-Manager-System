package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    // Doktor adına göre arama yapma işlemi :
    List<Doctor> findByName(String name);

    boolean existsByMail(String mail);

}

