package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    // müşteri adına göre arama yapmak için :
    List<Customer> findByName(String name);

}
