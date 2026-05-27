package com.springboot.CourierTracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.entity.Status;
import com.springboot.CourierTracking.service.ShipmentService;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
	@Autowired
	private ShipmentService shipmentService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Shipment>> createShipment(@RequestBody Shipment shipment) {

		return new ResponseEntity<ResponseStructure<Shipment>>(shipmentService.createShipment(shipment),
				HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Shipment>>> getAllShipments() {

        return new ResponseEntity<>(
                shipmentService.getAllShipments(),
                HttpStatus.OK);
    }

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Shipment>> getById(@PathVariable Integer id) {

		return new ResponseEntity<>(shipmentService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/tracking/{trackingNumber}")
	public ResponseEntity<ResponseStructure<Shipment>> getByTrackingNumber(@PathVariable String trackingNumber) {

		return new ResponseEntity<>(shipmentService.getByTrackingNumber(trackingNumber), HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<ResponseStructure<Shipment>> updateStatus(@PathVariable Integer id,
			@RequestParam Status status) {

		return new ResponseEntity<>(shipmentService.updateStatus(id, status), HttpStatus.OK);
	}

	@PutMapping("/{shipmentId}/assign-agent/{deliveryAgentId}")
	public ResponseEntity<ResponseStructure<Shipment>> assignDeliveryAgent(@PathVariable Integer shipmentId,
			@PathVariable Integer deliveryAgentId) {

		return new ResponseEntity<>(shipmentService.assignDeliveryAgent(shipmentId, deliveryAgentId), HttpStatus.OK);
	}

	@PutMapping("/{shipmentId}/assign-warehouse/{warehouseId}")
	public ResponseEntity<ResponseStructure<Shipment>> assignWarehouse(@PathVariable Integer shipmentId,
			@PathVariable Integer warehouseId) {

		return new ResponseEntity<>(shipmentService.assignWarehouse(shipmentId, warehouseId), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteShipment(@PathVariable Integer id) {

		return new ResponseEntity<>(shipmentService.deleteShipment(id), HttpStatus.OK);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<ResponseStructure<Shipment>> getByCustomer(@PathVariable Integer customerId) {

		return new ResponseEntity<>(shipmentService.getByCustomer(customerId), HttpStatus.OK);
	}

	@GetMapping("/warehouse/{warehouseId}")
	public ResponseEntity<ResponseStructure<Shipment>> getByWarehouse(@PathVariable Integer warehouseId) {

		return new ResponseEntity<>(shipmentService.getByWarehouse(warehouseId), HttpStatus.OK);
	}

	@GetMapping("/delivery-agent/{agentId}")
	public ResponseEntity<ResponseStructure<Shipment>> getByDeliveryAgent(@PathVariable Integer agentId) {

		return new ResponseEntity<>(shipmentService.getByDeliveryAgent(agentId), HttpStatus.OK);
	}

	@GetMapping("/source-destination")
	public ResponseEntity<ResponseStructure<List<Shipment>>> getBySourceAndDestination(@RequestParam String source,
			@RequestParam String destination) {

		return new ResponseEntity<>(shipmentService.getBySourceAndDestination(source, destination), HttpStatus.OK);
	}

	@GetMapping("/delivery-date")
	public ResponseEntity<ResponseStructure<List<Shipment>>> getByDeliveryDate(@RequestParam String deliveryDate) {

		return new ResponseEntity<>(shipmentService.getByDeliveryDate(deliveryDate), HttpStatus.OK);
	}

	@GetMapping("/pagination")
	public ResponseEntity<ResponseStructure<Page<Shipment>>> getByPaginationAndSorting(@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize, @RequestParam String fieldName) {

		return new ResponseEntity<>(shipmentService.getByPaginationAndSorting(pageNumber, pageSize, fieldName),
				HttpStatus.OK);
	}

}
