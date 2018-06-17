package com.centerbooking.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CenterType {

	@Id
	private Integer id;
	
	private String value;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CenterType [id=" + id + ", value=" + value + "]";
	}
}
