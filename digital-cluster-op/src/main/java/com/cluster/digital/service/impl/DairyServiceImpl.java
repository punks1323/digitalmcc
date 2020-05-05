package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.DairyDTO;
import com.cluster.digital.repo.ClusterRepository;
import com.cluster.digital.repo.DairyRepository;
import com.cluster.digital.repo.RouteRepository;
import com.cluster.digital.service.DairyService;
import org.aspectj.weaver.ast.Not;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class DairyServiceImpl implements DairyService {

    private DairyRepository dairyRepository;
    private ClusterRepository clusterRepository;
    private RouteRepository routeRepository;

    public DairyServiceImpl(ClusterRepository clusterRepository, DairyRepository dairyRepository, RouteRepository routeRepository) {
        this.clusterRepository = clusterRepository;
        this.dairyRepository = dairyRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public Dairy createNewDairy(DairyDTO dairyDTO) throws Throwable {
        // validate cluster id
        Optional<Cluster> clusterOptional = clusterRepository.findById(dairyDTO.getClusterId());
        clusterOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No cluster found with id: " + dairyDTO.getClusterId()));

        // validate all route ids
        Collection<Route> allRoutes = routeRepository.findByIdIn(dairyDTO.getRouteIds());
        if (allRoutes.size() != dairyDTO.getRouteIds().size())
            throw new NotFoundException("Some route ids not found in database.");

        Dairy dairy = new Dairy();
        dairy.setName(dairyDTO.getName());
        dairy.setCluster(clusterOptional.get());
        dairy.setDistrict(dairyDTO.getDistrict());
        dairy.setState(dairyDTO.getState());
        dairy.setRoutes(allRoutes);
        return dairyRepository.save(dairy);
    }

    @Override
    public Collection<Dairy> getAllDairies(String query) {
        return query == null ? dairyRepository.findAll() : dairyRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
    }
}
