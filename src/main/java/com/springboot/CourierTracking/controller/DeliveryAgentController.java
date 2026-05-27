package com.springboot.CourierTracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.DeliveryAgent;
import com.springboot.CourierTracking.service.DeliveryAgentService;

@RestController
@RequestMapping("/deliveryAgent")
public class DeliveryAgentController {
	@Autowired
	private DeliveryAgentService deliveryAgentService;

	@PostMapping
	public ResponseEntity<ResponseStructure<DeliveryAgent>> createDeliveryAgent(
			@RequestBody DeliveryAgent deliveryAgent) {

		return new ResponseEntity<>(deliveryAgentService.createDeliveryAgent(deliveryAgent), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<DeliveryAgent>>> getAllDeliveryAgents() {

		return new ResponseEntity<>(deliveryAgentService.getAllDeliveryAgents(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getById(@PathVariable Integer id) {

		return new ResponseEntity<>(deliveryAgentService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/vehicle/{vehicleNo}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getByVehicleNo(@PathVariable String vehicleNo) {

		return new ResponseEntity<>(deliveryAgentService.getByVehicleNo(vehicleNo), HttpStatus.OK);
	}

	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> getByContact(@PathVariable Long contact) {

		return new ResponseEntity<>(deliveryAgentService.getByContact(contact), HttpStatus.OK);
	}

	@GetMapping("/rating/{rating}")
	public ResponseEntity<ResponseStructure<List<DeliveryAgent>>> getByRatingGreaterThan(@PathVariable double rating) {

		return new ResponseEntity<>(deliveryAgentService.getByRatingGreaterThan(rating), HttpStatus.OK);
	}

	@GetMapping("/ratingdesc")
	public ResponseEntity<ResponseStructure<List<DeliveryAgent>>> sortByRatingInDescendingOrder() {

		return new ResponseEntity<>(deliveryAgentService.sortByRatingInDescendingOrder(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<DeliveryAgent>> updateDeliveryAgent(
			@RequestBody DeliveryAgent deliveryAgent) {

		return new ResponseEntity<>(deliveryAgentService.updateDeliveryAgent(deliveryAgent), HttpStatus.OK);
	}

	@PatchMapping("/availability/{id}/{status}")
	public ResponseEntity<ResponseStructure<DeliveryAgent>> updateAvailability(@PathVariable Integer id,
			@PathVariable Boolean status) {

		return new ResponseEntity<>(deliveryAgentService.updateDeliveryAgentAvailability(id, status), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteDeliveryAgent(@PathVariable Integer id) {

		return new ResponseEntity<>(deliveryAgentService.deleteDeliveryAgent(id), HttpStatus.OK);
	}

}
