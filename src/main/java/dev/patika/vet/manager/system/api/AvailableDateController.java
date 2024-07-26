package dev.patika.vet.manager.system.api;


import dev.patika.vet.manager.system.business.abstracts.IAvailableDateService;
import dev.patika.vet.manager.system.business.abstracts.IDoctorService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.animal.AnimalUpdateRequest;
import dev.patika.vet.manager.system.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.vet.manager.system.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;

import dev.patika.vet.manager.system.dto.response.animal.AnimalResponse;
import dev.patika.vet.manager.system.dto.response.availabledate.AvailableDateResponse;

import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.AvailableDate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/availableDates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    // dı !
    public AvailableDateController(IAvailableDateService availableDateService, IDoctorService doctorService, IModelMapperService modelMapperService) {
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapperService;
    }

    // SAVE ---> müsait gün kaydetme işlemi :
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {


        // REQUEST--> available date
        AvailableDate saveAvailableDate = this.modelMapper.forRequest().map(availableDateSaveRequest,AvailableDate.class);
        this.availableDateService.save(saveAvailableDate);

        // available date --> RESPONSE
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAvailableDate,AvailableDateResponse.class));
    }
    // tüm müsait günleri getiren
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> getAllAvailableDates() {
        List<AvailableDate> availableDates = availableDateService.getAllAvailableDates();

        List<AvailableDateResponse> availableDateResponses = availableDates.stream()
                .map(availableDate -> modelMapper.forResponse().map(availableDate, AvailableDateResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(availableDateResponses);
    }

    // cursor ---> müsait gün sayfalama
    @GetMapping("/cursor/")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<AvailableDate> availableDatePage = this.availableDateService.cursor(page, pageSize);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));

        // CURSOR HATASI ALIRSAN BURAYA BAK !!!
        CursorResponse<AvailableDateResponse> cursor = new CursorResponse<>();
        cursor.setItems(availableDateResponsePage.getContent());
        cursor.setPageNumber(availableDateResponsePage.getNumber());
        cursor.setPageSize(availableDateResponsePage.getSize());
        cursor.setTotalElement(availableDateResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    // ID'ye göre müsait gün getirme
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class);
        return ResultHelper.success(availableDateResponse);
    }

    // DELETE----> müsait gün silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.availableDateService.delete(id);

        return ResultHelper.ok();
    }

    // UPDATE ----> müsait gün güncelleme
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        AvailableDate updateAvailableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest,AvailableDate.class);
        this.availableDateService.update(updateAvailableDate);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAvailableDate,AvailableDateResponse.class));
    }

    // Belirli bir doktora ait AvailableDate kayıtlarını getiren metod
    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> getAvailableDatesByDoctor(@PathVariable("doctorId") long doctorId) {
        List<AvailableDate> availableDates = availableDateService.getAvailableDatesByDoctorId(doctorId);

        List<AvailableDateResponse> availableDateResponses = availableDates.stream()
                .map(availableDate -> modelMapper.forResponse().map(availableDate, AvailableDateResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(availableDateResponses);
    }

    // Belirli bir doktorun belirli bir tarihte müsait olup olmadığını kontrol eden metod
    @GetMapping("/doctor/{doctorId}/availability")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<String> checkDoctorAvailability(
            @PathVariable("doctorId") Long doctorId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Belirli bir doktorun belirtilen tarihte müsait olup olmadığını kontrol et
        boolean isAvailable = availableDateService.isDoctorAvailableOnDate(doctorId, date);

        // Kontrol sonucuna göre başarılı bir response döndür
        if (isAvailable) {
            return ResultHelper.success("Doktor belirtilen tarihte müsaittir.");
        } else {
            return ResultHelper.success("Doktor belirtilen tarihte müsait değil.");
        }
    }

    // Belirli bir doktorun belirli bir tarih aralığındaki müsait zamanlarını getiren metod
    @GetMapping("/doctor/{doctorId}/availability-range")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> getAvailableDatesByDoctorAndDateRange(
            @PathVariable("doctorId") long doctorId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<AvailableDate> availableDates = availableDateService.getAvailableDatesByDoctorIdAndDateRange(doctorId, startDate, endDate);

        List<AvailableDateResponse> availableDateResponses = availableDates.stream()
                .map(availableDate -> modelMapper.forResponse().map(availableDate, AvailableDateResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(availableDateResponses);
    }













}
