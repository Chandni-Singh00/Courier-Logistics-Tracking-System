package com.springboot.CourierTracking.service;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.TrackingHistory;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.repository.TrackingHistoryRepository;

@Service
public class TrackingHistoryService {
	@Autowired
	private TrackingHistoryRepository trackRepository;

	public ResponseStructure<List<TrackingHistory>> getAllTrackingHistory() {

		List<TrackingHistory> trackingHistories = trackRepository.findAll();

		if (trackingHistories.isEmpty()) {

			throw new NoRecordAvailableException("there is no tracking history in DB");
		}

		ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();

		res.setData(trackingHistories);
		res.setMessage("data fetched successfully");
		res.setStatusCode(HttpStatus.FOUND.value());

		return res;
	}

	public ResponseStructure<TrackingHistory> getById(Integer id) {

		Optional<TrackingHistory> opt = trackRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<TrackingHistory> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("id not found : " + id);
		}
	}

	public ResponseStructure<TrackingHistory> getByTrackingNumber(String trackNumber) {

		Optional<TrackingHistory> opt = trackRepository.findByShipment_TrackingNumber(trackNumber);

		if (opt.isPresent()) {

			ResponseStructure<TrackingHistory> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("trackNumber not found : " + trackNumber);
		}
	}

	public ResponseStructure<List<TrackingHistory>> getByStatus(Status status) {

		List<TrackingHistory> track = trackRepository.findByStatus(status);

		if (track.isEmpty()) {

			ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();

			res.setData(track);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("status not found : " + status);
		}
	}

	public ResponseStructure<List<TrackingHistory>> getByShipment(Integer shipmentId) {

		List<TrackingHistory> trackingHistories = trackRepository.findByShipment_Id(shipmentId);

		if (!trackingHistories.isEmpty()) {

			ResponseStructure<List<TrackingHistory>> res = new ResponseStructure<>();

			res.setData(trackingHistories);
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("tracking history not found for shipment id : " + shipmentId);
		}
	}

	public ResponseStructure<TrackingHistory> updateTrackingHistory(TrackingHistory trackingHistory) {

		ResponseStructure<TrackingHistory> res = new ResponseStructure<>();

		if (trackingHistory.getId() == null) {

			res.setData(trackingHistory);
			res.setMessage("id must be passed to update tracking history");
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());

			return res;
		}

		Optional<TrackingHistory> opt = trackRepository.findById(trackingHistory.getId());

		if (opt.isPresent()) {

			TrackingHistory saved = trackRepository.save(trackingHistory);

			res.setData(saved);
			res.setMessage("tracking history updated successfully");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException("tracking history not found with id : " + trackingHistory.getId());
		}
	}

}
