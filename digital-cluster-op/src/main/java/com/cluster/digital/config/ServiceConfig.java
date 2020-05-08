package com.cluster.digital.config;

import com.cluster.digital.database.repo.*;
import com.cluster.digital.service.*;
import com.cluster.digital.service.impl.*;
import com.cluster.digital.utils.AppAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

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

    @Bean
    public RouteService routeService(RouteRepository routeRepository, DairyRepository dairyRepository, MccRepository mccRepository) {
        return new RouteServiceImpl(routeRepository, dairyRepository, mccRepository);
    }

    @Bean
    public MccService mccService(MccRepository mccRepository, RouteRepository routeRepository, FileStorageService fileStorageService) {
        return new MccServiceImpl(mccRepository, routeRepository, fileStorageService);
    }

    @Bean
    public CspService cspService(CspRepository cspRepository, MccRepository mccRepository, FileStorageService fileStorageService) {
        return new CspServiceImpl(cspRepository, mccRepository, fileStorageService);
    }

    @Bean
    public FileStorageService fileStorageService() {
        return new FileStorageServiceImpl(System.getProperty("user.dir") + File.separator + "uploads");
    }

    @Bean
    public FarmerService farmerService(MccRepository mccRepository, FarmerRepository farmerRepository, FileStorageService fileStorageService) {
        return new FarmerServiceImpl(mccRepository, farmerRepository, fileStorageService);
    }

}
