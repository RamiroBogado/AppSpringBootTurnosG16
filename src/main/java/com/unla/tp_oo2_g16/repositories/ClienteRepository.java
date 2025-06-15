package com.unla.tp_oo2_g16.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
    Cliente findByDni(String dni);
    List<Cliente> findByesConcurrente(boolean esConcurrente);
    Cliente findByUser_EmailUser(String emailUser);
}
