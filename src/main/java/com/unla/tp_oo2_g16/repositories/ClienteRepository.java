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
    	       "(:filtro IS NULL OR " +
    	       "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
    	       "STR(c.dni) LIKE %:filtro% OR " +
    	       "STR(c.cuil) LIKE %:filtro% OR " +
    	       "LOWER(c.user.emailUser) LIKE LOWER(CONCAT('%', :filtro, '%'))) AND " +
    	       "(:concurrente IS NULL OR c.esConcurrente = :concurrente)")
    	List<Cliente> buscarConFiltroOpcional(@Param("filtro") String filtro,
    	                                      @Param("concurrente") Boolean concurrente);



}
