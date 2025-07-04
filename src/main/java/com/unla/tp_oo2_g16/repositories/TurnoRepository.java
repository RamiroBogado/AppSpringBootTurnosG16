package com.unla.tp_oo2_g16.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.models.entities.Turno;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    
    // Usando @Query explícito para evitar problemas con la herencia
    @Query("SELECT t FROM Turno t WHERE t.cliente.id = :clienteId")
    List<Turno> findByCliente(@Param("clienteId") Integer clienteId);
    
    List<Turno> findByCliente(Cliente cliente);

     
    @Query("SELECT t FROM Turno t WHERE t.servicio.id = :servicioId")
    List<Turno> findByServicio(@Param("servicioId") Integer servicioId);
    
    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Turno> findByEstado(String estado);
    
    Turno findByCodigoTurno(String codigoTurno);
    
    @Query("SELECT t FROM Turno t " +
    	       "WHERE LOWER(t.cliente.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "   OR LOWER(t.cliente.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "   OR LOWER(t.servicio.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "   OR STR(t.fechaHora) LIKE %:filtro% " +
    	       "   OR LOWER(t.estado) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
    	       "   OR LOWER(t.codigoTurno) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    	List<Turno> buscarPorClienteServicioFechaEstadoOCodigo(@Param("filtro") String filtro);

    @Query("SELECT t FROM Turno t WHERE " +
    	       "(LOWER(t.cliente.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(t.cliente.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(t.servicio.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "STR(t.fechaHora) LIKE %:filtro% OR " +
    	       "LOWER(t.codigoTurno) LIKE LOWER(CONCAT('%', :filtro, '%'))) AND " +
    	       "(:estado IS NULL OR :estado = '' OR t.estado = :estado)")
    	List<Turno> buscarPorFiltroYEstado(@Param("filtro") String filtro, @Param("estado") String estado);
    
}
