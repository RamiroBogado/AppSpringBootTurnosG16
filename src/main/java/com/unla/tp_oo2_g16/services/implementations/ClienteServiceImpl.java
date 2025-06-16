package com.unla.tp_oo2_g16.services.implementations;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.enums.RoleType;
import com.unla.tp_oo2_g16.models.entities.Cliente;
import com.unla.tp_oo2_g16.repositories.ClienteRepository;
import com.unla.tp_oo2_g16.services.interfaces.ClienteServiceInterface;
import com.unla.tp_oo2_g16.services.interfaces.RoleServiceInterface;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteServiceInterface {
    
    private final ClienteRepository clienteRepository;
    
    @Autowired
	RoleServiceInterface roleService;
    
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    @Override
    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }
    
    @Override
    public Cliente save(Cliente cliente) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
        
        // Codificás la contraseña y la seteás
        String encryptedPassword = passwordEncoder.encode(cliente.getUser().getPasswordUser());
        cliente.getUser().setPasswordUser(encryptedPassword);

        // Activás el usuario
        cliente.getUser().setActive(true);

        // Asignás el rol
        cliente.getUser().setRoles(Set.of(roleService.findByNombre(RoleType.USER)));

        return clienteRepository.save(cliente);
    }
    
    @Override
    public Cliente editado(Cliente cliente) {
        if (cliente.getIdPersona() != null) {
            Cliente clienteOriginal = clienteRepository.findById(cliente.getIdPersona()).orElseThrow();

            // Actualizás solo los campos editables
            clienteOriginal.setNombre(cliente.getNombre());
            clienteOriginal.setApellido(cliente.getApellido());
            clienteOriginal.setDni(cliente.getDni());
            clienteOriginal.setCuil(cliente.getCuil());
            clienteOriginal.setEsConcurrente(cliente.isEsConcurrente());

            // El user ya está asociado y no se toca

            return clienteRepository.save(clienteOriginal);
        }

        // Es un nuevo cliente
        return clienteRepository.save(cliente);
    }

    
    @Override
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);
    }
    
    @Override
    public Cliente findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }
    
    @Override
    public List<Cliente> findClientesConcurrentes() {
        return clienteRepository.findByesConcurrente(true);
    }

    @Override
    public Cliente findByEmail(String email) {
        return clienteRepository.findByUser_EmailUser(email);
    }
    
    public boolean existsByDni(String dni) {
        return clienteRepository.existsByDni(dni);
    }

    public boolean existsByCuil(Long cuil) {
        return clienteRepository.existsByCuil(cuil);
    }
    
    @Override
    public List<Cliente> buscarPorNombreODniOCuil(String filtro) {
        return clienteRepository.buscarPorFiltro(filtro);
    }

}