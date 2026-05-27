package com.springboot.CourierTracking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.entity.Warehouse;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.exception.OperationNotAllowedException;
import com.springboot.CourierTracking.repository.WarehouseRepository;

@Service
public class WarehouseService {
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	public ResponseStructure<Warehouse> createWarehouse(Warehouse warehouse) {

	    if (warehouse.getContact() != null) {

	        String contactStr = String.valueOf(warehouse.getContact());

	        if (contactStr.length() != 10) {
	            throw new IllegalArgumentException("Contact must be exactly 10 digits");
	        }

	        if (warehouseRepository.existsByContact(warehouse.getContact())) {
	            throw new DataIntegrityViolationException("Contact already exists");
	        }
	    }

	    ResponseStructure<Warehouse> res = new ResponseStructure<>();

	    Warehouse saved = warehouseRepository.save(warehouse);

	    res.setData(saved);
	    res.setStatusCode(HttpStatus.CREATED.value());
	    res.setMessage("Warehouse saved successfully");

	    return res;
	}
	
	public ResponseStructure<List<Warehouse>> getAllWarehouses() {

	    List<Warehouse> warehouses = warehouseRepository.findAll();

	    if (warehouses.isEmpty()) {
	        throw new NoRecordAvailableException("there is no warehouse in DB");
	    } else {

	        ResponseStructure<List<Warehouse>> res = new ResponseStructure<>();

	        res.setData(warehouses);
	        res.setMessage("data fetched successfully");
	        res.setStatusCode(HttpStatus.FOUND.value());

	        return res;
	    }
	}

	
	public ResponseStructure<Warehouse> getById(Integer id) {

	    Optional<Warehouse> opt = warehouseRepository.findById(id);

	    if (opt.isPresent()) {

	        ResponseStructure<Warehouse> res = new ResponseStructure<>();

	        res.setData(opt.get());
	        res.setMessage("data fetched successfully");
	        res.setStatusCode(HttpStatus.FOUND.value());

	        return res;

	    } else {
	        throw new IdNotFoundException("id not found : " + id);
	    }
	}
	
	public ResponseStructure<Warehouse> getByLocation(String location) {

	    Optional<Warehouse> opt = warehouseRepository.findByLocation(location);

	    if (opt.isPresent()) {

	        ResponseStructure<Warehouse> res = new ResponseStructure<>();

	        res.setData(opt.get());
	        res.setMessage("data fetched successfully");
	        res.setStatusCode(HttpStatus.FOUND.value());

	        return res;

	    } else {
	        throw new IdNotFoundException("location not found : " + location);
	    }
	}
	
	
	public ResponseStructure<List<Warehouse>> getByCapacityGreaterThan(int capacity) {

	    List<Warehouse> warehouses = warehouseRepository.findByCapacityGreaterThan(capacity);

	    if (!warehouses.isEmpty()) {

	        ResponseStructure<List<Warehouse>> res = new ResponseStructure<>();

	        res.setData(warehouses);
	        res.setMessage("data fetched successfully");
	        res.setStatusCode(HttpStatus.FOUND.value());

	        return res;

	    } else {
	        throw new IdNotFoundException("data not found greater than capacity :"+  capacity);
	    }
	}
	
	
	public ResponseStructure<Warehouse> updateWarehouse(Warehouse warehouse) {

	    ResponseStructure<Warehouse> res = new ResponseStructure<>();

	    if (warehouse.getId() == null) {

	        res.setData(warehouse);
	        res.setMessage("id must be passed to update the data");
	        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

	        return res;
	    }

	    Optional<Warehouse> opt = warehouseRepository.findById(warehouse.getId());

	    if (opt.isPresent()) {

	        Warehouse saved = warehouseRepository.save(warehouse);

	        res.setData(saved);
	        res.setMessage("data updated successfully");
	        res.setStatusCode(HttpStatus.OK.value());

	        return res;

	    } else {
	        throw new IdNotFoundException(
	                "there is no data present with given id to update : " + warehouse.getId());
	    }
	}
	
	public ResponseStructure<String> deleteWarehouse(Integer id) {

	    Optional<Warehouse> opt = warehouseRepository.findById(id);

	    ResponseStructure<String> res = new ResponseStructure<>();

	    if (opt.isPresent()) {

	        List<Shipment> shipments = opt.get().getShipments();

	        if (!shipments.isEmpty()) {

	            throw new OperationNotAllowedException(
	                    "Warehouse cannot be deleted because shipments exist");
	        }

	        warehouseRepository.delete(opt.get());

	        res.setData("Data deleted successfully");
	        res.setMessage("Success");
	        res.setStatusCode(HttpStatus.OK.value());

	        return res;

	    } else {
	        throw new IdNotFoundException("Id does not exist : " + id);
	    }
	}
}

