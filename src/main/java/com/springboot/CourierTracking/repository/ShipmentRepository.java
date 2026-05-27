package com.springboot.CourierTracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.Customer;
import com.springboot.CourierTracking.entity.Shipment;

public interface ShipmentRepository  extends JpaRepository<Shipment,Integer>{

	Optional<Shipment> findByTrackingNumber(String trackNo);
	Optional<Shipment> findByCustomer_Id(Integer customer_id);
	Optional<Shipment> findByWarehouse_Id(Integer warehouse_id);
	Optional<Shipment> findByDeliveryAgent_Id(Integer deliveryAgent_id);
	
	List<Shipment> findBySourceAndDestination(String source,String dest);
	List<Shipment> findByDeliveryDate(String deliveryDate);
	
	
	
	boolean existsByTrackingNumber(String trackNo);
	
	
}
