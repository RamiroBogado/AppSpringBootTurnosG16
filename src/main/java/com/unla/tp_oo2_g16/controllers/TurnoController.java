package com.unla.tp_oo2_g16.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	//private ModelMapper modelMapper = new ModelMapper();
	
	/*
	 * PRIMERA VISTA: botón "Solicitar Turno" URL: GET /index
	 */
	@GetMapping("/index")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView mostrarInicio() {		
		return new ModelAndView(ViewRouteHelper.SOLICITAR_TURNO);
	}
		
	@GetMapping("/seleccionar-servicio")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView seleccionarServicio() {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARSERVICIO_TURNO);
	    mav.addObject("servicios", servicioService.findAll());
	    return mav;
	}
	
	@PostMapping("/seleccionar-sede")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView seleccionarSede(@RequestParam int servicioId) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARSEDE_TURNO);
	    
	    Servicio servicio = servicioService.findById(servicioId);
	    	    
	    mav.addObject("servicio", servicio);
	    mav.addObject("sedes", servicio.getSedes());
	    return mav;
	}


	@PostMapping("/seleccionar-fecha")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView seleccionarFecha(@RequestParam int servicioId, @RequestParam int sedeId) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARFECHA_TURNO);

	    mav.addObject("servicioId", servicioId);
	    mav.addObject("sedeId", sedeId);
	    mav.addObject("fechasDisponibles", disponibilidadesService.findFechasDisponibles(servicioId));

	    return mav;
	}
	
	@PostMapping("/seleccionar-horario")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView seleccionarHorario(@RequestParam int servicioId,
	                                       @RequestParam int sedeId,
	                                       @RequestParam String fecha) {
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.SELECCIONARHORARIO_TURNO);

	    mav.addObject("servicioId", servicioId);
	    mav.addObject("sedeId", sedeId);
	    mav.addObject("fecha", fecha);
	    mav.addObject("horarios", disponibilidadesService.findHorariosDisponiblesPorFecha(servicioId, fecha));

	    return mav;
	}
	
	@PostMapping("/confirmar")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ModelAndView confirmarTurno(@RequestParam int servicioId,
	                                 @RequestParam int sedeId,
	                                 @RequestParam String fecha,
	                                 @RequestParam String horario,
	                                 Principal principal) {
	    // Buscar entidades necesarias
	    Servicio servicio = servicioService.findById(servicioId);
	    Sede sede = sedeService.findById(sedeId);
	    

	    Cliente cliente = clienteService.findByEmail(principal.getName());

	    // Combinar fecha y hora en LocalDateTime
	    LocalDate fechaParsed = LocalDate.parse(fecha);
	    LocalTime horaParsed = LocalTime.parse(horario);
	    LocalDateTime fechaHora = LocalDateTime.of(fechaParsed, horaParsed);

	    // Crear el turno
	    Turno turno = new Turno(cliente, servicio, fechaHora);
	    	    
	    // Redirigir a la vista de confirmación
	    ModelAndView mav = new ModelAndView(ViewRouteHelper.CONFRIMACION_TURNO);
	    mav.addObject("sedeSeleccionada", sede);
	    mav.addObject("turno", turno);
	    return mav;
	}

	@PostMapping("/guardar-turno")
	public ModelAndView guardarTurno(@RequestParam int servicioId,
	                                @RequestParam int sedeId,
	                                @RequestParam String fecha,
	                                @RequestParam String horario,
	                                Principal principal) throws MessagingException {
	    // Parsear fecha y hora
	    LocalDate fechaParsed = LocalDate.parse(fecha);
	    LocalTime horaParsed = LocalTime.parse(horario);
	    LocalDateTime fechaHora = LocalDateTime.of(fechaParsed, horaParsed);

	    Servicio servicio = servicioService.findById(servicioId);
	    Sede sede = sedeService.findById(sedeId);
	    Cliente cliente = clienteService.findByEmail(principal.getName());

	    Turno turno = new Turno(cliente, servicio, fechaHora);

	    turnoService.save(turno);
	    	    
	    disponibilidadesService.ocuparDisponibilidad(servicioId, fechaParsed, horaParsed);
	     
	    emailService.enviarConfirmacionTurnoHtml(cliente.getUser().getEmailUser(), turno, sede);

	    ModelAndView mav = new ModelAndView(ViewRouteHelper.GUARDAR_TURNO);
	    mav.addObject("success", "Turno guardado correctamente");
	    return mav;
	}

	/* PRIMERA VISTA ANULACION: formulario de anulacion
	 * URL: GET /anular-turno  */
	@GetMapping("/anular-turno")
    public ModelAndView mostrarFormularioAnulacion() {
		
        return new ModelAndView(ViewRouteHelper.ANULAR_TURNO);
    }
	
	/* SEGUNDA VISTA ANULACION: vista de anulacion correcta
	 * URL: POST /anulacionCorrecta  */
	@PostMapping("/anulacionCorrecta")
	public ModelAndView anularTurno(@RequestParam("codigo") String codigoA, RedirectAttributes redirectAttributes) {
	    Turno turnoAux = turnoService.findByCodigoTurno(codigoA);
	    
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
