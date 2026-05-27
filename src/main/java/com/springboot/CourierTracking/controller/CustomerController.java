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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Customer;
import com.springboot.CourierTracking.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	
	@PostMapping()
	public ResponseEntity<ResponseStructure<Customer>> createCustomer(@RequestBody Customer customer ){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.createCustomer(customer),HttpStatus.CREATED);
	}

	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers(){
		return new ResponseEntity<ResponseStructure<List<Customer>>>(customerService.getAllCustomers(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Customer>> getById(@PathVariable Integer id ){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getById(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ResponseStructure<Customer>> getByEmail(@PathVariable String email ){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getByEmail(email),HttpStatus.OK);
	}
	
	
	@PutMapping()
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody Customer customer ){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.updateCustomer(customer),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(@PathVariable Integer id ){
		return new ResponseEntity<ResponseStructure<String>>(customerService.deleteCustomer(id),HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Customer>> getByContact(@PathVariable Long contact ){
		return new ResponseEntity<ResponseStructure<Customer>>(customerService.getByContact(contact),HttpStatus.OK);
	}
	
	@GetMapping("/page/{pageNumber}/{pageSize}/{fieldName}")
	public ResponseEntity<ResponseStructure<Page<Customer>>> getByPaginationAndSorting(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String fieldName ){
		return new ResponseEntity<ResponseStructure<Page<Customer>>>(customerService.getByPaginationAndSorting(pageNumber, pageSize, fieldName),HttpStatus.OK);
	}
	
}
