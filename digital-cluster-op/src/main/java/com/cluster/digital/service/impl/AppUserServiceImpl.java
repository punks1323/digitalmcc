package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.AppUser;
import com.cluster.digital.database.entity.Role;
import com.cluster.digital.database.repo.AppUserRepository;
import com.cluster.digital.database.repo.RoleRepository;
import com.cluster.digital.service.AppUserService;
import com.cluster.digital.utils.MConstants;
import com.cluster.digital.utils.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepoService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepoService;

    /**
     * default pwd length 8
     */
    @Value("${deviceRegistration.pwdLength:8}")
    private String pwdLength;

    public AppUserServiceImpl(AppUserRepository appUserRepoService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.appUserRepoService = appUserRepoService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepoService = roleRepository;
    }

    @Override
    public AppUser createAdmin(String username, String password) {
        Optional<AppUser> byUsername = appUserRepoService.findByUsername(username);
        if (!byUsername.isPresent()) {
            Set<String> roles = new HashSet<>();
            roles.add(MConstants.ROLE.ROLE_ADMIN);
            List<Role> rolesForUser = roles.stream().map(roleRepoService::findByName).collect(Collectors.toList());

            AppUser appUser = new AppUser();
            appUser.setUsername(username);
            appUser.setPassword(bCryptPasswordEncoder.encode(password));
            appUser.setRoles(rolesForUser);
            return appUserRepoService.save(appUser);
        }
        return byUsername.get();
    }

    @Override
    public AppUser createFieldEngineer(String username) {
        Optional<AppUser> byUsername = appUserRepoService.findByUsername(username);
        if (!byUsername.isPresent()) {
            Set<String> roles = new HashSet<>();
            roles.add(MConstants.ROLE.ROLE_FE);
            List<Role> rolesForUser = roles.stream().map(roleRepoService::findByName).collect(Collectors.toList());

            AppUser appUser = new AppUser();
            appUser.setUsername(username);
            appUser.setPassword(bCryptPasswordEncoder.encode(SecurityUtil.generatePwd(pwdLength, username)));
            appUser.setRoles(rolesForUser);
            return appUserRepoService.save(appUser);
        }
        return byUsername.get();
    }
}
