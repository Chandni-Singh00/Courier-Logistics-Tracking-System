package com.springboot.CourierTracking.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeliveryAgent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Column(unique=true)
	private Long contact;
	@Column(unique=true)
	private String vehicleNumber;
	
	private Boolean availabilityStatus;
	private Double rating;
	
	@JsonIgnore
	@OneToMany(mappedBy="deliveryAgent")
	private List<Shipment> shipments;
	
	
}
