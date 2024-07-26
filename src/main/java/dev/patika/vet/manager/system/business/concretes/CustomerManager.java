package dev.patika.vet.manager.system.business.concretes;


import dev.patika.vet.manager.system.business.abstracts.ICustomerService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.CustomerRepo;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private  final CustomerRepo customerRepo;

    // DI !
    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }


    // yeni müşteri oluşturma : save
    @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    // id'ye göre müşteri getir: get
    @Override
    public Customer get(long id) {
        return this.customerRepo.findById(id).orElseThrow(()
                -> new NotFoundException(Msg.NOT_FOUND));
    }

    // müşteri sayfalama işlemi --> cursor
    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.customerRepo.findAll(pageable);
    }

    // müşteri güncelle ---> update
    @Override
    public Customer update(Customer customer) {
        this.get(customer.getId());
        if(!customerRepo.existsById(customer.getId())) {
            throw new NotFoundException(customer.getId() + " id'li kayıt sistemde bulunamadı.");
        }
        return this.customerRepo.save(customer);
    }

    // silme işlemi ---> delete
    @Override
    public boolean delete(long id) {
        if(!customerRepo.existsById(id)) {
            throw  new NotFoundException(id + " id'li kayıt sistemde bulunamadı.");
        }
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }

    // tüm müşterileri getir
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        if(customers.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
        return customers;
    }

    // müşterilerin adına göre getir
    @Override
    public List<Customer> getCustomerByName(String name) {
        return customerRepo.findByName(name);
    }

    // customer ıd'ye göre havanları getir.
    @Override
    public List<Animal> getAnimalsByCustomer(long customerId) {
        Customer customer = this.get(customerId);
        return customer.getAnimals();
    }
}
