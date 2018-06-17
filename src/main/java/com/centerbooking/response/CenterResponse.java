package com.centerbooking.response;

public class CenterResponse {
	private final Integer 	id;
	private final String 	name;
	private final String 	streetAddres;
	private final String 	centerTypeValue;
	
	
	public CenterResponse(Integer id, String name, String streetAddres, String centerTypeValue) {
		super();
		this.id = id;
		this.name = name;
		this.streetAddres = streetAddres;
		this.centerTypeValue = centerTypeValue;
	}


	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getStreetAddres() {
		return streetAddres;
	}


	public String getCenterTypeValue() {
		return centerTypeValue;
	}
}
