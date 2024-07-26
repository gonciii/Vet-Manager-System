package dev.patika.vet.manager.system.api;


import dev.patika.vet.manager.system.business.abstracts.IAnimalService;
import dev.patika.vet.manager.system.business.abstracts.ICustomerService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.animal.AnimalSaveRequest;
import dev.patika.vet.manager.system.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;
import dev.patika.vet.manager.system.dto.response.animal.AnimalResponse;
import dev.patika.vet.manager.system.entities.Animal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")

public class AnimalController {
    private final IAnimalService animalService;
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;


    // dependency injection !
    public AnimalController(IAnimalService animalService, ICustomerService customerService, IModelMapperService modelMapper) {
        this.animalService = animalService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    // kayıt işlemi --> SAVE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {


        // REQUEST--> animal
        Animal saveAnimal = this.modelMapper.forRequest().map(animalSaveRequest,Animal.class);
        this.animalService.save(saveAnimal);

        // animal --> RESPONSE
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal,AnimalResponse.class));
    }

    // ID'ye göre hayvan getirme
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id") long id) {
        Animal animal = this.animalService.get(id);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal,AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    // UPDATE ----> animal güncelleme
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        Animal updateAnimal = this.modelMapper.forRequest().map(animalUpdateRequest,Animal.class);
        this.animalService.update(updateAnimal);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAnimal,AnimalResponse.class));
    }

    // DELETE----> animal silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.animalService.delete(id);

        return ResultHelper.ok();
    }

    // cursor ---> animal sayfalama
    @GetMapping("/cursor/")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Animal> animalPage = this.animalService.cursor(page, pageSize);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));

        // CURSOR HATASI ALIRSAN BURAYA BAK !!!
        CursorResponse<AnimalResponse> cursor = new CursorResponse<>();
        cursor.setItems(animalResponsePage.getContent());
        cursor.setPageNumber(animalResponsePage.getNumber());
        cursor.setPageSize(animalResponsePage.getSize());
        cursor.setTotalElement(animalResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    // tüm hayvanları getirme işlemi :
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAllAnimals() {
        List<Animal> animals = animalService.getAllAnimals();
        List<AnimalResponse> animalResponses = animals.stream().
                map(animal -> modelMapper.forResponse().map(animal,AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(animalResponses);

    }

    // hayvanları isme göre  filtreleme işlemi
    @GetMapping("/filter/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalByName(@PathVariable(name = "name",required = false) String name) {
        List<Animal> filterAnimals = animalService.getAnimalByName(name);
        List<AnimalResponse> animalResponses = filterAnimals.stream().map(animal -> modelMapper.forResponse().map(animal,AnimalResponse.class)).collect(Collectors.toList());

        return ResultHelper.success(animalResponses);
    }

    // hayvan sahipleri isme göre filtreleme (burda hata var )
    @GetMapping("/ownerName/{ownerName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getCustomerByName(@PathVariable(name = "ownerName") String ownerName) {
        List<Animal> animals = animalService.getCustomerByName(ownerName);
        List<AnimalResponse> animalResponses= animals.stream().map(animal -> modelMapper.forResponse().map(animal,AnimalResponse.class)).collect(Collectors.toList());
        return ResultHelper.success(animalResponses);

    }



}
