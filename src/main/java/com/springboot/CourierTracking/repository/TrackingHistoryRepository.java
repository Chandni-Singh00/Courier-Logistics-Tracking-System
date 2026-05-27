package com.springboot.CourierTracking.repository;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.PackageEntity;
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.entity.TrackingHistory;

public interface TrackingHistoryRepository  extends JpaRepository<TrackingHistory,Integer>{

	Optional<TrackingHistory> findByShipment_TrackingNumber(String trackingNo);
	
	List<TrackingHistory> findByStatus(Status status);
	
	
	List<TrackingHistory> findByShipment_Id(Integer Id);
	
	
	 boolean existsById(Integer id);
	 
	
	
}
