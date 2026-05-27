package com.springboot.CourierTracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.CourierTracking.entity.PackageEntity;

public interface PackageEntityRepository extends JpaRepository<PackageEntity,Integer>{
	
	
	Optional<PackageEntity> findByShipment_Id(Integer Id);
	
	 boolean existsById(Integer id);
	 

}
