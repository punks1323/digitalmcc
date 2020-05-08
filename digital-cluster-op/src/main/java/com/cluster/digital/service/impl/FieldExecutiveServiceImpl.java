package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.FieldExecutive;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.FieldExecutiveRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.FieldExecutiveDTORequest;
import com.cluster.digital.model.response.FieldExecutiveDTOResponse;
import com.cluster.digital.service.FieldExecutiveService;

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
    public FieldExecutiveDTOResponse createNewFieldExecutive(FieldExecutiveDTORequest request) throws Throwable {
        Route route = check4RouteExistence(routeRepository, request.getRouteId());

        FieldExecutive fieldExecutive = new FieldExecutive();
        fieldExecutive.setName(request.getName());
        fieldExecutive.setMobile(request.getMobile());
        fieldExecutive.setRoute(route);

        return fieldExecutiveRepository.save(fieldExecutive).getResponseDTO();
    }

    @Override
    public List<FieldExecutiveDTOResponse> getAllFieldExecutives(String query) {
        List<FieldExecutive> fieldExecutiveList = query == null ? fieldExecutiveRepository.findAll() : fieldExecutiveRepository.findByNameIgnoreCaseContainingOrMobileIgnoreCaseContaining(query, query);
        return fieldExecutiveList.stream().map(FieldExecutive::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public FieldExecutiveDTOResponse getFieldExecutive(String fieldExecutiveId) throws Throwable {
        return check4FieldExecutiveExistence(fieldExecutiveRepository, fieldExecutiveId).getResponseDTO();
    }
}
