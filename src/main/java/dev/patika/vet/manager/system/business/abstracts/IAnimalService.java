package dev.patika.vet.manager.system.business.abstracts;


// Business Layer

import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    // save
    Animal save(Animal animal);

    // get 'id ye göre
    Animal get(long id);

    // sayfalama --page
    Page<Animal> cursor(int page,int pageSize);


    // update güncelleme
    Animal update(Animal animal);


    // delete silme
    boolean delete(long id);

    // bütün hayvanları getir
    List<Animal> getAllAnimals();

    // hayvan sahiplerini isme göre filtreler
    List<Animal> getCustomerByName(String ownerName);

    // hayvanları isme göre filtreleme
    List<Animal> getAnimalByName(String name);

    // Hayvan sahibinin kayıtlı tüm hayvanlarını getirmek için :
    List<Animal> getAnimalsByCustomerId(long customerId);
}
