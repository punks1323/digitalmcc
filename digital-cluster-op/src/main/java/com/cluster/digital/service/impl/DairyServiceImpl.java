package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.ClusterRepository;
import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.exception.AllIdDoesNotFoundException;
import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;
import com.cluster.digital.service.DairyService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class DairyServiceImpl implements DairyService {

    private final DairyRepository dairyRepository;
    private final ClusterRepository clusterRepository;
    private final RouteRepository routeRepository;

    public DairyServiceImpl(ClusterRepository clusterRepository, DairyRepository dairyRepository, RouteRepository routeRepository) {
        this.clusterRepository = clusterRepository;
        this.dairyRepository = dairyRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public DairyDTOResponse createNewDairy(DairyDTORequest dairyDTORequest) throws Throwable {

        // validate cluster id
        Cluster cluster = check4ClusterExistence(clusterRepository, dairyDTORequest.getClusterId());

        // validate all route ids
        List<Route> allRoutes = routeRepository.findByIdIn(dairyDTORequest.getRouteIds());
        if (allRoutes.size() != dairyDTORequest.getRouteIds().size())
            throw new AllIdDoesNotFoundException("Some route ids not found in database: " + dairyDTORequest.getRouteIds());

        Dairy dairy = new Dairy();
        dairy.setName(dairyDTORequest.getName());
        dairy.setCluster(cluster);
        dairy.setDistrict(dairyDTORequest.getDistrict());
        dairy.setState(dairyDTORequest.getState());
        dairy.setRoutes(allRoutes);
        return dairyRepository.save(dairy).getResponseDTO();
    }

    @Override
    public List<DairyDTOResponse> getAllDairies(String query) {
        List<Dairy> dairies = query == null ? dairyRepository.findAll() : dairyRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return dairies.stream().map(Dairy::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public DairyDTOResponse addRoutesToDairy(String dairyId, List<String> routeIds) throws Throwable {

        Dairy dairy = check4DairyExistence(dairyRepository, dairyId);

        Collection<Route> routes = routeRepository.findByIdIn(routeIds);
        if (routes.size() != routeIds.size())
            throw new AllIdDoesNotFoundException("Some route ids not found in database: " + routeIds);

        dairy.getRoutes().addAll(routes);
        return dairyRepository.save(dairy).getResponseDTO();
    }
}
