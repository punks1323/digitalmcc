package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.ClusterRepository;
import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;
import com.cluster.digital.service.DairyService;
import com.cluster.digital.utils.BeanUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    public List<DairyDTOResponse> createNewDairy(DairyDTORequest request) throws Throwable {

        // validate cluster id
        Cluster cluster = check4ClusterExistence(clusterRepository, request.getClusterId());

        Dairy dairy = BeanUtils.copyBeanProperties(request, new Dairy());
        dairy.setCluster(cluster);

        Dairy savedDairy = dairyRepository.save(dairy);
        return attachRouteIdsAndReturn(Collections.singletonList(savedDairy));
    }

    @Override
    public List<DairyDTOResponse> getAllDairies(String query) {
        List<Dairy> dairies = query == null ? dairyRepository.findAll() : dairyRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return attachRouteIdsAndReturn(dairies);
    }

    @Override
    public List<DairyDTOResponse> updateDairy(String dairyId, DairyDTORequest request) throws Throwable {

        // verify that dairy exists
        Dairy dairy = check4DairyExistence(dairyRepository, dairyId);

        // verify that all route ids are correct
        Collection<Route> routes = verifyAllRouteIds(routeRepository, request.getRouteIds());

        BeanUtils.copyBeanProperties(request, dairy);
        dairyRepository.save(dairy);

        // set new dairy for all given routes
        List<Route> modifiedRoutes = routes.stream().map(r -> {
            r.setDairy(dairy);
            return r;
        }).collect(Collectors.toList());

        // update routes
        routeRepository.saveAll(modifiedRoutes);

        return attachRouteIdsAndReturn(Collections.singletonList(dairy));
    }

    private List<DairyDTOResponse> attachRouteIdsAndReturn(List<Dairy> dairies) {
        return dairies.stream().map(dairy -> {
            DairyDTOResponse response = dairy.getResponseDTO();
            response.setRoutes(routeRepository.findRouteIdByDairyId(dairy.getId()));
            return response;
        }).collect(Collectors.toList());
    }
}
