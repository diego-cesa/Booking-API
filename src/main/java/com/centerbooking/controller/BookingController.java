package com.centerbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centerbooking.request.AppointmentRequest;
import com.centerbooking.response.AppointmentResponse;
import com.centerbooking.response.CenterResponse;
import com.centerbooking.service.BookingService;

@RestController
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@RequestMapping("/test")
	public @ResponseBody String test() {
		return "Test";
	}
	
	@RequestMapping("/loadCenters")
	public @ResponseBody String loadCenters(){
		return bookingService.loadCenters();
	}

	@RequestMapping("/center")
	public @ResponseBody List<CenterResponse> centerList() {
		return bookingService.getCenterList();
	}
	
	@RequestMapping("/center/{id}")
	public @ResponseBody CenterResponse center(@PathVariable(value="id") String id) {
		return bookingService.getCenter(Integer.parseInt(id));
	}
	
	@RequestMapping("/appointments")
	public @ResponseBody List<AppointmentResponse> appointmentList() {
		return bookingService.getAppointmentList();
	}
	
	@RequestMapping("/appointments/{id}")
	public @ResponseBody AppointmentResponse appointments(@PathVariable(value="id") String id) {
		return bookingService.getAppointment(Integer.parseInt(id));
	}
	
	@RequestMapping(value = "/appointments", method = RequestMethod.POST)
	public @ResponseBody AppointmentResponse addAppointment(@RequestBody AppointmentRequest appointmentRequest) {
		return bookingService.addAppointment(appointmentRequest);
	}
	
	@RequestMapping(value = "/appointments", method = RequestMethod.PUT)
	public @ResponseBody AppointmentResponse uppdateAppointment(@RequestBody AppointmentRequest appointmentRequest) {
		return bookingService.updateAppointment(appointmentRequest);
	}
	
	@RequestMapping(value = "/appointments", method = RequestMethod.DELETE)
	public @ResponseBody String deleteAppointment(@RequestBody AppointmentRequest appointmentRequest) {
		return bookingService.deleteAppointment(appointmentRequest);
	}
	
}
    
