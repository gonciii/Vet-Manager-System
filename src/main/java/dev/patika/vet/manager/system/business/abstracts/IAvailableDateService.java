package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {

    // save
    AvailableDate save(AvailableDate availableDate);

    // id'ye göre
    AvailableDate get(Long id);

    // get All
    List<AvailableDate> getAllAvailableDates();

    // update
    AvailableDate update(AvailableDate availableDate);

    // delete
    boolean delete(Long id);

    // cursor
    Page<AvailableDate> cursor(int page , int pageSize);

    // doktora göre
    List<AvailableDate> getAvailableDatesByDoctorId(Long doctorId);

    //Doktur girilen tarihler arasinda musait mi?
    boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date);

    //Bir doktorun belirli zaman araliklarinda bos zamani var mi?
    List<AvailableDate> getAvailableDatesByDoctorIdAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate);





}
