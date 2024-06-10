package com.turkcell.identityService.entitites;

import com.turkcell.identityService.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role extends BaseEntity<Integer> implements GrantedAuthority {

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return name.toLowerCase();
    }
}
