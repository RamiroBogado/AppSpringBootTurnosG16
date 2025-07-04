package com.unla.tp_oo2_g16.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.tp_oo2_g16.models.entities.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
		
    @Query("SELECT s FROM Sede s WHERE " +
            "LOWER(s.direccion) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "LOWER(s.localidad.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))")
        List<Sede> buscarPorFiltro(@Param("filtro") String filtro);

    
    List<Sede> findAllByOrderByDireccionAsc();

}
