package com.centerbooking.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.centerbooking.error.JsonLoadFailed;
import com.centerbooking.error.ResourceNotFoundException;
import com.centerbooking.model.Appointment;
import com.centerbooking.model.AppointmentRepository;
import com.centerbooking.model.Center;
import com.centerbooking.model.CenterRepository;
import com.centerbooking.model.CenterType;
import com.centerbooking.model.CenterTypeRepository;
import com.centerbooking.request.AppointmentRequest;
import com.centerbooking.response.AppointmentResponse;
import com.centerbooking.response.CenterResponse;

public class BookingService {
	
	@Autowired
	CenterTypeRepository centerTypeRepository;

	@Autowired
	CenterRepository centerRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	private static final String JSON_FILE = "/centers.json";
	
	public JSONObject readJsonFile() {
		
		JSONParser parser = new JSONParser();
		
		try {
			InputStream is = getClass().getResourceAsStream(JSON_FILE);
			Reader reader = new InputStreamReader(is);
			return (JSONObject)  parser.parse(reader);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String loadCenters(){
		JSONObject centersInfo = readJsonFile();
		
		if(centersInfo == null) {
			throw new JsonLoadFailed("Error: unable to read json file: " + JSON_FILE);
		}
		
		//Load CenterType
		JSONArray centerTypes = (JSONArray)centersInfo.get("CenterTypes");
		List<CenterType> centerTypeList = new ArrayList<CenterType>();
		
		for(Object centerTypeObj: centerTypes) {
			JSONObject centerType = (JSONObject) centerTypeObj;
			
			CenterType newType = new CenterType();
			newType.setId(Integer.parseInt((String) centerType.get("Id")));
			newType.setValue((String) centerType.get("Value"));
		
			centerTypeRepository.save(newType);
			centerTypeList.add(newType);
		}
		
		//Load Center
		JSONArray centers = (JSONArray)centersInfo.get("Centers");
		for(Object centerObject: centers) {
			JSONObject center = (JSONObject) centerObject;
			
			Center newCenter = new Center();
			newCenter.setId(Integer.parseInt((String) center.get("Id")));
			newCenter.setName((String) center.get("Name"));
			newCenter.setStreetAddress((String) center.get("StreetAddress"));
			
			for(CenterType centerType: centerTypeList) {
				if(centerType.getId() == Integer.parseInt((String) center.get("CenterTypeId"))) {
					newCenter.setCenterType(centerType);
					break;
				}
			}
			
			centerRepository.save(newCenter);
		}
		
		return "Service Centers Info Saved!";
	}
	
	
	public CenterResponse getCenter(Integer id){
		try {
			Center center = centerRepository.findById(id).get();
			return new CenterResponse(center.getId(), center.getName(), center.getStreetAddress(), center.getCenterType().getValue());
		}
		catch(NoSuchElementException e) {
			throw new ResourceNotFoundException("Center not found. Id: " + id);
		}	
	}
	
	public List<CenterResponse> getCenterList() {
		
		List<CenterResponse> centerListResponse = new ArrayList<CenterResponse>();
		Iterable<Center> centerList = centerRepository.findAll();
	
		for(Center center: centerList) {
			centerListResponse.add(new CenterResponse(center.getId(), center.getName(), center.getStreetAddress(), center.getCenterType().getValue()));
		}
		
		if(centerListResponse.size() == 0) {
			throw new ResourceNotFoundException("No center found. Please make the request: /loadCenters");
		}
		
		
		return centerListResponse;
		
	}
	
	public AppointmentResponse getAppointment(Integer id) {
		try {
			Appointment appointment = appointmentRepository.findById(id).get();
			CenterResponse centerResponse = new CenterResponse(appointment.getCenter().getId(), appointment.getCenter().getName(), 
					appointment.getCenter().getStreetAddress(), appointment.getCenter().getCenterType().getValue());
			
			return new AppointmentResponse(appointment.getId(), appointment.getClientFullName(), appointment.getDate(), centerResponse);
		}
		catch(NoSuchElementException e) {
			throw new ResourceNotFoundException("Appointment not found. Id: " + id);
		}
	}
	
	public List<AppointmentResponse> getAppointmentList(){
		
		List<AppointmentResponse> appointmentListResponse = new ArrayList<AppointmentResponse>();
		
		Iterable<Appointment> appointmentList = appointmentRepository.findAll();
		
		for(Appointment appointment: appointmentList) {
			
			CenterResponse centerResponse = new CenterResponse(appointment.getCenter().getId(), appointment.getCenter().getName(), 
					appointment.getCenter().getStreetAddress(), appointment.getCenter().getCenterType().getValue());
			
			appointmentListResponse.add(new AppointmentResponse(appointment.getId(), appointment.getClientFullName(), appointment.getDate(), centerResponse));
		}
		
		if(appointmentListResponse.size() == 0) {
			throw new ResourceNotFoundException("No appointment found.");
		}
		
		return appointmentListResponse;
	}
	
	public AppointmentResponse addAppointment(AppointmentRequest appointmentRequest) {
		
		//Check center's availability
		Appointment dateCheck = appointmentRepository.findAppointmentByDate(appointmentRequest.getCenterId(), appointmentRequest.getDate());
		if(dateCheck != null) {
			throw new ResourceNotFoundException("Center already booked for this date.");
		}
		
		Appointment appointment = new Appointment();
		appointment.setClientFullName(appointmentRequest.getClientFullName());
		appointment.setDate(appointmentRequest.getDate());
		
		try {
			Center center = centerRepository.findById(appointmentRequest.getCenterId()).get();
			appointment.setCenter(center);
			appointmentRepository.save(appointment);
			
			CenterResponse centerResponse = new CenterResponse(appointment.getCenter().getId(), appointment.getCenter().getName(), 
					appointment.getCenter().getStreetAddress(), appointment.getCenter().getCenterType().getValue());
				
			return new AppointmentResponse(appointment.getId(), appointment.getClientFullName(), appointment.getDate(), centerResponse);
		}
		catch(NoSuchElementException e) {
			throw new ResourceNotFoundException("Center not found. Id: " + appointmentRequest.getCenterId());
		}
	}
	
	public AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest) {
		
		//Check center's availability
		Appointment dateCheck = appointmentRepository.findAppointmentByDate(appointmentRequest.getCenterId(), appointmentRequest.getDate());
		if(dateCheck != null && dateCheck.getId() != appointmentRequest.getId()) {
			throw new ResourceNotFoundException("Center already booked for this date.");
		}
		
		Center center = null;
		try {
			center = centerRepository.findById(appointmentRequest.getCenterId()).get();
		}
		catch(NoSuchElementException e) {
			throw new ResourceNotFoundException("Center not found. Id: " + appointmentRequest.getCenterId());
		}
		
		try {
			Appointment appointment = dateCheck != null ? dateCheck : appointmentRepository.findById(appointmentRequest.getId()).get();
			
			appointment.setClientFullName(appointmentRequest.getClientFullName());
			appointment.setDate(appointmentRequest.getDate());
			appointment.setCenter(center);
			appointmentRepository.save(appointment);
			
			CenterResponse centerResponse = new CenterResponse(appointment.getCenter().getId(), appointment.getCenter().getName(), 
					appointment.getCenter().getStreetAddress(), appointment.getCenter().getCenterType().getValue());
			return new AppointmentResponse(appointment.getId(), appointment.getClientFullName(), appointment.getDate(), centerResponse);
		}
		catch(NoSuchElementException e) {
			throw new ResourceNotFoundException("Appointment not found. Id: " + appointmentRequest.getId());
		}		
	}
	
	public String deleteAppointment(AppointmentRequest appointmentRequest) {
		
		try {
		appointmentRepository.deleteById(appointmentRequest.getId());
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Appointment not found. Id: " + appointmentRequest.getId());
		}
		
		return "Appointment deleted. Id: " + appointmentRequest.getId();
	}
}
