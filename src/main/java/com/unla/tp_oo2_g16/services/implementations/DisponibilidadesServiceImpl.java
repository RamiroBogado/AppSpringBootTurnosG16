package com.unla.tp_oo2_g16.services.implementations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.models.entities.Disponibilidad;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.repositories.DisponibilidadRepository;
import com.unla.tp_oo2_g16.services.interfaces.DisponibilidadesServiceInterface;

@Service
public class DisponibilidadesServiceImpl implements DisponibilidadesServiceInterface {

    @Autowired
    private DisponibilidadRepository disponibilidadRepository;
   
    @Override
    public List<Disponibilidad> findAll() {
        return disponibilidadRepository.findAll();
    }

    @Override
    public Disponibilidad findById(Integer id) {
        return disponibilidadRepository.findById(id).orElse(null);
    }

    @Override
    public Disponibilidad save(Disponibilidad disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    @Override
    public void deleteById(Integer id) {
        disponibilidadRepository.deleteById(id);
    }
    
    @Override
    public List<LocalTime> findAllhorariosDisponibles() {
        List<Disponibilidad> disponibilidades = disponibilidadRepository.findAll();

        Set<LocalTime> horariosSet = new HashSet<>();

        for (Disponibilidad d : disponibilidades) {
            LocalTime start = d.getHorarioInicio();
            LocalTime end = d.getHorarioFin();

            // Generar horarios de 1 hora entre start y end (inclusive start, exclusivo end)
            LocalTime time = start;
            while (time.isBefore(end)) {
                horariosSet.add(time);
                time = time.plusHours(1);
            }
        }

        // Convertir a lista y ordenar
        List<LocalTime> horarios = new ArrayList<>(horariosSet);
        horarios.sort(LocalTime::compareTo);

        return horarios;
    }
    
    @Override
    public List<String> findHorariosDisponibles(Integer servicioId) {
        return disponibilidadRepository.findHorariosDisponiblesByServicio(servicioId).stream()
                .map(LocalTime::toString)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> findFechasDisponibles(int servicioId) {
        List<LocalDate> fechas = disponibilidadRepository.findFechasDisponiblesByServicio(servicioId);

        // Mapear LocalDate a String yyyy-MM-dd para pasar a Thymeleaf y flatpickr
        return fechas.stream()
                     .map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                     .collect(Collectors.toList());
    }
    
    @Override
    public List<String> findHorariosDisponiblesPorFecha(int servicioId, String fecha) {
        LocalDate fechaParsed = LocalDate.parse(fecha); // formato ISO "yyyy-MM-dd"
        List<LocalTime> horarios = disponibilidadRepository.findHorariosDisponiblesPorFecha(servicioId, fechaParsed);
        return horarios.stream()
                       .map(LocalTime::toString) // "09:00", "10:30", etc.
                       .collect(Collectors.toList());
    }
    
    @Override
    public Disponibilidad findDisponible(int servicioId, LocalDate fecha, LocalTime horario) {
        return disponibilidadRepository.findDisponible(servicioId, fecha, horario);
    }

    @Override
    public void ocuparDisponibilidad(int servicioId, LocalDate fecha, LocalTime horario) {
        Disponibilidad disponibilidad = disponibilidadRepository.findDisponible(servicioId, fecha, horario);
        
        disponibilidad.setEstado(true);
        disponibilidadRepository.save(disponibilidad);

    }
    
    @Override
    public Disponibilidad findNoDisponible(int servicioId, LocalDate fecha, LocalTime horario) {
        return disponibilidadRepository.findNoDisponible(servicioId, fecha, horario);
    }
    
    @Override
    public void liberarDisponibilidad(int servicioId, LocalDate fecha, LocalTime horario) {
        Disponibilidad disponibilidad = disponibilidadRepository.findNoDisponible(servicioId, fecha, horario);

        disponibilidad.setEstado(false);
        disponibilidadRepository.save(disponibilidad);

    }
    
    public void liberarDisponibilidadPorTurno(Turno turno) {
        Servicio servicio = turno.getServicio();
        LocalDate fecha = turno.getFechaHora().toLocalDate();
        LocalTime hora = turno.getFechaHora().toLocalTime();

        liberarDisponibilidad(servicio.getIdServicio(), fecha, hora);

    }






}
