package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.model.request.ClusterDTO;
import com.cluster.digital.repo.ClusterRepository;
import com.cluster.digital.service.ClusterService;

import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public class ClusterServiceImpl implements ClusterService {

    private ClusterRepository clusterRepository;

    public ClusterServiceImpl(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Override
    public Cluster createNewCluster(ClusterDTO clusterDTO) {
        Cluster cluster = new Cluster();
        cluster.setName(clusterDTO.getName());
        cluster.setDistrict(clusterDTO.getDistrict());
        cluster.setState(clusterDTO.getState());
        return clusterRepository.save(cluster);
    }

    @Override
    public Collection<Cluster> getAllClusters(String query) {
        return query == null ? clusterRepository.findAll() : clusterRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
    }
}
