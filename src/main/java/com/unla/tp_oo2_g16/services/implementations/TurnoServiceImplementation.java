package com.unla.tp_oo2_g16.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.repositories.TurnoRepository;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoServiceImplementation implements TurnoServiceInterface {
    
    private final TurnoRepository turnoRepository;
    
    @Override
    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }
    
    @Override
    public Turno findById(Integer id) {
        return turnoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Turno save(Turno turno) {
        return turnoRepository.save(turno);
    }
    
    @Override
    public void deleteById(Integer id) {
        turnoRepository.deleteById(id);
    }
    
    @Override
    public List<Turno> findByCliente(Integer clienteId) {
        return turnoRepository.findByCliente(clienteId);
    }
       
    @Override
    public List<Turno> findByServicio(Integer servicioId) {
        return turnoRepository.findByServicio(servicioId);
    }
    
    @Override
    public List<Turno> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin) {
        return turnoRepository.findByFechaHoraBetween(inicio, fin);
    }
    
    @Override
    public List<Turno> findByEstado(String estado) {
        return turnoRepository.findByEstado(estado);
    }

	@Override
	public Turno findByCodigoTurno(String codigoTurno) {
		
		return turnoRepository.findByCodigoTurno(codigoTurno);
	} 

}
