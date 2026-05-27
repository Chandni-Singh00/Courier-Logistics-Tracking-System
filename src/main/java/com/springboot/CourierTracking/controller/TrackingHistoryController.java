package com.springboot.CourierTracking.controller;

import java.io.ObjectInputFilter.Status;
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
import com.springboot.CourierTracking.entity.TrackingHistory;
import com.springboot.CourierTracking.service.TrackingHistoryService;

@RestController
@RequestMapping("/tracking-history")
public class TrackingHistoryController {
	@Autowired
	private TrackingHistoryService trackService;

	@GetMapping
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getAllTrackingHistory() {

		ResponseStructure<List<TrackingHistory>> response = trackService.getAllTrackingHistory();

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<TrackingHistory>> getById(@PathVariable Integer id) {

		ResponseStructure<TrackingHistory> response = trackService.getById(id);

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@GetMapping("/tracking-number/{trackingNumber}")
	public ResponseEntity<ResponseStructure<TrackingHistory>> getByTrackingNumber(@PathVariable String trackingNumber) {

		ResponseStructure<TrackingHistory> response = trackService.getByTrackingNumber(trackingNumber);

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getByStatus(@PathVariable Status status) {

		ResponseStructure<List<TrackingHistory>> response = trackService.getByStatus(status);

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@GetMapping("/shipment/{shipmentId}")
	public ResponseEntity<ResponseStructure<List<TrackingHistory>>> getByShipment(@PathVariable Integer shipmentId) {

		ResponseStructure<List<TrackingHistory>> response = trackService.getByShipment(shipmentId);

		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<TrackingHistory>> updateTrackingHistory(
			@RequestBody TrackingHistory trackingHistory) {

		ResponseStructure<TrackingHistory> response = trackService.updateTrackingHistory(trackingHistory);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
