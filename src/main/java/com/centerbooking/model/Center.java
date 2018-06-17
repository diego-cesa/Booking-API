package com.centerbooking.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Center {
	@Id
	private Integer id;
	
	private String 	name;
	private String 	streetAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private CenterType centerType;	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public CenterType getCenterType() {
		return centerType;
	}
	public void setCenterType(CenterType centerType) {
		this.centerType = centerType;
	}
	@Override
	public String toString() {
		return "Center [id=" + id + ", name=" + name + ", streetAddress=" + streetAddress + ", centerType=" + centerType
				+ "]";
	}
}
