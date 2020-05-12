package com.cluster.digital.api.admin;

import com.cluster.digital.exception.IdsNotFoundInDatabaseException;
import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;
import com.cluster.digital.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@RestController
@RequestMapping("/admin/cluster")
public class ClusterController {

    @Autowired
    ClusterService clusterService;

    @PostMapping
    public List<ClusterDTOResponse> createCluster(@Valid @RequestBody ClusterDTORequest request) throws IdsNotFoundInDatabaseException {
        return clusterService.createNewCluster(request);
    }

    @GetMapping
    public List<ClusterDTOResponse> getAllClusters(@RequestParam(value = "search", required = false) String query) {
        return clusterService.getAllClusters(query);
    }

    @GetMapping("/{clusterId}")
    public List<ClusterDTOResponse> getCluster(@PathVariable("clusterId") String clusterId) throws Throwable {
        return clusterService.getCluster(clusterId);
    }

    @PatchMapping("/{clusterId}")
    public ClusterDTOResponse updateCluster(@PathVariable("clusterId") String clusterId, @RequestBody ClusterDTORequest request) throws Throwable {
        return clusterService.updateCluster(clusterId, request);
    }
}
