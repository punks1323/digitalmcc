package com.cluster.digital.component;

import com.cluster.digital.database.entity.Role;
import com.cluster.digital.repo.RoleRepository;
import com.cluster.digital.service.AppUserService;
import com.cluster.digital.utils.MConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Admin username.
     */
    public static final String ADMIN_USERNAME = "admin";

    /**
     * Admin password
     */
    public static final String ADMIN_PASSWORD = "digitalop";

    /**
     * Flag to stop re-execution of listener
     */
    private boolean alreadySetup = false;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        createUserRolesIfNotFound();
        appUserService.createAdmin(ADMIN_USERNAME, ADMIN_PASSWORD);
        alreadySetup = true;
    }

    @Transactional
    private void createUserRolesIfNotFound() {
        List<String> allRoles = Arrays.asList(MConstants.ROLE.ROLE_ADMIN, MConstants.ROLE.ROLE_FE, MConstants.ROLE.ROLE_CSP);
        allRoles.forEach(s -> {
            Role role = roleRepository.findByName(s);
            if (role == null) {
                role = new Role(s);
                roleRepository.save(role);
            }
        });

    }
}