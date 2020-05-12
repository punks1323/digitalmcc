package com.cluster.digital.service;

import com.cluster.digital.exception.IdsNotFoundInDatabaseException;
import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface ClusterService extends BaseInterface {
    List<ClusterDTOResponse> createNewCluster(ClusterDTORequest clusterDTORequest) throws IdsNotFoundInDatabaseException;

    List<ClusterDTOResponse> getAllClusters(String query);

    List<ClusterDTOResponse> getCluster(String clusterId) throws Throwable;

    ClusterDTOResponse updateCluster(String clusterId, ClusterDTORequest request) throws Throwable;
}
