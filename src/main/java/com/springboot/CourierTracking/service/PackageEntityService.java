package com.springboot.CourierTracking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.CourierTracking.dto.ResponseStructure;
import com.springboot.CourierTracking.entity.PackageEntity;
import com.springboot.CourierTracking.entity.PackageType;
import com.springboot.CourierTracking.entity.Shipment;
import com.springboot.CourierTracking.exception.IdNotFoundException;
import com.springboot.CourierTracking.exception.NoRecordAvailableException;
import com.springboot.CourierTracking.repository.PackageEntityRepository;

@Service
public class PackageEntityService {
	@Autowired
	private PackageEntityRepository packageRepository;

	public ResponseStructure<List<PackageEntity>> getAllPackages() {

		List<PackageEntity> packages = packageRepository.findAll();

		if (packages.isEmpty()) {
			throw new NoRecordAvailableException("there is no package in DB");
		}

		ResponseStructure<List<PackageEntity>> res = new ResponseStructure<>();

		res.setData(packages);
		res.setMessage("data fetched successfully");
		res.setStatusCode(HttpStatus.FOUND.value());

		return res;
	}

	public ResponseStructure<PackageEntity> getById(Integer id) {

		Optional<PackageEntity> opt = packageRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<PackageEntity> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("id not found : " + id);
		}
	}

	public ResponseStructure<PackageEntity> getByShipment(Integer shipment_id) {

		Optional<PackageEntity> opt = packageRepository.findByShipment_Id(shipment_id);

		if (opt.isPresent()) {

			ResponseStructure<PackageEntity> res = new ResponseStructure<>();

			res.setData(opt.get());
			res.setMessage("data fetched successfully");
			res.setStatusCode(HttpStatus.FOUND.value());

			return res;

		} else {

			throw new IdNotFoundException("shipment id not found : " + shipment_id);
		}
	}

	public ResponseStructure<PackageEntity> updatePackage(PackageEntity packageEntity) {

		ResponseStructure<PackageEntity> res = new ResponseStructure<>();

		if (packageEntity.getId() == null) {

			res.setData(packageEntity);
			res.setMessage("id must be passed to update package");
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());

			return res;
		}

		Optional<PackageEntity> opt = packageRepository.findById(packageEntity.getId());

		if (opt.isPresent()) {

			PackageEntity saved = packageRepository.save(packageEntity);

			res.setData(saved);
			res.setMessage("package updated successfully");
			res.setStatusCode(HttpStatus.OK.value());

			return res;

		} else {

			throw new IdNotFoundException("package not found with id : " + packageEntity.getId());
		}
	}
}
