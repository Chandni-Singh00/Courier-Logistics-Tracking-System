package com.springboot.CourierTracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.Warehouse;


public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {

	Optional<Warehouse> findByLocation(String location);
	
	List<Warehouse> findByCapacityGreaterThan(Integer capacity);
	
	boolean existsByContact(Long contact);
	
	 boolean existsById(Integer id);
	 
	
}
