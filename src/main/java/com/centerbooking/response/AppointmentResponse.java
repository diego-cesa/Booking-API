package com.centerbooking.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentResponse {
	private final Integer 			id;
	private final String 			clientFullName;
	private final Date 				date;
	private final CenterResponse    center;
	
	public AppointmentResponse(Integer id, String clientFullName, Date date, CenterResponse center) {
		super();
		this.id = id;
		this.clientFullName = clientFullName;
		this.date = date;
		this.center = center;
	}

	public Integer getId() {
		return id;
	}

	public String getClientFullName() {
		return clientFullName;
	}

	public String getDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	    return f.format(date);
	}

	public CenterResponse getCenter() {
		return center;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", clientFullName=" + clientFullName + ", date=" + date + ", centerId=" + center.getId()
				+ "]";
	}
}
