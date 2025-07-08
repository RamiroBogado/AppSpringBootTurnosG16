package com.unla.tp_oo2_g16.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    
    @Query("SELECT DISTINCT s FROM Servicio s " + 
            "LEFT JOIN FETCH s.sedes sede " + 
            "LEFT JOIN FETCH sede.localidad " + 
            "WHERE s.idServicio = :id")
    List<Servicio> findByIdWithSedes(@Param("id") Integer id);

}