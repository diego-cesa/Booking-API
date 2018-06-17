package com.centerbooking.model;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{

	@Query("SELECT a FROM Appointment a inner join a.center c WHERE c.id = :id AND a.date = :date")
	public Appointment findAppointmentByDate(@Param("id") Integer id, @Param("date") Date date);
}
