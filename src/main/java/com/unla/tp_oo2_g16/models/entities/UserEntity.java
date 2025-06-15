package com.unla.tp_oo2_g16.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @Setter(AccessLevel.NONE)
    private Integer idUserEntity;

    @NotBlank
    @Email
    @Column(name = "email_user", nullable = false, length = 80, unique = true)
    private String emailUser;

    @NotBlank
    @Column(name = "password_user", nullable = false)
    private String passwordUser;

    @Column(name = "active_user", nullable = false)
    private boolean active;

    @CreationTimestamp
    @Column(name = "create_at_user")
    private Timestamp createAt;

    @UpdateTimestamp
    @Column(name = "update_at_user")
    private Timestamp updateAt;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre().getPrefixedName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.emailUser;
    }
    
	@Override
	public String getPassword() {
		return this.passwordUser;
	}

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return this.active; }


}
