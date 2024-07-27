package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {


    boolean existsByMail(String mail);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))") // This method finds the customers by name.
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);


}
