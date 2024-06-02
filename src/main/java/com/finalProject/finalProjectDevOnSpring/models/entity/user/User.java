package com.finalProject.finalProjectDevOnSpring.models.entity.user;

import com.finalProject.finalProjectDevOnSpring.models.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Table(name = "USERS")
public class User extends BaseEntity {
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String username;
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String password;
    @Column(columnDefinition = "VARCHAR NOT NULL UNIQUE")
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<UserRole> role;
}
