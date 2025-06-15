package com.unla.tp_oo2_g16.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.tp_oo2_g16.enums.RoleType;
import com.unla.tp_oo2_g16.models.entities.RoleEntity;
import com.unla.tp_oo2_g16.repositories.RoleRepository;
import com.unla.tp_oo2_g16.services.interfaces.RoleServiceInterface;

@Service
public class RoleServiceImpl implements RoleServiceInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleEntity findByNombre(RoleType nombre) {
		
		return roleRepository.findByNombre(nombre);
	}



}
