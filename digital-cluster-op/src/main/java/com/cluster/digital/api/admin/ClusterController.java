package com.cluster.digital.api.admin;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.model.request.ClusterDTO;
import com.cluster.digital.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

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
    public Collection<Cluster> getAllClusters(@RequestParam(value = "search", required = false) String query) {
        return clusterService.getAllClusters(query);
    }

    @PostMapping
    public Cluster createCluster(@Valid @RequestBody ClusterDTO clusterDTO) {
        return clusterService.createNewCluster(clusterDTO);
    }
}
