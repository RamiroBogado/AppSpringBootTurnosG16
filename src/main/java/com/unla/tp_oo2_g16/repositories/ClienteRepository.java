package com.unla.tp_oo2_g16.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
    Cliente findByDni(String dni);
    List<Cliente> findByesConcurrente(boolean esConcurrente);
    Cliente findByUser_EmailUser(String emailUser);
    
    boolean existsByDni(String dni);
    boolean existsByCuil(Long cuil);
    
    @Query("SELECT c FROM Cliente c WHERE " +
    	       "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "STR(c.dni) LIKE %:filtro% OR " +
    	       "STR(c.cuil) LIKE %:filtro%")
    	List<Cliente> buscarPorFiltro(@Param("filtro") String filtro);

}
