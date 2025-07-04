package com.unla.tp_oo2_g16.repositories;

import com.unla.tp_oo2_g16.models.entities.Localidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {

    List<Localidad> findAllByOrderByCpAsc();

    Localidad findById(int idLocalidad);
    Localidad findByNombre(String nombre);
    Localidad findByCp(String cp);

    boolean existsByCp(String cp);
    boolean existsByCpAndIdLocalidadNot(String cp, Integer idLocalidad);

    @Query("SELECT l FROM Localidad l WHERE " +
    	       "LOWER(l.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))") //OR " +
    	       //"l.cp LIKE CONCAT('%', :filtro, '%')")
    	List<Localidad> buscarPorFiltro(@Param("filtro") String filtro);

}
