package dev.patika.vet.manager.system.api;

import dev.patika.vet.manager.system.business.abstracts.IAnimalService;
import dev.patika.vet.manager.system.business.abstracts.IVaccineService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;
import dev.patika.vet.manager.system.dto.response.vaccine.VaccineResponse;
import dev.patika.vet.manager.system.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapperService) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapper = modelMapperService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VaccineResponse> createVaccine(@RequestBody @Valid VaccineSaveRequest vaccineSaveRequest) {
        VaccineResponse vaccineResponse = vaccineService.save(vaccineSaveRequest);
        return new ResponseEntity<>(vaccineResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") long id) {
        Vaccine vaccine = this.vaccineService.get(id);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.getAllVaccines();
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VaccineResponse> updateVaccine(@RequestBody @Valid VaccineUpdateRequest vaccineUpdateRequest) {
        VaccineResponse vaccineResponse = vaccineService.update(vaccineUpdateRequest);
        return new ResponseEntity<>(vaccineResponse, HttpStatus.OK); // 200 OK status kodu döner
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/cursor")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize) {
        Page<Vaccine> vaccinePage = this.vaccineService.cursor(page, pageSize);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
        CursorResponse<VaccineResponse> cursor = new CursorResponse<>();
        cursor.setItems(vaccineResponsePage.getContent());
        cursor.setPageNumber(vaccineResponsePage.getNumber());
        cursor.setPageSize(vaccineResponsePage.getSize());
        cursor.setTotalElement(vaccineResponsePage.getTotalElements());
        return ResultHelper.success(cursor);
    }

    @GetMapping("/animals/{animalId}/vaccines")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("animalId") long animalId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(animalId);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/date/range")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByDateRange(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByDateRange(startDate, endDate);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }
}