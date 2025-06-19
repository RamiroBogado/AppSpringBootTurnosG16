package com.unla.tp_oo2_g16.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unla.tp_oo2_g16.enums.RoleType;
import com.unla.tp_oo2_g16.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
	
	RoleEntity findByNombre(RoleType nombre);
	
    @SuppressWarnings("null")
    Optional<RoleEntity> findById(Integer integer);

    Optional<RoleEntity> findBynombre(RoleType type);
}
