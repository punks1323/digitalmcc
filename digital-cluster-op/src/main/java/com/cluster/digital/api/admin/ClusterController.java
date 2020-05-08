package com.cluster.digital.api.admin;

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

    @GetMapping
    public List<ClusterDTOResponse> getAllClusters(@RequestParam(value = "search", required = false) String query) {
        return clusterService.getAllClusters(query);
    }

    @GetMapping("/{clusterId}")
    public ClusterDTOResponse getCluster(@PathVariable("clusterId") String clusterId) throws Throwable {
        return clusterService.getCluster(clusterId);
    }

    @PostMapping
    public ClusterDTOResponse createCluster(@Valid @RequestBody ClusterDTORequest clusterDTORequest) {
        return clusterService.createNewCluster(clusterDTORequest);
    }
}
