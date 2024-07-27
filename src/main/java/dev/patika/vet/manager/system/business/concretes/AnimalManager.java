package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IAnimalService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AnimalRepo;
import dev.patika.vet.manager.system.dao.CustomerRepo;
import dev.patika.vet.manager.system.entities.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager  implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;


    // dependency injection +
    public AnimalManager(AnimalRepo animalRepo, CustomerRepo customerRepo) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
    }

    // save --> yeni hayvan kaydetme
    @Override
    public Animal save(Animal animal) {
        return this.animalRepo.save(animal);
    }

    //  id 'ye göre hayvanı getir !
    @Override
    public Animal get(Long id) {
        return this.animalRepo.findById(id).orElseThrow(()
                -> new NotFoundException(Msg.NOT_FOUND));
    }

    // cursor -- page işlemi ! sayfalama yaparak hayvanı getir.
    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);
    }

    // update işlemi ! kaydı güncelle
    @Override
    public Animal update(Animal animal) {
        // id kontrol --- > yoksa exception
        this.get(animal.getId());
        if( !animalRepo.existsById(animal.getId())) {
            throw new NotFoundException(animal.getId() + " id'li kayıt sistemde bulanamdı.");
        }
        return this.animalRepo.save(animal);
    }

    // delete işlemi ! hayvan kaydını sil
    @Override
    public boolean delete(Long id) {
        if(!animalRepo.existsById(id)) {
            throw  new NotFoundException(id  + " id 'li kayıt sistemde bulunamadı.");
        }
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    // getAllAnimals --- > tüm hayvanları getir.
    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> animals = animalRepo.findAll();
        if(animals.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
        return animals;
    }

    // hayvan adına göre arama yap
    @Override
    public List<Animal> getAnimalsByName(String name) {
        return this.animalRepo.findByAnimalName(name);
    }

    @Override
    public List<Animal> getAnimalsByCustomerName(String name) {
        return this.animalRepo.findAnimalsByCustomerName(name);
    }

}
