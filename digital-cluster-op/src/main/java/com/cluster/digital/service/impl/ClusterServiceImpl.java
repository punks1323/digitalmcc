package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.database.repo.DairyRepository;
import com.cluster.digital.exception.IdsNotFoundInDatabaseException;
import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;
import com.cluster.digital.database.repo.ClusterRepository;
import com.cluster.digital.service.ClusterService;
import com.cluster.digital.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Slf4j
public class ClusterServiceImpl implements ClusterService {

    private final ClusterRepository clusterRepository;
    private final DairyRepository dairyRepository;

    public ClusterServiceImpl(ClusterRepository clusterRepository, DairyRepository dairyRepository) {
        this.clusterRepository = clusterRepository;
        this.dairyRepository = dairyRepository;
    }

    @Override
    public List<ClusterDTOResponse> createNewCluster(ClusterDTORequest request) throws IdsNotFoundInDatabaseException {
        // Create new cluster
        Cluster cluster = BeanUtils.copyBeanProperties(request, new Cluster());
        Cluster savedCluster = clusterRepository.save(cluster);
        return addDairiesAndReturn(Collections.singletonList(savedCluster));
    }

    @Override
    public List<ClusterDTOResponse> getAllClusters(String query) {
        List<Cluster> clusters = query == null ? clusterRepository.findAll() : clusterRepository.findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(query, query, query);
        return addDairiesAndReturn(clusters);
    }

    @Override
    public List<ClusterDTOResponse> getCluster(String clusterId) throws Throwable {
        Cluster savedCluster = check4ClusterExistence(clusterRepository, clusterId);
        return addDairiesAndReturn(Collections.singletonList(savedCluster));
    }

    @Override
    public ClusterDTOResponse updateCluster(String clusterId, ClusterDTORequest request) throws Throwable {
        Cluster savedCluster = check4ClusterExistence(clusterRepository, clusterId);

        // copy normal properties
        BeanUtils.copyBeanProperties(request, savedCluster);

        // attach cluster ids to dairies
        setClusterIdToDairies(savedCluster, request.getDairies());

        return null;
    }

    private void setClusterIdToDairies(Cluster savedCluster, Collection<String> dairiesId) throws IdsNotFoundInDatabaseException {
        // Set this cluster pk to given dairies
        Collection<Dairy> dairiesToBeAttached = verifyAllDairyIds(dairyRepository, dairiesId);
        List<Dairy> updatedDairies = dairiesToBeAttached.stream().map(dairy -> {
            dairy.setCluster(savedCluster);
            return dairy;
        }).collect(Collectors.toList());
        dairyRepository.saveAll(updatedDairies);
    }

    private List<ClusterDTOResponse> addDairiesAndReturn(List<Cluster> clusters) {
        return clusters.stream().map(c -> {
            ClusterDTOResponse response = c.getResponseDTO();
            response.setDairies(dairyRepository.findDairyIdByClusterId(c.getId()));
            return response;
        }).collect(Collectors.toList());
    }
}
