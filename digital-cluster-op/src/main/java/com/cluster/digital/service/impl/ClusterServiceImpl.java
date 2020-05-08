package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;
import com.cluster.digital.database.repo.ClusterRepository;
import com.cluster.digital.service.ClusterService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;

    public ClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public ClusterDTOResponse createNewCluster(ClusterDTORequest clusterDTORequest) {
        Cluster cluster = new Cluster();
        cluster.setName(clusterDTORequest.getName());
        cluster.setDistrict(clusterDTORequest.getDistrict());
        cluster.setState(clusterDTORequest.getState());
        return clusterRepository.save(cluster).getResponseDTO();
    }

    @Override
    public List<ClusterDTOResponse> getAllClusters(String query) {
        List<Cluster> clusters = query == null ? clusterRepository.findAll() : clusterRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return clusters.stream().map(Cluster::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ClusterDTOResponse getCluster(String clusterId) throws Throwable {
        return check4ClusterExistence(clusterRepository, clusterId).getResponseDTO();
    }
}
