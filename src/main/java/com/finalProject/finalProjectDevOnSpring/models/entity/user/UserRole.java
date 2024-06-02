package com.finalProject.finalProjectDevOnSpring.models.entity.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Getter
@Setter
@FieldNameConstants
@Accessors(chain = true)
@Entity
@Table(name = "ROLES")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    @Enumerated(EnumType.STRING)
    private RoleType role;
    @ManyToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(role.name());
    }
}
