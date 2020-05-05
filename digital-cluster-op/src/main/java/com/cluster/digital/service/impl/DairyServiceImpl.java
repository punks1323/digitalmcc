package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;
import com.cluster.digital.repo.ClusterRepository;
import com.cluster.digital.repo.DairyRepository;
import com.cluster.digital.repo.RouteRepository;
import com.cluster.digital.service.DairyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    public DairyDTOResponse createNewDairy(DairyDTORequest dairyDTORequest) throws Throwable {
        // validate cluster id
        Optional<Cluster> clusterOptional = clusterRepository.findById(dairyDTORequest.getClusterId());
        clusterOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No cluster found with id: " + dairyDTORequest.getClusterId()));

        // validate all route ids
        List<Route> allRoutes = routeRepository.findByIdIn(dairyDTORequest.getRouteIds());
        if (allRoutes.size() != dairyDTORequest.getRouteIds().size())
            throw new NotFoundException("Some route ids not found in database.");

        Dairy dairy = new Dairy();
        dairy.setName(dairyDTORequest.getName());
        dairy.setCluster(clusterOptional.get());
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
        Optional<Dairy> dairyOptional = dairyRepository.findById(dairyId);
        dairyOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No dairy present with id: " + dairyId));

        Collection<Route> routes = routeRepository.findByIdIn(routeIds);
        if (routes.size() != routeIds.size())
            throw new NotFoundException("Some route ids not found in database.");

        Dairy dairy = dairyOptional.get();
        dairy.getRoutes().addAll(routes);
        return dairyRepository.save(dairy).getResponseDTO();
    }
}
