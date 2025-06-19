package com.unla.tp_oo2_g16.services.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.unla.tp_oo2_g16.dtos.DisponibilidadDTO;
import com.unla.tp_oo2_g16.models.entities.Disponibilidad;
import com.unla.tp_oo2_g16.models.entities.Turno;

public interface DisponibilidadesServiceInterface {
	
    List<Disponibilidad> findAll();
    
    
    Disponibilidad findById(Integer id);
    Disponibilidad save(Disponibilidad disponibilidad);
    void deleteById(Integer id);
    
    List<LocalTime> findAllhorariosDisponibles();
    
    public List<String> findHorariosDisponibles(Integer servicioId);
        
    List<DisponibilidadDTO> findHorariosDisponiblesPorFecha(int servicioId, String fecha);

	Disponibilidad findDisponible(int servicioId, LocalDate fecha, LocalTime horario);

	void ocuparDisponibilidad(int servicioId, LocalDate fecha, LocalTime horario);

	Disponibilidad findNoDisponible(int servicioId, LocalDate fecha, LocalTime horario);
	
	void liberarDisponibilidad(int servicioId, LocalDate fecha, LocalTime horario);
	
	public void liberarDisponibilidadPorTurno(Turno turno);

	public List<DisponibilidadDTO> findFechasDisponibles(int servicioId);
}

