package com.springboot.CourierTracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Warehouse;
import com.springboot.CourierTracking.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Warehouse>> createWarehouse(@RequestBody Warehouse warehouse) {

		return new ResponseEntity<ResponseStructure<Warehouse>>(warehouseService.createWarehouse(warehouse),
				HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Warehouse>>> getAllWarehouses() {

		return new ResponseEntity<ResponseStructure<List<Warehouse>>>(warehouseService.getAllWarehouses(),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Warehouse>> getById(@PathVariable Integer id) {

		return new ResponseEntity<ResponseStructure<Warehouse>>(warehouseService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/location/{location}")
	public ResponseEntity<ResponseStructure<Warehouse>> getByLocation(@PathVariable String location) {

		return new ResponseEntity<ResponseStructure<Warehouse>>(warehouseService.getByLocation(location),
				HttpStatus.OK);
	}

	@GetMapping("/greaterthan/{capacity}")
	public ResponseEntity<ResponseStructure<List<Warehouse>>> getByCapacityGreaterThan(@PathVariable Integer capacity) {

		return new ResponseEntity<ResponseStructure<List<Warehouse>>>(
				warehouseService.getByCapacityGreaterThan(capacity), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Warehouse>> updateWarehouse(@RequestBody Warehouse warehouse) {

		return new ResponseEntity<ResponseStructure<Warehouse>>(warehouseService.updateWarehouse(warehouse),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteWarehouse(@PathVariable Integer id) {

		return new ResponseEntity<ResponseStructure<String>>(warehouseService.deleteWarehouse(id), HttpStatus.OK);
	}

}
