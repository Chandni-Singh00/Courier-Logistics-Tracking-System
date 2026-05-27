package com.springboot.CourierTracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.DeliveryAgent;

public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent,Integer>{

	Optional<DeliveryAgent> findByVehicleNumber(String vehicleNo);
	
	Optional<DeliveryAgent> findByContact(Long contact);
	
	List<DeliveryAgent> findByRatingGreaterThan(double rating);
	
	boolean existsByContact(Long contact);
	
	
	boolean existsByVehicleNumber(String vehicleNo);
	
	 boolean existsById(Integer id);
	 
	
}
