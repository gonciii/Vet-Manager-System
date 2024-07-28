package dev.patika.vet.manager.system.api;


import dev.patika.vet.manager.system.business.abstracts.IAnimalService;
import dev.patika.vet.manager.system.business.abstracts.IAppointmentService;
import dev.patika.vet.manager.system.business.abstracts.IDoctorService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vet.manager.system.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;

import dev.patika.vet.manager.system.dto.response.animal.AnimalResponse;
import dev.patika.vet.manager.system.dto.response.appointment.AppointmentResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Appointment;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;



    // dependency injection !
    public AppointmentController(IAppointmentService appointmentService, IModelMapperService modelMapper, IDoctorService doctorService, IAnimalService animalService) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    // kayıt işlemi --> SAVE
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        return appointmentService.save(appointmentSaveRequest);
    }

    // ID'ye göre randevu getirme
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") long id) {
        Appointment appointment = this.appointmentService.get(id);
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment,AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    // UPDATE ----> randevu güncelleme
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment updateAppointment = this.modelMapper.forRequest().map(appointmentUpdateRequest,Appointment.class);
        this.appointmentService.update(updateAppointment);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateAppointment,AppointmentResponse.class));
    }

    // DELETE----> randevu silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }

    // tüm randevuarı getir
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentResponse> appointmentResponses = appointments.stream().
                map(appointment -> modelMapper.forResponse().map(appointment,AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);

    }

    // cursor ---> randevu sayfalama
    @GetMapping("/cursor/")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Appointment> appointmentPage = this.appointmentService.cursor(page, pageSize);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
        CursorResponse<AppointmentResponse> cursor = new CursorResponse<>();
        cursor.setItems(appointmentResponsePage.getContent());
        cursor.setPageNumber(appointmentResponsePage.getNumber());
        cursor.setPageSize(appointmentResponsePage.getSize());
        cursor.setTotalElement(appointmentResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    @GetMapping("/doctor")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultData<List<AppointmentResponse>>> getByDoctorIdAndAppointmentDateBetween(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("start_date_time") LocalDateTime startDateTime,
            @RequestParam("end_date_time") LocalDateTime endDateTime) {
        ResultData<List<AppointmentResponse>> result = this.appointmentService.getAppointmentsByDoctorAndDate(startDateTime, endDateTime, doctorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/animal")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultData<List<AppointmentResponse>>>getByAnimalIdAndAppointmentDateBetween(
            @RequestParam("animalId") Long animalId,
            @RequestParam("start_date_time") LocalDateTime startDateTime,
            @RequestParam("end_date_time") LocalDateTime endDateTime) {
        ResultData<List<AppointmentResponse>> result = this.appointmentService.getAppointmentsByAnimalAndDate(animalId,startDateTime, endDateTime);

        return ResponseEntity.ok(result);
    }
}
