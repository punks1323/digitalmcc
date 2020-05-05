package com.cluster.digital.service;

import com.cluster.digital.model.request.RouteDTORequest;
import com.cluster.digital.model.response.RouteDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public interface RouteService {
    RouteDTOResponse createNewRoute(RouteDTORequest routeDTORequest) throws Throwable;

    List<RouteDTOResponse> getAllRoutes(String query);

    RouteDTOResponse addMccToRoutes(String dairyId, List<String> mccIds) throws Throwable;
}
