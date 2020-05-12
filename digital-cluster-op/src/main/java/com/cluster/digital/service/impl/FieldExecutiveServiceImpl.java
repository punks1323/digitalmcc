package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.FieldExecutive;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.FieldExecutiveRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.FieldExecutiveDTORequest;
import com.cluster.digital.model.response.FieldExecutiveDTOResponse;
import com.cluster.digital.service.FieldExecutiveService;
import com.cluster.digital.utils.BeanUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-08
 */
public class FieldExecutiveServiceImpl implements FieldExecutiveService {

    FieldExecutiveRepository fieldExecutiveRepository;
    RouteRepository routeRepository;

    public FieldExecutiveServiceImpl(FieldExecutiveRepository fieldExecutiveRepository, RouteRepository routeRepository) {
        this.fieldExecutiveRepository = fieldExecutiveRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public List<FieldExecutiveDTOResponse> createNewFieldExecutive(FieldExecutiveDTORequest request) throws Throwable {

        // verify that a route exists with given route id
        Route route = check4RouteExistence(routeRepository, request.getRouteId());

        // save field executive
        FieldExecutive fieldExecutive = BeanUtils.copyBeanProperties(request, new FieldExecutive());
        FieldExecutive savedFE = fieldExecutiveRepository.save(fieldExecutive);

        // set field executive on route
        route.setFieldExecutive(savedFE);

        // save route
        routeRepository.save(route);
        return attachRoutesAndReturn(Collections.singletonList(savedFE));
    }

    @Override
    public List<FieldExecutiveDTOResponse> getAllFieldExecutives(String query) {
        List<FieldExecutive> fieldExecutiveList = query == null ? fieldExecutiveRepository.findAll() : fieldExecutiveRepository.findByNameIgnoreCaseContainingOrMobileIgnoreCaseContaining(query, query);
        return attachRoutesAndReturn(fieldExecutiveList);
    }

    @Override
    public List<FieldExecutiveDTOResponse> getFieldExecutive(String fieldExecutiveId) throws Throwable {
        FieldExecutive fieldExecutive = check4FieldExecutiveExistence(fieldExecutiveRepository, fieldExecutiveId);
        return attachRoutesAndReturn(Collections.singletonList(fieldExecutive));
    }

    public List<FieldExecutiveDTOResponse> attachRoutesAndReturn(List<FieldExecutive> fieldExecutives) {
        return fieldExecutives
                .stream()
                .map(f -> {
                    FieldExecutiveDTOResponse response = f.getResponseDTO();
                    response.setRoutes(routeRepository.findRouteIdsByFieldExecutiveIdId(f.getId()));
                    return response;
                }).collect(Collectors.toList());
    }
}
