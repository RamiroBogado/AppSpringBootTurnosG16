package com.unla.tp_oo2_g16.configurations.seeder;

import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.models.entities.Sede;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;
import com.unla.tp_oo2_g16.repositories.SedeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SedeSeeder implements CommandLineRunner {

    private final LocalidadRepository localidadRepository;
    private final SedeRepository sedeRepository;

    public SedeSeeder(LocalidadRepository localidadRepository, SedeRepository sedeRepository) {
        this.localidadRepository = localidadRepository;
        this.sedeRepository = sedeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (sedeRepository.count() == 0) {
            cargarUbicaciones();
        }
    }

    private void cargarUbicaciones() {
        Localidad lomas = localidadRepository.findById(1).orElseThrow();
        Localidad temperley = localidadRepository.findById(2).orElseThrow();
        Localidad banfield = localidadRepository.findById(3).orElseThrow();

        sedeRepository.save(new Sede("Av. Hipólito Yrigoyen 1000", lomas));
        sedeRepository.save(new Sede("Calle San Martín 200", temperley));
        sedeRepository.save(new Sede("Av. Alsina 300", banfield));
    }
}
