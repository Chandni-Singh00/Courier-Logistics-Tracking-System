package com.springboot.CourierTracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Payment;
import com.springboot.CourierTracking.entity.PaymentStatus;
import com.springboot.CourierTracking.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {

		return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getById(@PathVariable Integer id) {

		return new ResponseEntity<>(paymentService.getById(id), HttpStatus.OK);
	}

	@PatchMapping("/status/{id}/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer id,
			@PathVariable PaymentStatus status) {

		return new ResponseEntity<>(paymentService.updatePaymentStatus(id, status), HttpStatus.OK);
	}

	@PatchMapping("/cancel/{id}")
	public ResponseEntity<ResponseStructure<String>> cancelPayment(@PathVariable Integer id) {

		return new ResponseEntity<>(paymentService.cancelPayment(id), HttpStatus.OK);
	}

}
