package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ICustomerService {

    // MÜŞTERİ KAYDETME -- SAVE
    Customer save(Customer customer);

    // Müşterini id numarasına göre getirme --GET
    Customer get(long id);

    // Müşteri sayfalama --> Cursor
    Page<Customer> cursor(int page,int pageSize);

    // Müşteri güncelleme --> update
    Customer update(Customer customer);

    // Müşteri silme --> delete
    boolean delete(long id);

    // Tüm müşterileri getirme
    List<Customer> getAllCustomers();

    // Müşteri adına göre arama
    List<Customer> getCustomerByName(String name);

    //Müşteriye ait tüm hayvanları getirme
    List<Animal> getAnimalsByCustomer(long customerId);

}
