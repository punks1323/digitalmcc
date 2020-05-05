package com.cluster.digital.config;

import com.cluster.digital.repo.*;
import com.cluster.digital.service.AppUserService;
import com.cluster.digital.service.ClusterService;
import com.cluster.digital.service.DairyService;
import com.cluster.digital.service.impl.AppUserServiceImpl;
import com.cluster.digital.service.impl.ClusterServiceImpl;
import com.cluster.digital.service.impl.DairyServiceImpl;
import com.cluster.digital.utils.AppAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Configuration
public class ServiceConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AppAuditorAware();
    }

    @Bean
    public AppUserService appUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        return new AppUserServiceImpl(appUserRepository, bCryptPasswordEncoder, roleRepository);
    }

    @Bean
    public ClusterService clusterService(ClusterRepository clusterRepository) {
        return new ClusterServiceImpl(clusterRepository);
    }

    @Bean
    public DairyService dairyService(ClusterRepository clusterRepository, DairyRepository dairyRepository, RouteRepository routeRepository) {
        return new DairyServiceImpl(clusterRepository, dairyRepository, routeRepository);
    }
}
