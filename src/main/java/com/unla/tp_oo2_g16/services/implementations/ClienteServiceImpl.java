package com.unla.tp_oo2_g16.services.implementations;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.dtos.ClienteDTO;
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
    @Autowired
    UserServiceImp userService;
    
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
    
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }
    
    public List<Cliente> buscarPorFiltroYConcurrente(String filtro, String concurrenteStr) {
        Boolean concurrente = null;

        if ("true".equalsIgnoreCase(concurrenteStr)) {
            concurrente = true;
        } else if ("false".equalsIgnoreCase(concurrenteStr)) {
            concurrente = false;
        }

        return clienteRepository.buscarConFiltroOpcional(filtro, concurrente);
    }

    
 // Convierte Cliente a ClienteDTO
    public ClienteDTO toDTO(Cliente c) {
        return new ClienteDTO(
            c.getIdPersona(),
            c.getNombre(),
            c.getApellido(),
            c.getDni(),
            c.getCuil(),
            c.isEsConcurrente(),
            c.getUser() != null ? c.getUser().getEmailUser() : null,
            null // no enviamos password al editar
        );
    }

    // Convierte ClienteDTO a Cliente
    public Cliente toEntity(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setIdPersona(dto.idPersona());
        c.setNombre(dto.nombre());
        c.setApellido(dto.apellido());
        c.setDni(dto.dni());
        c.setCuil(dto.cuil());
        c.setEsConcurrente(dto.esConcurrente());
        if (c.getUser() == null) {
            c.setUser(new com.unla.tp_oo2_g16.models.entities.UserEntity());
        }
        c.getUser().setEmailUser(dto.emailUser());
        if (dto.passwordUser() != null && !dto.passwordUser().isEmpty()) {
            c.getUser().setPasswordUser(dto.passwordUser());
        }
        return c;
    }

}