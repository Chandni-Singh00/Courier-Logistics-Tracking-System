package com.springboot.CourierTracking.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private String trackingNumber;
	private String source;
	private String destination;
	private Double distance;
	private Double weight;
	
	@CreationTimestamp
	private LocalDateTime shipmentDateTime;
	
	private LocalDate deliveryDate;
	
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	
	@ManyToOne
	@JoinColumn
	private Customer customer;
	
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn
	private Payment payment;
	
	
	@ManyToOne
	@JoinColumn
	private DeliveryAgent deliveryAgent;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private PackageEntity packageEntity;
	
	@ManyToOne
	@JoinColumn
	private Warehouse warehouse;
	
	
	@JsonIgnore
	@OneToMany(mappedBy ="shipment",cascade=CascadeType.ALL)
	private List<TrackingHistory> trackingHistories;
	
	
	
	

}
