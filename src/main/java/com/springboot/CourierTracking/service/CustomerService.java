package com.springboot.CourierTracking.service;

import java.util.Iterator;
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
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.entity.Status;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.exception.OperationNotAllowedException;
import com.springboot.CourierTracking.exception.ResourceNotFoundException;
import com.springboot.CourierTracking.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public ResponseStructure<Customer> createCustomer(Customer customer) {

		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new DataIntegrityViolationException("mail id  already exist");
		}
		if (customer.getContact() != null) {

			String contactStr = String.valueOf(customer.getContact());

			if (contactStr.length() != 10) {
				throw new IllegalArgumentException("Contact must be exactly 10 digits");
			}

			if (customerRepository.existsByContact(customer.getContact())) {
				throw new DataIntegrityViolationException("Contact already exists");
			}
		}

		ResponseStructure<Customer> res = new ResponseStructure<>();

		Customer saved = customerRepository.save(customer);
		res.setData(saved);
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("data saved succesfully");
		return res;

	}

	public ResponseStructure<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();

		if (customers.isEmpty()) {
			throw new NoRecordAvailableException("there is no customer in DB");
		} else {
			ResponseStructure<List<Customer>> res = new ResponseStructure<>();
			res.setData(customers);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		}
	}

	public ResponseStructure<Customer> getById(Integer id) {
		Optional<Customer> opt = customerRepository.findById(id);

		if (opt.isPresent()) {
			ResponseStructure<Customer> res = new ResponseStructure<>();
			;
			res.setMessage("data fetched succesfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		} else {
			throw new IdNotFoundException("id not found :" + id);
		}

	}

	public ResponseStructure<Customer> getByEmail(String email) {
		Optional<Customer> opt = customerRepository.findByEmail(email);

		if (opt.isPresent()) {
			ResponseStructure<Customer> res = new ResponseStructure<>();
			;
			res.setData(opt.get());
			res.setMessage("data fetched succesfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		} else {
			throw new IdNotFoundException("email not found :" + email);
		}

	}

	public ResponseStructure<Customer> updateCustomer(Customer customer) {

		ResponseStructure<Customer> res = new ResponseStructure<Customer>();

		if (customer.getId() == null) {
			res.setData(customer);
			res.setMessage("id must be passed to update the data");
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return res;

		}

		Optional<Customer> opt = customerRepository.findById(customer.getId());

		if (opt.isPresent()) {
			Customer saved = customerRepository.save(customer);
			res.setData(saved);
			res.setMessage("data updated successfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		} else {
			throw new IdNotFoundException("there is no data present with given id to update : " + customer.getId());
		}

	}

	public ResponseStructure<String> deleteCustomer(Integer id) {

	    Optional<Customer> opt = customerRepository.findById(id);

	    ResponseStructure<String> res = new ResponseStructure<>();

	    if (opt.isPresent()) {

	        List<Shipment> shipments = opt.get().getShipment();

	        if (!shipments.isEmpty()) {

	            for (Shipment shipment : shipments) {

	            	if (shipment.getStatus() != Status.DELIVERED
	            	        && shipment.getStatus() != Status.CANCELLED) {

	            	

	                    throw new OperationNotAllowedException(
	                            "Customer cannot be deleted as there is an active shipment");
	                }
	            }
	        }

	        customerRepository.delete(opt.get());

	        res.setData("Data deleted successfully");
	        res.setMessage("Success");
	        res.setStatusCode(HttpStatus.OK.value());

	        return res;

	    } else {
	        throw new IdNotFoundException("Id does not exist: " + id);
	    }
	}
	
	
	public ResponseStructure<Customer> getByContact(Long contact) {
		Optional<Customer> opt = customerRepository.findByContact(contact);

		if (opt.isPresent()) {
			ResponseStructure<Customer> res = new ResponseStructure<>();
			;
			res.setData(opt.get());
			res.setMessage("data fetched succesfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		} else {
			throw new IdNotFoundException("contact not found :" + contact);
		}

	}

	public ResponseStructure<Page<Customer>> getByPaginationAndSorting(int pageNumber, int pageSize, String fieldName) {
		Page<Customer> page = customerRepository
				.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(fieldName).ascending()));

		if (!page.isEmpty()) {
			ResponseStructure<Page<Customer>> res = new ResponseStructure<>();
			;
			res.setData(page);
			res.setMessage("data fetched succesfully");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		} else {
			throw new IdNotFoundException("data not found ");
		}

	}

}
