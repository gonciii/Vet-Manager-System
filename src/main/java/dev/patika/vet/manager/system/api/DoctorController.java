package dev.patika.vet.manager.system.api;
import dev.patika.vet.manager.system.business.abstracts.IDoctorService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dto.request.doctor.DoctorSaveRequest;
import dev.patika.vet.manager.system.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.vet.manager.system.dto.response.CursorResponse;
import dev.patika.vet.manager.system.dto.response.doctor.DoctorResponse;
import dev.patika.vet.manager.system.entities.Doctor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/doctors")

public class DoctorController {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;


    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    /*
    // doktor kayıt etme --- > SAVE --post
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {


        // REQUEST--> doctor
        Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
        this.doctorService.save(saveDoctor);

        // doctor --> RESPONSE
        return ResultHelper.created(this.modelMapper.forResponse().map(saveDoctor,DoctorResponse.class));
    }

     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {

        // REQUEST --> doctor
        Doctor saveDoctor = new Doctor();
        saveDoctor.setName(doctorSaveRequest.getName());
        saveDoctor.setMail(doctorSaveRequest.getMail());
        saveDoctor.setCity(doctorSaveRequest.getCity());
        saveDoctor.setPhone(doctorSaveRequest.getPhone());
        saveDoctor.setAddress(doctorSaveRequest.getAddress());


        this.doctorService.save(saveDoctor);

        // doctor --> RESPONSE
        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setId(saveDoctor.getId());
        doctorResponse.setName(saveDoctor.getName());
        doctorResponse.setMail(saveDoctor.getMail());
        doctorResponse.setAddress(saveDoctor.getAddress());
        doctorResponse.setPhone(saveDoctor.getPhone());
        doctorResponse.setCity(saveDoctor.getCity());



        return ResultHelper.created(doctorResponse);
    }

    // ID'ye göre doktor getirme ---- GET
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") long id) {
        Doctor doctor= this.doctorService.get(id);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(doctor,DoctorResponse.class);
        return ResultHelper.success(doctorResponse);
    }

    // UPDATE ----> doctor güncelleme
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        Doctor updateDoctor = this.modelMapper.forRequest().map(doctorUpdateRequest,Doctor.class);
        this.doctorService.update(updateDoctor);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateDoctor,DoctorResponse.class));
    }

    // DELETE----> doctor silme
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.doctorService.delete(id);
        return ResultHelper.ok();
    }

    // cursor ---> doctor sayfalama
    @GetMapping("/cursor/")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Doctor> doctorPage = this.doctorService.cursor(page, pageSize);
        Page<DoctorResponse> doctorResponsePage = doctorPage
                .map(doctor -> this.modelMapper.forResponse().map(doctor, DoctorResponse.class));

        // CURSOR HATASI ALIRSAN BURAYA BAK !!!
        CursorResponse<DoctorResponse> cursor = new CursorResponse<>();
        cursor.setItems(doctorResponsePage.getContent());
        cursor.setPageNumber(doctorResponsePage.getNumber());
        cursor.setPageSize(doctorResponsePage.getSize());
        cursor.setTotalElement(doctorResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }
    // tüm doktorları listelemek için :
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<DoctorResponse>> getDoctors() {

        List<Doctor> doctors = doctorService.getAllDoctors();

        List<DoctorResponse> doctorResponse = doctors.stream()
                .map(doctor -> modelMapper.forResponse().map(doctor, DoctorResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(doctorResponse);
    }


}
