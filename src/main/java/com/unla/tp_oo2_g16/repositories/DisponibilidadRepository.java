package com.unla.tp_oo2_g16.repositories;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Disponibilidad;
import com.unla.tp_oo2_g16.models.entities.Servicio;

@Repository
public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {
	
	@Query("SELECT d.horarioInicio FROM Disponibilidad d WHERE d.servicio.idServicio = :idServicio AND d.estado = false")
	List<LocalTime> findHorariosDisponiblesByServicio(@Param("idServicio") Integer idServicio);

	@Query("SELECT DISTINCT d.fecha FROM Disponibilidad d WHERE d.servicio.id = :servicioId AND d.estado = false AND d.fecha >= CURRENT_DATE ORDER BY d.fecha")
	List<LocalDate> findFechasDisponiblesByServicio(@Param("servicioId") int servicioId);
	
	@Query("SELECT DISTINCT d.horarioInicio FROM Disponibilidad d " +
		       "WHERE d.servicio.id = :servicioId " +
		       "AND d.fecha = :fecha " +
		       "AND d.estado = false " +
		       "ORDER BY d.horarioInicio")
		List<LocalTime> findHorariosDisponiblesPorFecha(@Param("servicioId") int servicioId,
		                                                 @Param("fecha") LocalDate fecha);
	
	@Query("SELECT d FROM Disponibilidad d WHERE d.servicio.idServicio = :servicioId AND d.fecha = :fecha AND d.horarioInicio = :horario AND d.estado = false")
	Disponibilidad findDisponible(int servicioId, LocalDate fecha, LocalTime horario);
	
	@Query("SELECT d FROM Disponibilidad d WHERE d.servicio.idServicio = :servicioId AND d.fecha = :fecha AND d.horarioInicio = :horario AND d.estado = TRUE")
	Disponibilidad findNoDisponible(int servicioId, LocalDate fecha, LocalTime horario);

	List<Disponibilidad> findByServicioAndEstado(Servicio servicio, boolean estado);
	
	List<Disponibilidad> findByServicioAndFechaAndEstado(Servicio servicio, LocalDate fecha, boolean estado);



}
