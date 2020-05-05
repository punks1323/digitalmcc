package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.RouteDTORequest;
import com.cluster.digital.model.response.RouteDTOResponse;
import com.cluster.digital.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<RouteDTOResponse> getAllRoutes(@RequestParam(value = "search", required = false) String query) {
        return routeService.getAllRoutes(query);
    }

    @PostMapping
    public RouteDTOResponse createNewRoute(@Valid @RequestBody RouteDTORequest routeDTORequest) throws Throwable {
        return routeService.createNewRoute(routeDTORequest);
    }

    @PatchMapping("/{routeId}")
    public RouteDTOResponse addRoutesToDairy(@PathVariable("routeId") String routeId, @RequestBody List<String> mccIds) throws Throwable {
        return routeService.addMccToRoutes(routeId, mccIds);
    }
}
