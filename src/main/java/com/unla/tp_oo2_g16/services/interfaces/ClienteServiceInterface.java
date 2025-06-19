package com.unla.tp_oo2_g16.services.interfaces;

import java.util.List;

import com.unla.tp_oo2_g16.dtos.ClienteDTO;
import com.unla.tp_oo2_g16.models.entities.Cliente;

public interface ClienteServiceInterface {
    List<Cliente> findAll();
    Cliente findById(Integer id);
    Cliente save(Cliente cliente);
    void deleteById(Integer id);
    Cliente findByDni(String dni);
    List<Cliente> findClientesConcurrentes();
    
    Cliente findByEmail(String email);
    
    public boolean existsByDni(String dni);

    public boolean existsByCuil(Long cuil);
    
    boolean existsByEmail(String email);
    
    List<Cliente> buscarPorFiltroYConcurrente(String filtro, String concurrenteStr);
    
	Cliente editado(Cliente cliente);
	
	ClienteDTO toDTO(Cliente c);
	Cliente toEntity(ClienteDTO dto);

}