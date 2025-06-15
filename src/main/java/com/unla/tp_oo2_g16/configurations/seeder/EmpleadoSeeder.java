package com.unla.tp_oo2_g16.configurations.seeder;

import com.unla.tp_oo2_g16.models.entities.Empleado;
import com.unla.tp_oo2_g16.repositories.EmpleadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class EmpleadoSeeder implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoSeeder(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (empleadoRepository.count() == 0) {
            cargarEmpleados();
        }
    }

    private void cargarEmpleados() {
        empleadoRepository.save(new Empleado("Laura", "Martínez", "32123456", "EMP001", "puesto"));
        empleadoRepository.save(new Empleado("Carlos", "Gómez", "30111222", "EMP002", "puesto"));
        empleadoRepository.save(new Empleado("Ana", "Pérez", "29099877", "EMP003", "puesto"));
        empleadoRepository.save(new Empleado("Javier", "Rodríguez", "33123456", "EMP004", "puesto"));
        empleadoRepository.save(new Empleado("Lucía", "Fernández", "30012345", "EMP005", "puesto"));
    }
}
