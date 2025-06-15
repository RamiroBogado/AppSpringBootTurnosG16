package com.unla.tp_oo2_g16.configurations.seeder;

import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.repositories.ServicioRepository;
import com.unla.tp_oo2_g16.services.interfaces.SedeServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ServicioSeeder implements CommandLineRunner {

    private final ServicioRepository servicioRepository;
    
    @Autowired
    SedeServiceInterface sedeService;

    public ServicioSeeder(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (servicioRepository.count() == 0) {
            cargarServicios();
        }
    }

    private void cargarServicios() {
        servicioRepository.save(servicioBuild("Consulta General", "Consulta médica general", 30));
        servicioRepository.save(servicioBuild("Odontología", "Tratamiento odontológico", 45));
        servicioRepository.save(servicioBuild("Psicología", "Sesión psicológica individual", 60));
        System.out.println("Servicios cargados!");
    }
    
    private Servicio servicioBuild(String nombre, String descripcion, int duracion) {
    	
    	return Servicio.builder()
    			.nombre(nombre)
    			.descripcion(descripcion)
    			.duracion(duracion)
    			.disponibilidades(null)
    			.sedes(sedeService.findAllSet())
    			.empleado(null)
    			.build();
    		
    };
}
