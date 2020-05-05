package com.cluster.digital.service;

import com.cluster.digital.database.entity.Route;
import com.cluster.digital.model.request.RouteDTO;

import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
public interface RouteService {
    Route createNewRoute(RouteDTO routeDTO) throws Throwable;

    Collection<Route> getAllRoutes(String query);
}
