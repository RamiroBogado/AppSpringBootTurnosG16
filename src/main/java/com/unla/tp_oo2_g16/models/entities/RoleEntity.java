package com.unla.tp_oo2_g16.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.unla.tp_oo2_g16.enums.RoleType;

import java.sql.Timestamp;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    @Setter(AccessLevel.NONE)
    private Integer idRoleEntity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_role", nullable = false, length = 80, unique = true)
    private RoleType nombre;

    @Column(name = "create_at_role")
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "update_at_role")
    @UpdateTimestamp
    private Timestamp updateAt;

    public RoleEntity(@NotNull RoleType nombre){
        this.nombre = nombre;
    }
}