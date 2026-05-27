package com.springboot.CourierTracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
	Optional<Customer> findByEmail(String email);
	
	Optional<Customer> findByContact(Long contact);
	
	boolean existsByEmail(String email);
	boolean existsByContact(Long contact);
	
	 boolean existsById(Integer id);
	 
	

}
