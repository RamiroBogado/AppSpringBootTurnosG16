package com.unla.tp_oo2_g16.services.implementations;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.TurnoGestionDTO;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.repositories.TurnoRepository;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TurnoServiceImplementation implements TurnoServiceInterface {
    
    private final TurnoRepository turnoRepository;
    
    @Autowired
    ClienteServiceInterface clienteService;
    @Autowired
    ServicioServiceInterface servicioService;
    @Autowired
    SedeServiceInterface sedeService;
    
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
        if (turno.getCodigoTurno() == null || turno.getCodigoTurno().isEmpty()) {
            turno.setCodigoTurno(generateCodigoTurno());
        }
        return turnoRepository.save(turno);
    }

    private String generateCodigoTurno() {
        return "TUR" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
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
	
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // ...

    public TurnoGestionDTO toDTO(Turno turno) {
        // Obtener la primera sede del servicio (si existe)
        Sede sede = turno.getServicio().getSedes().stream().findFirst().orElse(null);

        return new TurnoGestionDTO(
            turno.getIdTurno(),
            turno.getCliente().getIdPersona(),
            turno.getServicio().getIdServicio(),
            sede != null ? sede.getIdSede() : null,
            // Formatear fechaHora para input datetime-local
            turno.getFechaHora().format(FORMATO_FECHA_HORA),
            turno.getEstado(),
            turno.getCodigoTurno(),
            turno.getCliente().getNombre() + " " + turno.getCliente().getApellido(),
            turno.getServicio().getNombre(),
            sede != null ? sede.getDireccion() : "",
            sede != null && sede.getLocalidad() != null ? sede.getLocalidad().getLocalidad() : ""
        );
    }

    public Turno toEntity(TurnoGestionDTO dto) {
        Turno turno = new Turno();

        turno.setIdTurno(dto.idTurno());
        // Parsear el string de fecha/hora del input datetime-local
        turno.setFechaHora(LocalDateTime.parse(dto.fechaHora(), FORMATO_FECHA_HORA));
        turno.setEstado(dto.estado());

        Cliente cliente = clienteService.findById(dto.idCliente());
        Servicio servicio = servicioService.findById(dto.idServicio());

        turno.setCliente(cliente);
        turno.setServicio(servicio);

        // No asignamos sede porque Turno no la tiene directamente

        // Código turno: usar el que viene o generar uno nuevo
        turno.setCodigoTurno(
            dto.codigoTurno() != null ? dto.codigoTurno()
                                      : UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        );

        return turno;
    }

	
	public List<Turno> buscarPorFiltroYEstado(String filtro, String estado) {
	    if ((filtro == null || filtro.isEmpty()) && (estado == null || estado.isEmpty())) {
	        return turnoRepository.findAll();
	    } else if (estado == null || estado.isEmpty()) {
	        return turnoRepository.buscarPorClienteServicioFechaEstadoOCodigo(filtro);
	    } else {
	        return turnoRepository.buscarPorFiltroYEstado(filtro, estado);
	    }
	}

	@Override
	public List<Turno> findByClienteCliente(Cliente cliente) {
	    return turnoRepository.findByCliente(cliente);
	}


}
