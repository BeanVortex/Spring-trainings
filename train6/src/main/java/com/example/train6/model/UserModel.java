package com.example.train6.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class UserModel implements UserDetails {

    @Id
    @Generated
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Roles.class)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
