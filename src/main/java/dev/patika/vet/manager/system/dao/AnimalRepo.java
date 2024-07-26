package dev.patika.vet.manager.system.dao;

// DATA ACCESS LAYER
import dev.patika.vet.manager.system.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository    // bu anatasyon sınıfın bir Spring repository anatasyonu,bileşeni olduğunu belirtir !

public interface AnimalRepo  extends JpaRepository<Animal,Long> {

    // JpaRepository arayüzü, temel CRUD işlemlerini otomatik olarak sağlar:
    // Spring Data JPA, method isimlendirme kurallarına göre özel sorgular oluşturmanıza izin verir.
    List<Animal> findByName(String ownerName);
}
