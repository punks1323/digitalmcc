package com.cluster.digital.service;

import com.cluster.digital.database.entity.AppUser;
import com.cluster.digital.database.entity.Role;
import com.cluster.digital.database.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> appUserOptional = appUserRepoService.findByUsername(username);
        if (!appUserOptional.isPresent()) {
            throw new UsernameNotFoundException("AppUser not found with username : " + username);
        }
        AppUser appUser = appUserOptional.get();

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(), appUser.getPassword(), appUser.isEnabled(), true, true,
                true, getGrantedAuthorities(appUser.getRoles()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        return roles.stream().map(m -> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
    }
}