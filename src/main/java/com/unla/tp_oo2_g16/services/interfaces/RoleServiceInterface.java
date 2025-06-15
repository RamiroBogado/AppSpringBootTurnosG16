package com.unla.tp_oo2_g16.services.interfaces;

import com.unla.tp_oo2_g16.enums.RoleType;
import com.unla.tp_oo2_g16.models.entities.RoleEntity;

public interface RoleServiceInterface {
	
	RoleEntity findByNombre(RoleType nombre);
}
