package com.cluster.digital.service;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.model.request.ClusterDTO;

import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface ClusterService {
    Cluster createNewCluster(ClusterDTO clusterDTO);

    Collection<Cluster> getAllClusters(String query);
}
