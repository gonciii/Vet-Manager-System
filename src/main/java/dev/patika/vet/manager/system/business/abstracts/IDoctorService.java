package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDoctorService {

    Doctor save(Doctor doctor);

    Doctor get(long id);

    Page<Doctor> cursor(int page,int pageSize);

    Doctor update(Doctor doctor);

    boolean delete(long id);

    List<Doctor> getAllDoctors();

    List<Doctor> getDoctorsByName(String name);
}
