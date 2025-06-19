<<<<<<< HEAD
package com.unla.tp_oo2_g16.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByLegajo(String legajo);
    Empleado findByDni(String dni);
    boolean existsByDni(String dni);

    boolean existsByLegajo(String legajo);

    boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona);

    boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona);
    
    @Query("SELECT e FROM Empleado e WHERE " +
    	       "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(e.legajo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "e.dni LIKE CONCAT('%', :filtro, '%') OR " +
    	       "LOWER(e.puesto) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    	List<Empleado> buscarPorFiltro(@Param("filtro") String filtro);

}
=======
package com.unla.tp_oo2_g16.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByLegajo(String legajo);
    Empleado findByDni(String dni);
    boolean existsByDni(String dni);

    boolean existsByLegajo(String legajo);

    boolean existsByDniAndIdPersonaNot(String dni, Integer idPersona);

    boolean existsByLegajoAndIdPersonaNot(String legajo, Integer idPersona);
    
    @Query("SELECT e FROM Empleado e WHERE " +
    	       "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(e.legajo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "e.dni LIKE CONCAT('%', :filtro, '%') OR " +
    	       "LOWER(e.puesto) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    	List<Empleado> buscarPorFiltro(@Param("filtro") String filtro);

}
>>>>>>> 70bfd25
