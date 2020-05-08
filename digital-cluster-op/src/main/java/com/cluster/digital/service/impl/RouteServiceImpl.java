package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.exception.AllIdDoesNotFoundException;
import com.cluster.digital.model.request.RouteDTORequest;
import com.cluster.digital.model.response.RouteDTOResponse;
import com.cluster.digital.service.RouteService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final DairyRepository dairyRepository;
    private final MccRepository mccRepository;

    public RouteServiceImpl(RouteRepository routeRepository, DairyRepository dairyRepository, MccRepository mccRepository) {
        this.routeRepository = routeRepository;
        this.dairyRepository = dairyRepository;
        this.mccRepository = mccRepository;
    }

    @Override
    public RouteDTOResponse createNewRoute(RouteDTORequest routeDTORequest) throws Throwable {
        // validate dairy id
        Dairy dairy = check4DairyExistence(dairyRepository, routeDTORequest.getDairyId());

        // validate mcc ids
        Collection<String> mccIds = routeDTORequest.getMccIds();
        List<Mcc> mccList = mccRepository.findByIdIn(mccIds);
        if (mccList.size() != mccIds.size())
            throw new AllIdDoesNotFoundException("Some mcc ids not found in database: " + mccList);

        Route route = new Route();
        route.setName(routeDTORequest.getName());
        route.setDistrict(routeDTORequest.getDistrict());
        route.setState(routeDTORequest.getState());
        route.setDairy(dairy);
        route.setMccs(mccList);
        return routeRepository.save(route).getResponseDTO();
    }

    @Override
    public List<RouteDTOResponse> getAllRoutes(String query) {
        List<Route> routes = query == null ?
                routeRepository.findAll() :
                routeRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return routes.stream().map(Route::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public RouteDTOResponse addMccToRoutes(String routeId, List<String> mccIds) throws Throwable {

        Collection<Mcc> mccList = mccRepository.findByIdIn(mccIds);
        if (mccList.size() != mccIds.size())
            throw new AllIdDoesNotFoundException("Some mcc ids not found in database: " + mccIds);

        Route route = check4RouteExistence(routeRepository, routeId);
        route.getMccs().addAll(mccList);
        return routeRepository.save(route).getResponseDTO();
    }

    @Override
    public RouteDTOResponse getRoute(String routeId) throws Throwable {
        return check4RouteExistence(routeRepository, routeId).getResponseDTO();
    }
}
