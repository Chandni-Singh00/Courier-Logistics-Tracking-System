package com.springboot.CourierTracking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.DeliveryAgent;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.exception.OperationNotAllowedException;
import com.springboot.CourierTracking.repository.DeliveryAgentRepository;

@Service
public class DeliveryAgentService {
	@Autowired
	private DeliveryAgentRepository deliveryAgentRepository;

	public ResponseStructure<DeliveryAgent> createDeliveryAgent(DeliveryAgent deliveryAgent) {

		if (deliveryAgent.getContact() != null) {

			String contactStr = String.valueOf(deliveryAgent.getContact());

			if (contactStr.length() != 10) {
				throw new IllegalArgumentException("Contact must be exactly 10 digits");
			}

			if (deliveryAgentRepository.existsByContact(deliveryAgent.getContact())) {
				throw new DataIntegrityViolationException("Contact already exists");
			}
		}

		if (deliveryAgentRepository.existsByVehicleNumber(deliveryAgent.getVehicleNumber())) {
			throw new DataIntegrityViolationException("Vehicle number already exists");
		}

		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();

		DeliveryAgent saved = deliveryAgentRepository.save(deliveryAgent);

		res.setData(saved);
		res.setMessage("Delivery Agent saved successfully");
		res.setStatusCode(HttpStatus.CREATED.value());

		return res;
	}

	public ResponseStructure<List<DeliveryAgent>> getAllDeliveryAgents() {

		List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findAll();

		if (deliveryAgents.isEmpty()) {
			throw new NoRecordAvailableException("there is no delivery agent in DB");
		}

		ResponseStructure<List<DeliveryAgent>> res = new ResponseStructure<>();

		res.setData(deliveryAgents);
		res.setMessage("data fetched successfully");
		res.setStatusCode(HttpStatus.FOUND.value());

		return res;
	}

	public ResponseStructure<DeliveryAgent> getById(Integer id) {

		Optional<DeliveryAgent> opt = deliveryAgentRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("id not found : " + id);
		}
	}

	public ResponseStructure<DeliveryAgent> getByVehicleNo(String vehicleNo) {

		Optional<DeliveryAgent> opt = deliveryAgentRepository.findByVehicleNumber(vehicleNo);

		if (opt.isPresent()) {

			ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("vehicle number not found : " + vehicleNo);
		}
	}

	public ResponseStructure<DeliveryAgent> getByContact(Long contact) {

		Optional<DeliveryAgent> opt = deliveryAgentRepository.findByContact(contact);

		if (opt.isPresent()) {

			ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("contact not found : " + contact);
		}
	}

	public ResponseStructure<List<DeliveryAgent>> getByRatingGreaterThan(double rating) {

		List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findByRatingGreaterThan(rating);

		if (!deliveryAgents.isEmpty()) {

			ResponseStructure<List<DeliveryAgent>> res = new ResponseStructure<>();

			res.setData(deliveryAgents);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("data not found greater than rating : " + rating);
		}
	}
	
	
	public ResponseStructure<List<DeliveryAgent>> sortByRatingInDescendingOrder() {

		List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findAll(Sort.by("rating").descending());

		if (!deliveryAgents.isEmpty()) {

			ResponseStructure<List<DeliveryAgent>> res = new ResponseStructure<>();

			res.setData(deliveryAgents);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new NoRecordAvailableException("data not found " );
		}
	}
	
	
	
	
	
	
	

	public ResponseStructure<DeliveryAgent> updateDeliveryAgent(DeliveryAgent deliveryAgent) {

		ResponseStructure<DeliveryAgent> res = new ResponseStructure<>();

		if (deliveryAgent.getId() == null) {

			res.setData(deliveryAgent);
			res.setMessage("id must be passed to update the data");
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());

			return res;
		}

		Optional<DeliveryAgent> opt = deliveryAgentRepository.findById(deliveryAgent.getId());

		if (opt.isPresent()) {

			DeliveryAgent saved = deliveryAgentRepository.save(deliveryAgent);

			res.setData(saved);
			res.setMessage("data updated successfully");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException(
					"there is no data present with given id to update : " + deliveryAgent.getId());
		}
	}

	public ResponseStructure<String> deleteDeliveryAgent(Integer id) {

		Optional<DeliveryAgent> opt = deliveryAgentRepository.findById(id);

		ResponseStructure<String> res = new ResponseStructure<>();

		if (opt.isPresent()) {

			deliveryAgentRepository.delete(opt.get());

			res.setData("Data deleted successfully");
			res.setMessage("Success");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException("Id does not exist : " + id);
		}
	}
	
	public ResponseStructure<DeliveryAgent> updateDeliveryAgentAvailability(
	        Integer id,
	        Boolean availabilityStatus) {

	    Optional<DeliveryAgent> opt =
	            deliveryAgentRepository.findById(id);

	    if (opt.isPresent()) {

	        DeliveryAgent deliveryAgent = opt.get();

	        deliveryAgent.setAvailabilityStatus(availabilityStatus);

	        DeliveryAgent saved =
	                deliveryAgentRepository.save(deliveryAgent);

	        ResponseStructure<DeliveryAgent> res =
	                new ResponseStructure<>();

	        res.setData(saved);
	        res.setMessage("availability updated successfully");
	        res.setStatusCode(HttpStatus.OK.value());

	        return res;

	    } else {

	        throw new IdNotFoundException(
	                "delivery agent not found with id : " + id);
	    }
	}
	
}
