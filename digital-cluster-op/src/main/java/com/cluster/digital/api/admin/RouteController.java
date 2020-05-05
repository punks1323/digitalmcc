package com.cluster.digital.api.admin;

import com.cluster.digital.database.entity.Route;
import com.cluster.digital.model.request.RouteDTO;
import com.cluster.digital.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/admin/route")
public class RouteController {

    @Autowired
    RouteService routeService;

    @GetMapping
    public Collection<Route> getAllRoutes(@RequestParam(value = "search", required = false) String query) {
        return routeService.getAllRoutes(query);
    }

    @PostMapping
    public Route createNewRoute(@Valid @RequestBody RouteDTO routeDTO) throws Throwable {
        return routeService.createNewRoute(routeDTO);
    }
}
