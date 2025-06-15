package com.unla.tp_oo2_g16.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Turno;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    
    // Usando @Query explícito para evitar problemas con la herencia
    @Query("SELECT t FROM Turno t WHERE t.cliente.id = :clienteId")
    List<Turno> findByCliente(@Param("clienteId") Integer clienteId);
     
    @Query("SELECT t FROM Turno t WHERE t.servicio.id = :servicioId")
    List<Turno> findByServicio(@Param("servicioId") Integer servicioId);
    
    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Turno> findByEstado(String estado);
    
    Turno findByCodigoTurno(String codigoTurno);

}
