package com.cluster.digital.database.entity;

import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Data
public class AppUser extends Auditable<String> implements UserDetails {

    private static final String USER_ID_GENERATOR = "app-user-id-generator";
    private static final String USER_ID_GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String USER_ID_PREFIX = "USER-";
    private static final String USER_ID_LEAD_COUNT = "5";
    @Id
    @GeneratedValue(generator = USER_ID_GENERATOR)
    @GenericGenerator(name = USER_ID_GENERATOR,
            parameters = {@org.hibernate.annotations.Parameter(name = MConstants.ENTITY_ID.PREFIX, value = USER_ID_PREFIX), @org.hibernate.annotations.Parameter(name = MConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = USER_ID_LEAD_COUNT)},
            strategy = USER_ID_GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(m -> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
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
        return true;
    }

}