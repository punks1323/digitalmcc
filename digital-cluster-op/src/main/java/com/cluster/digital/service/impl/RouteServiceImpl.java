package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.RouteDTORequest;
import com.cluster.digital.model.response.RouteDTOResponse;
import com.cluster.digital.repo.DairyRepository;
import com.cluster.digital.repo.MccRepository;
import com.cluster.digital.repo.RouteRepository;
import com.cluster.digital.service.RouteService;

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
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;
    private DairyRepository dairyRepository;
    private MccRepository mccRepository;

    public RouteServiceImpl(RouteRepository routeRepository, DairyRepository dairyRepository, MccRepository mccRepository) {
        this.routeRepository = routeRepository;
        this.dairyRepository = dairyRepository;
        this.mccRepository = mccRepository;
    }

    @Override
    public RouteDTOResponse createNewRoute(RouteDTORequest routeDTORequest) throws Throwable {
        // validate dairy id
        Optional<Dairy> dairyOptional = dairyRepository.findById(routeDTORequest.getDairyId());
        dairyOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Dairy with id " + routeDTORequest.getDairyId() + " does not exists."));

        // validate mcc ids
        Collection<String> mccIds = routeDTORequest.getMccIds();
        List<Mcc> mccList = mccRepository.findByIdIn(mccIds);
        if (mccList.size() != mccIds.size())
            throw new NotFoundException("Some mcc ids not found in database");

        Route route = new Route();
        route.setName(routeDTORequest.getName());
        route.setDistrict(routeDTORequest.getDistrict());
        route.setState(routeDTORequest.getState());
        route.setDairy(dairyOptional.get());
        route.setMccs(mccList);
        return routeRepository.save(route).getResponseDTO();
    }

    @Override
    public List<RouteDTOResponse> getAllRoutes(String query) {
        List<Route> routes = routeRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return routes.stream().map(Route::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public RouteDTOResponse addMccToRoutes(String dairyId, List<String> mccIds) throws Throwable {
        Optional<Route> routeOptional = routeRepository.findById(dairyId);
        routeOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Route with id " + dairyId + " does not exists."));

        Collection<Mcc> mccList = mccRepository.findByIdIn(mccIds);
        if (mccList.size() != mccIds.size())
            throw new NotFoundException("Some mcc ids not found in database");
        Route route = routeOptional.get();
        route.getMccs().addAll(mccList);
        return routeRepository.save(route).getResponseDTO();
    }
}
