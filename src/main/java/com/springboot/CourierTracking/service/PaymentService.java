package com.springboot.CourierTracking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Payment;
import com.springboot.CourierTracking.entity.PaymentStatus;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.exception.OperationNotAllowedException;
import com.springboot.CourierTracking.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	public ResponseStructure<List<Payment>> getAllPayments() {

		List<Payment> payments = paymentRepository.findAll();

		if (payments.isEmpty()) {
			throw new NoRecordAvailableException("there is no payment in DB");
		}

		ResponseStructure<List<Payment>> res = new ResponseStructure<>();

		res.setData(payments);
		res.setMessage("data fetched successfully");
		res.setStatusCode(HttpStatus.FOUND.value());

		return res;
	}

	public ResponseStructure<Payment> getById(Integer id) {

		Optional<Payment> opt = paymentRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<Payment> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {
			throw new IdNotFoundException("id not found : " + id);
		}
	}

	public ResponseStructure<Payment> updatePaymentStatus(Integer id, PaymentStatus paymentStatus) {

		Optional<Payment> opt = paymentRepository.findById(id);

		if (opt.isPresent()) {

			Payment payment = opt.get();

			payment.setPaymentStatus(paymentStatus);

			Payment saved = paymentRepository.save(payment);

			ResponseStructure<Payment> res = new ResponseStructure<>();

			res.setData(saved);
			res.setMessage("payment status updated successfully");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException("payment not found with id : " + id);
		}
	}

	public ResponseStructure<String> cancelPayment(Integer id) {

		Optional<Payment> opt = paymentRepository.findById(id);

		if (opt.isPresent()) {

			Payment payment = opt.get();

			if (payment.getPaymentStatus() == PaymentStatus.PAID) {

				throw new OperationNotAllowedException("Paid payment cannot be cancelled");
			}

			payment.setPaymentStatus(PaymentStatus.FAILED);

			paymentRepository.save(payment);

			ResponseStructure<String> res = new ResponseStructure<>();

			res.setData("Payment cancelled successfully");
			res.setMessage("Success");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException("payment not found with id : " + id);
		}
	}

}
