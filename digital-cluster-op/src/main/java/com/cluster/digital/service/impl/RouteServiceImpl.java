package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.database.repo.FieldExecutiveRepository;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.RouteDTORequest;
import com.cluster.digital.model.response.RouteDTOResponse;
import com.cluster.digital.service.RouteService;
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
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final DairyRepository dairyRepository;
    private final MccRepository mccRepository;
    private final FieldExecutiveRepository fieldExecutiveRepository;

    public RouteServiceImpl(RouteRepository routeRepository, DairyRepository dairyRepository, MccRepository mccRepository, FieldExecutiveRepository fieldExecutiveRepository) {
        this.routeRepository = routeRepository;
        this.dairyRepository = dairyRepository;
        this.mccRepository = mccRepository;
        this.fieldExecutiveRepository = fieldExecutiveRepository;
    }

    @Override
    public List<RouteDTOResponse> createNewRoute(RouteDTORequest request) throws Throwable {

        // validate dairy id
        Dairy dairy = check4DairyExistence(dairyRepository, request.getDairyId());

        // validate mcc ids
        Collection<Mcc> mccs = verifyAllMccIds(mccRepository, request.getMccIds());

        // create new route
        Route route = BeanUtils.copyBeanProperties(request, new Route());
        route.setDairy(dairy);

        // check for field executive existence and set
        if (request.getFieldExecutiveId() != null)
            route.setFieldExecutive(check4FieldExecutiveExistence(fieldExecutiveRepository, request.getFieldExecutiveId()));

        // save route
        Route savedRoute = routeRepository.save(route);

        // set route to given mccs
        List<Mcc> updatedMccs = mccs.stream().map(m -> {
            m.setRoute(savedRoute);
            return m;
        }).collect(Collectors.toList());

        mccRepository.saveAll(updatedMccs);

        return attachMccIdAndReturn(Collections.singletonList(savedRoute));
    }

    @Override
    public List<RouteDTOResponse> getAllRoutes(String query) {
        List<Route> routes = query == null ?
                routeRepository.findAll() :
                routeRepository.findByNameIgnoreCaseContaining(query);
        return attachMccIdAndReturn(routes);
    }

    @Override
    public List<RouteDTOResponse> updateRoute(String routeId, RouteDTORequest request) throws Throwable {

        // verify route id
        Route route = check4RouteExistence(routeRepository, routeId);

        // verify mcc ids
        Collection<Mcc> mccs = verifyAllMccIds(mccRepository, request.getMccIds());

        BeanUtils.copyBeanProperties(request, route);

        // check for dairy existence and set
        if (request.getDairyId() != null)
            route.setDairy(check4DairyExistence(dairyRepository, request.getDairyId()));

        // check for field executive existence and set
        if (request.getFieldExecutiveId() != null)
            route.setFieldExecutive(check4FieldExecutiveExistence(fieldExecutiveRepository, request.getFieldExecutiveId()));

        Route savedRoute = routeRepository.save(route);
        return attachMccIdAndReturn(Collections.singletonList(savedRoute));
    }

    @Override
    public RouteDTOResponse getRoute(String routeId) throws Throwable {
        return check4RouteExistence(routeRepository, routeId).getResponseDTO();
    }

    public List<RouteDTOResponse> attachMccIdAndReturn(List<Route> routeList) {
        return routeList.stream().map(r -> {
            RouteDTOResponse response = r.getResponseDTO();
            response.setMccList(mccRepository.findMccIdByRouteId(r.getId()));
            return response;
        }).collect(Collectors.toList());
    }
}
