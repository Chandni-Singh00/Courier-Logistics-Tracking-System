package com.springboot.CourierTracking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Customer;
import com.springboot.CourierTracking.entity.DeliveryAgent;
import com.springboot.CourierTracking.entity.PackageEntity;
import com.springboot.CourierTracking.entity.Payment;
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.entity.Status;
import com.springboot.CourierTracking.entity.TrackingHistory;
import com.springboot.CourierTracking.entity.Warehouse;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.exception.OperationNotAllowedException;
import com.springboot.CourierTracking.exception.ResourceNotFoundException;
import com.springboot.CourierTracking.repository.CustomerRepository;
import com.springboot.CourierTracking.repository.DeliveryAgentRepository;
import com.springboot.CourierTracking.repository.PackageEntityRepository;
import com.springboot.CourierTracking.repository.PaymentRepository;
import com.springboot.CourierTracking.repository.ShipmentRepository;
import com.springboot.CourierTracking.repository.TrackingHistoryRepository;
import com.springboot.CourierTracking.repository.WarehouseRepository;

@Service
public class ShipmentService {
	@Autowired
	private ShipmentRepository shipmentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private WarehouseRepository warehouseRepository;

	@Autowired
	private DeliveryAgentRepository deliveryAgentRepository;

	@Autowired
	private PackageEntityRepository packageRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TrackingHistoryRepository trackRepository;

	public ResponseStructure<Shipment> createShipment(Shipment shipment) {

		ResponseStructure<Shipment> res = new ResponseStructure<>();

		
		Integer customerId = shipment.getCustomer().getId();

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new IdNotFoundException("customer id not found : " + customerId));

		shipment.setCustomer(customer);

		
//		Integer warehouseId = shipment.getWarehouse().getId();
//
//		Warehouse warehouse = warehouseRepository.findById(warehouseId)
//				.orElseThrow(() -> new IdNotFoundException("warehouse id not found : " + warehouseId));
//
//		shipment.setWarehouse(warehouse);
//
//		
//		shipment.setStatus(Status.IN_TRANSIT);
//		
//		
//		Integer delivId = shipment.getDeliveryAgent().getId();
//
//	    DeliveryAgent deliveryAgent = deliveryAgentRepository.findById(delivId)
//	            .orElseThrow(() ->
//	                    new IdNotFoundException("delivery agent id not found : " + delivId));
//
//	   
//	    if (!deliveryAgent.getAvailabilityStatus()) {
//	        throw new OperationNotAllowedException("specified delivery agent is not available");
//	    }
//
//	    shipment.setDeliveryAgent(deliveryAgent);

		shipment.setStatus(Status.CREATED); 
		
		if (shipmentRepository.existsByTrackingNumber(shipment.getTrackingNumber())) {
			throw new DataIntegrityViolationException("tracking number already exists");
		}

		
		if (shipment.getPayment() == null) {
			throw new ResourceNotFoundException("payment is necessary to provide");
		}

		Payment payment = shipment.getPayment();
		payment.setShipment(shipment);

		
		if (shipment.getPackageEntity() == null) {
			throw new ResourceNotFoundException("package entity is necessary to provide");
		}

		PackageEntity pkg = shipment.getPackageEntity();
		pkg.setShipment(shipment);

		// ---------------- PRICE CALCULATION ----------------

		double amount = 50.0; // base charge

		// weight based pricing
		if (shipment.getWeight() != null) {

			if (shipment.getWeight() <= 5) {
				amount += 50;
			} else if (shipment.getWeight() <= 10) {
				amount += 100;
			} else {
				amount += 150;
			}
		}

		// distance based pricing
		if (shipment.getDistance() != null) {

			if (shipment.getDistance() <= 50) {
				amount += 30;
			} else if (shipment.getDistance() <= 200) {
				amount += 80;
			} else {
				amount += 150;
			}
		}

		// fragile item extra charge
		if (Boolean.TRUE.equals(pkg.getFragile())) {
			amount += 20;
		}

		payment.setAmount(amount);

		
		TrackingHistory track = new TrackingHistory();

		track.setLocation(shipment.getSource());
		track.setRemarks("Shipment Created");
		track.setStatus(Status.CREATED);
		track.setShipment(shipment);

		shipment.setTrackingHistories(new ArrayList<>());
		shipment.getTrackingHistories().add(track);

		
		Shipment savedShipment = shipmentRepository.save(shipment);

		
		res.setData(savedShipment);
		res.setMessage("Shipment created successfully");
		res.setStatusCode(HttpStatus.CREATED.value());

		return res;
	}

	public ResponseStructure<List<Shipment>> getAllShipments() {
		ResponseStructure<List<Shipment>> res = new ResponseStructure<>();
		List<Shipment> shipments = shipmentRepository.findAll();
		if (shipments.isEmpty()) {
			throw new NoRecordAvailableException("data not available in DB");

		} else {
			res.setData(shipments);
			res.setMessage("data fetched succesfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		}
	}

	public ResponseStructure<Shipment> getById(Integer id) {
		Optional<Shipment> opt = shipmentRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<Shipment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("id not found : " + id);
		}

	}

	public ResponseStructure<Shipment> getByTrackingNumber(String trackNo) {
		Optional<Shipment> opt = shipmentRepository.findByTrackingNumber(trackNo);

		if (opt.isPresent()) {

			ResponseStructure<Shipment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("tracking no not found : " + trackNo);
		}

	}

	public ResponseStructure<Shipment> updateStatus(Integer id, Status status) {

		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new IdNotFoundException("id not found : " + id));

		shipment.setStatus(status);

		TrackingHistory track = new TrackingHistory();

		track.setShipment(shipment);
		track.setStatus(status);

		// ---------------- LOCATION BASED ON STATUS ----------------

		if (status == Status.IN_TRANSIT) {

			track.setLocation(shipment.getWarehouse().getLocation());
			track.setRemarks("Shipment is in transit");

		} else if (status == Status.OUT_FOR_DELIVERY) {

			track.setLocation(shipment.getDestination());
			track.setRemarks("Shipment is out for delivery");

		} else if (status == Status.DELIVERED) {

			track.setLocation(shipment.getDestination());
			track.setRemarks("Shipment delivered successfully");

			// make agent available again
			if (shipment.getDeliveryAgent() != null) {
				shipment.getDeliveryAgent().setAvailabilityStatus(true);
			}

		} else if (status == Status.CANCELLED) {

			track.setLocation(shipment.getSource());
			track.setRemarks("Shipment has been cancelled");

		} else {

			track.setLocation(shipment.getSource());
			track.setRemarks("Shipment status updated to " + status);

		}

		shipment.getTrackingHistories().add(track);

		Shipment updatedShipment = shipmentRepository.save(shipment);

		ResponseStructure<Shipment> res = new ResponseStructure<>();

		res.setData(updatedShipment);
		res.setMessage("Shipment status updated successfully");
		res.setStatusCode(HttpStatus.OK.value());

		return res;
	}

	public ResponseStructure<Shipment> assignDeliveryAgent(Integer shipmentId, Integer deliveryAgentId) {

		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new IdNotFoundException("shipment id not found : " + shipmentId));

		DeliveryAgent deliveryAgent = deliveryAgentRepository.findById(deliveryAgentId)
				.orElseThrow(() -> new IdNotFoundException("delivery agent id not found : " + deliveryAgentId));

		if (!deliveryAgent.getAvailabilityStatus()) {
			throw new OperationNotAllowedException("delivery agent is not available");
		}

		shipment.setDeliveryAgent(deliveryAgent);

		deliveryAgent.setAvailabilityStatus(false);

		shipment.setStatus(Status.OUT_FOR_DELIVERY);

		TrackingHistory track = new TrackingHistory();

		track.setShipment(shipment);
		track.setStatus(Status.OUT_FOR_DELIVERY);
		track.setLocation(shipment.getDestination());
		track.setRemarks("Delivery agent assigned");

		shipment.getTrackingHistories().add(track);

		Shipment updatedShipment = shipmentRepository.save(shipment);

		ResponseStructure<Shipment> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Delivery agent assigned successfully");
		res.setData(updatedShipment);

		return res;
	}

	public ResponseStructure<Shipment> assignWarehouse(Integer shipmentId, Integer warehouseId) {

		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new IdNotFoundException("shipment id not found : " + shipmentId));

		Warehouse warehouse = warehouseRepository.findById(warehouseId)
				.orElseThrow(() -> new IdNotFoundException("delivery agent id not found : " + warehouseId));

		shipment.setWarehouse(warehouse);

		shipment.setStatus(Status.IN_TRANSIT);

		TrackingHistory track = new TrackingHistory();

		track.setShipment(shipment);
		track.setStatus(Status.IN_TRANSIT);
		track.setLocation(warehouse.getLocation());
		track.setRemarks("watehouse  assigned");

		shipment.getTrackingHistories().add(track);

		Shipment updatedShipment = shipmentRepository.save(shipment);

		ResponseStructure<Shipment> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Warehouse assigned successfully");
		res.setData(updatedShipment);

		return res;
	}

	public ResponseStructure<String> deleteShipment(Integer id) {
		Optional<Shipment> opt = shipmentRepository.findById(id);
		if (opt.isPresent()) {
			shipmentRepository.delete(opt.get());
			ResponseStructure<String> res = new ResponseStructure<>();
			res.setMessage("Succesfully deleted");
			res.setData("deleted successfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;

		} else {
			throw new IdNotFoundException("id not found: " + id);
		}

	}
	
	
	public ResponseStructure<Shipment> getByCustomer(Integer customer_id) {
		Optional<Shipment> opt = shipmentRepository.findByCustomer_Id(customer_id);

		if (opt.isPresent()) {

			ResponseStructure<Shipment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("tracking no not found : " + customer_id);
		}

	}
	
	public ResponseStructure<Shipment> getByWarehouse(Integer warehouse_id) {
		Optional<Shipment> opt = shipmentRepository.findByWarehouse_Id(warehouse_id);

		if (opt.isPresent()) {

			ResponseStructure<Shipment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("tracking no not found : " + warehouse_id);
		}

	}
	
	public ResponseStructure<Shipment> getByDeliveryAgent(Integer dlvAgt_id) {
		Optional<Shipment> opt = shipmentRepository.findByDeliveryAgent_Id(dlvAgt_id);

		if (opt.isPresent()) {

			ResponseStructure<Shipment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("tracking no not found : " + dlvAgt_id);
		}

	}
	
	public ResponseStructure<List<Shipment>> getBySourceAndDestination(String src,String dest ) {
		List<Shipment> opt = shipmentRepository.findBySourceAndDestination(src,dest);

		if (opt.isEmpty()) {

			ResponseStructure<List<Shipment>> res = new ResponseStructure<>();

			res.setData(opt);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("src and dest not found : " + src +" "+ dest);
		}

	}
	
	public ResponseStructure<List<Shipment>> getByDeliveryDate(String deliveryDate) {
		List<Shipment> opt = shipmentRepository.findByDeliveryDate(deliveryDate);

		if (opt.isEmpty()) {

			ResponseStructure<List<Shipment>> res = new ResponseStructure<>();

			res.setData(opt);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("deliveryDate  not found : " + deliveryDate);
		}

	}
	
	public ResponseStructure<Page<Shipment>> getByPaginationAndSorting(Integer pageNumber,Integer pageSize,String fieldName) {
		Page<Shipment> page = shipmentRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by(fieldName)));

		if (page.isEmpty()) {

			ResponseStructure<Page<Shipment>> res = new ResponseStructure<>();

			res.setData(page);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new NoRecordAvailableException("data not found" );
		}

	}
	

}
