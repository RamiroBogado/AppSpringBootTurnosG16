package com.unla.tp_oo2_g16.configurations.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.unla.tp_oo2_g16.models.entities.Localidad;
import com.unla.tp_oo2_g16.repositories.LocalidadRepository;

@Component
@Order(1)
public class LocalidadSeeder implements CommandLineRunner {

    private final LocalidadRepository localidadRepository;

    public LocalidadSeeder(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    @Override
    public void run(String... args) {
        if (localidadRepository.count() == 0) {
            localidadRepository.save(new Localidad("Lomas de Zamora"));
            localidadRepository.save(new Localidad("Temperley"));
            localidadRepository.save(new Localidad("Banfield"));
        }
    }
}
