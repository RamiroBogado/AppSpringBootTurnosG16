package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.models.entities.Cliente;

public interface ClienteServiceInterface {
    List<Cliente> findAll();
    Cliente findById(Integer id);
    Cliente save(Cliente cliente);
    void deleteById(Integer id);
    Cliente findByDni(String dni);
    List<Cliente> findClientesConcurrentes();
    
    Cliente findByEmail(String email);
}