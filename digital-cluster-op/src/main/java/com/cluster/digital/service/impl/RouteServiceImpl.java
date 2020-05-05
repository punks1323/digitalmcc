package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.RouteDTO;
import com.cluster.digital.repo.DairyRepository;
import com.cluster.digital.repo.MccRepository;
import com.cluster.digital.repo.RouteRepository;
import com.cluster.digital.service.RouteService;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

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
    public Route createNewRoute(RouteDTO routeDTO) throws Throwable {
        // validate dairy id
        Optional<Dairy> dairyOptional = dairyRepository.findById(routeDTO.getDairyId());
        dairyOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Dairy with id " + routeDTO.getDairyId() + " does not exists."));

        // validate mcc ids
        Collection<String> mccIds = routeDTO.getMccIds();
        Collection<Mcc> mccList = mccRepository.findByIdIn(mccIds);
        if (mccList.size() != mccIds.size())
            throw new NotFoundException("Some mcc ids not found in database");

        Route route = new Route();
        route.setName(route.getName());
        route.setDairy(dairyOptional.get());
        route.setDistrict(routeDTO.getDistrict());
        route.setState(routeDTO.getState());
        route.setMccs(mccList);
        return routeRepository.save(route);
    }

    @Override
    public Collection<Route> getAllRoutes(String query) {
        return routeRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
    }
}
