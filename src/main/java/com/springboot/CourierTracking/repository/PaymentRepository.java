package com.springboot.CourierTracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer>{

	 boolean existsById(Integer id);
	 
}
