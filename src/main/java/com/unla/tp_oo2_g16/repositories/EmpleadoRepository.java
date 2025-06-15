package com.unla.tp_oo2_g16.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByLegajo(String legajo);
    Empleado findByDni(String dni);
}
