package dev.patika.vet.manager.system.dao;

// DATA ACCESS LAYER
import dev.patika.vet.manager.system.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository    // bu anatasyon sınıfın bir Spring repository anatasyonu,bileşeni olduğunu belirtir !

public interface AnimalRepo  extends JpaRepository<Animal,Long> {

    // isme göre arama
    @Query("SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Animal> findByAnimalName(@Param("name") String name);

    // customer name göre arama
    @Query("SELECT a FROM Animal a WHERE a.customer.name = :customerName")
    List<Animal> findAnimalsByCustomerName(@Param("customerName") String customerName);

}
