package com.springboot.CourierTracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.PackageEntity;
import com.springboot.CourierTracking.service.PackageEntityService;

@RestController
@RequestMapping("/package")
public class PackageEntityController {
	@Autowired
	private PackageEntityService packageService;

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<PackageEntity>>> getAllPackages() {

		return new ResponseEntity<>(packageService.getAllPackages(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<PackageEntity>> getById(@PathVariable Integer id) {

		return new ResponseEntity<>(packageService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/shipment/{shipmentId}")
	public ResponseEntity<ResponseStructure<PackageEntity>> getByShipment(@PathVariable Integer shipmentId) {

		return new ResponseEntity<>(packageService.getByShipment(shipmentId), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<PackageEntity>> updatePackage(@RequestBody PackageEntity packageEntity) {

		return new ResponseEntity<>(packageService.updatePackage(packageEntity), HttpStatus.OK);
	}

}
