package com.cluster.digital.service;

import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface ClusterService extends BaseInterface {
    ClusterDTOResponse createNewCluster(ClusterDTORequest clusterDTORequest);

    List<ClusterDTOResponse> getAllClusters(String query);

    ClusterDTOResponse getCluster(String clusterId) throws Throwable;
}
