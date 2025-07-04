package com.unla.tp_oo2_g16.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unla.tp_oo2_g16.dtos.DisponibilidadDTO;
import com.unla.tp_oo2_g16.dtos.SedeDTO;
import com.unla.tp_oo2_g16.dtos.ServicioDTO;
import com.unla.tp_oo2_g16.dtos.TurnoDTO;
import com.unla.tp_oo2_g16.helpers.ViewRouteHelper;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.models.entities.Turno;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.DisponibilidadesServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.EmailServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.ServicioServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.TurnoServiceInterface;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/turno")
@Tag(name = "Turnos", description = "Gestión de turnos")
public class TurnoController {

	@Autowired
	private ServicioServiceInterface servicioService;
	@Autowired
	private SedeServiceInterface sedeService;
	@Autowired
	private DisponibilidadesServiceInterface disponibilidadesService;
	@Autowired
	private TurnoServiceInterface turnoService;
	@Autowired
	private ClienteServiceInterface clienteService;
	@Autowired
	private EmailServiceInterface emailService;
	
	/*
	 * PRIMERA VISTA: botón "Solicitar Turno" URL: GET /index
	 */
	@GetMapping("/index")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView mostrarInicio() {		
		return new ModelAndView(ViewRouteHelper.SOLICITAR_TURNO);
	}
		
	@GetMapping("/seleccionar-servicio")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView seleccionarServicio() {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARSERVICIO_TURNO);

	    List<ServicioDTO> serviciosDTO = servicioService.findAll().stream()
	        .map(servicio -> new ServicioDTO(servicio.getIdServicio(), servicio.getNombre()))
	        .collect(Collectors.toList());

	    mav.addObject("servicios", serviciosDTO);
	    return mav;
	}


	
	@PostMapping("/seleccionar-sede")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView seleccionarSede(@RequestParam int servicioId) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARSEDE_TURNO);

	    Servicio servicio = servicioService.findById(servicioId);

	    // Convertimos el set de sedes a lista de SedeDTO
	    List<SedeDTO> sedesDTO = servicio.getSedes().stream()
	        .map(sede -> new SedeDTO(
	            sede.getIdSede(),
	            sede.getDireccion(),
	            sede.getLocalidad() // nombre de la localidad
	        ))
	        .collect(Collectors.toList());

	    // Pasamos el servicio (puede ser la entidad o un DTO según lo uses)
	    ServicioDTO servicioDTO = new ServicioDTO(servicio.getIdServicio(), servicio.getNombre());

	    mav.addObject("servicio", servicioDTO);
	    mav.addObject("sedes", sedesDTO);

	    return mav;
	}

	@PostMapping("/seleccionar-fecha")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView seleccionarFecha(@RequestParam int servicioId, @RequestParam int sedeId) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARFECHA_TURNO);

	    mav.addObject("servicioId", servicioId);
	    mav.addObject("sedeId", sedeId);

	    List<String> fechas = disponibilidadesService.findFechasDisponibles(servicioId).stream()
	        .map(DisponibilidadDTO::fecha)
	        .distinct()
	        .toList();

	    mav.addObject("fechasDisponibles", fechas);

	    return mav;
	}

	@PostMapping("/seleccionar-horario")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView seleccionarHorario(@RequestParam int servicioId,
	                                       @RequestParam int sedeId,
	                                       @RequestParam String fecha) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARHORARIO_TURNO);

	    mav.addObject("servicioId", servicioId);
	    mav.addObject("sedeId", sedeId);
	    mav.addObject("fecha", fecha);

	    List<String> horarios = disponibilidadesService.findHorariosDisponiblesPorFecha(servicioId, fecha).stream()
	        .map(DisponibilidadDTO::horario)
	        .toList();

	    mav.addObject("horarios", horarios);

	    return mav;
	}

	
	@PostMapping("/confirmar")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView confirmarTurno(@ModelAttribute TurnoDTO turnoDTO, Principal principal) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.CONFRIMACION_TURNO);

	    Servicio servicio = servicioService.findById(turnoDTO.servicioId());
	    Sede sede = sedeService.findById(turnoDTO.sedeId());
	    Cliente cliente = clienteService.findByEmail(principal.getName());

	    LocalDateTime fechaHora = LocalDateTime.of(
	        LocalDate.parse(turnoDTO.fecha()),
	        LocalTime.parse(turnoDTO.horario())
	    );

	    Turno turno = new Turno(cliente, servicio, fechaHora);

	    mav.addObject("turno", turno); 
	    mav.addObject("sedeSeleccionada", sede);

	    return mav;
	}


	@PostMapping("/guardar-turno")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView guardarTurno(@ModelAttribute TurnoDTO turnoDTO, Principal principal) throws MessagingException {
	    Cliente cliente = clienteService.findByEmail(principal.getName());
	    Servicio servicio = servicioService.findById(turnoDTO.servicioId());
	    Sede sede = sedeService.findById(turnoDTO.sedeId());

	    LocalDateTime fechaHora = LocalDateTime.of(
	        LocalDate.parse(turnoDTO.fecha()),
	        LocalTime.parse(turnoDTO.horario())
	    );

	    Turno turno = new Turno(cliente, servicio, fechaHora);

	    turnoService.save(turno);

	    disponibilidadesService.ocuparDisponibilidad(servicio.getIdServicio(), fechaHora.toLocalDate(), fechaHora.toLocalTime());

	    emailService.enviarConfirmacionTurnoHtml(cliente.getUser().getEmailUser(), turno, sede);

	    ModelAndView mav = new ModelAndView(ViewRouteHelper.GUARDAR_TURNO);
	    mav.addObject("cliente", cliente);
	    mav.addObject("success", "Turno guardado correctamente");
	    mav.addObject("codigoTurno", turno.getCodigoTurno());
	    
	    return mav;
	}



	/* PRIMERA VISTA ANULACION: formulario de anulacion
	 * URL: GET /anular-turno  */
	@GetMapping("/anular-turno")
	@PreAuthorize("hasAnyRole('USER')")
    public ModelAndView mostrarFormularioAnulacion() {
		
        return new ModelAndView(ViewRouteHelper.ANULAR_TURNO);
    }
	
	/* SEGUNDA VISTA ANULACION: vista de anulacion correcta
	 * URL: POST /anulacionCorrecta  */
	@PostMapping("/anulacionCorrecta")
	@PreAuthorize("hasAnyRole('USER')")
	public ModelAndView anularTurno(@RequestParam("codigo") String codigoTurno, RedirectAttributes redirectAttributes) {
	    Turno turnoAux = turnoService.findByCodigoTurno(codigoTurno);
	    
	    if (turnoAux == null) {
	        redirectAttributes.addFlashAttribute("error", "❌ El código ingresado no corresponde a ningún turno.");
	        return new ModelAndView("redirect:/turno/anular-turno");
	    }

	    disponibilidadesService.liberarDisponibilidadPorTurno(turnoAux);
	    
	    // Cambiar estado del turno
	    turnoAux.setEstado("ANULADO");
	    turnoService.save(turnoAux);

	    return new ModelAndView(ViewRouteHelper.ANULACIONCORRECTA_TURNO);
	}

}
