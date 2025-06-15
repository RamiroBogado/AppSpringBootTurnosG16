package com.unla.tp_oo2_g16.configurations.seeder;

import com.unla.tp_oo2_g16.models.entities.Disponibilidad;
import com.unla.tp_oo2_g16.models.entities.Servicio;
import com.unla.tp_oo2_g16.repositories.DisponibilidadRepository;
import com.unla.tp_oo2_g16.repositories.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@Order(4)
public class DisponibilidadSeeder implements CommandLineRunner {

    private final ServicioRepository servicioRepository;
    private final DisponibilidadRepository disponibilidadRepository;

    public DisponibilidadSeeder(ServicioRepository servicioRepository, DisponibilidadRepository disponibilidadRepository) {
        this.servicioRepository = servicioRepository;
        this.disponibilidadRepository = disponibilidadRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (disponibilidadRepository.count() == 0) {
            cargarDisponibilidades();
        }
    }

    private void cargarDisponibilidades() {
        List<Servicio> servicios = servicioRepository.findAll();

        if (servicios.isEmpty()) {
            System.out.println("Faltan servicios para asignar disponibilidades");
            return;
        }

        // Días de la semana permitidos (de lunes a viernes)
        List<DayOfWeek> diasPermitidos = List.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
        );

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(10); // elegir dias para generar las fechas

        for (Servicio servicio : servicios) {
            int duracion = servicio.getDuracion(); // duración en minutos

            for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
                if (diasPermitidos.contains(fecha.getDayOfWeek())) {
                    LocalTime horaInicio = LocalTime.of(9, 0);
                    LocalTime horaFin = LocalTime.of(17, 0);

                    while (horaInicio.plusMinutes(duracion).isBefore(horaFin.plusSeconds(1))) {
                        LocalTime bloqueFin = horaInicio.plusMinutes(duracion);
                        Disponibilidad disponibilidad = new Disponibilidad();
                        disponibilidad.setFecha(fecha);
                        disponibilidad.setHorarioInicio(horaInicio);
                        disponibilidad.setHorarioFin(bloqueFin);
                        disponibilidad.setServicio(servicio);
                        disponibilidad.setEstado(false); // disponible

                        disponibilidadRepository.save(disponibilidad);
                        horaInicio = bloqueFin;
                    }
                }
            }
        }

        System.out.println("Disponibilidades generadas automáticamente para próximos 30 días");
    }
}
