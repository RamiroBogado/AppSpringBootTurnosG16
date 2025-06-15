package com.unla.tp_oo2_g16.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.unla.tp_oo2_g16.models.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findById(Integer integer);

    Optional<UserEntity> findByEmailUser(String emailUser);

    @Query(value = "from UserEntity u order by u.id")
    List<UserEntity> findAll();

    List<UserEntity> findAllByActive(boolean active);
}