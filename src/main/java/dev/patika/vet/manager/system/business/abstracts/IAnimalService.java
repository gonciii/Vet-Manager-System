package dev.patika.vet.manager.system.business.abstracts;


// Business Layer

import dev.patika.vet.manager.system.entities.Animal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal);

    Animal get(Long id);


    Page<Animal> cursor(int page,int pageSize);


    Animal update(Animal animal);

    boolean delete(Long id);

    // bütün hayvanları getir
    List<Animal> getAllAnimals();

    // İsme göre hayvan aramak için
    List<Animal> getAnimalsByName(String name);

    // customer name göre hayvanları getirsin !
    List<Animal> getAnimalsByCustomerName(String name);


}
