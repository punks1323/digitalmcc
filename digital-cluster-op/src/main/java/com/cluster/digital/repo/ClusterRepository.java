package com.cluster.digital.repo;

import com.cluster.digital.database.entity.Cluster;
import com.cluster.digital.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Repository
public interface ClusterRepository extends JpaRepository<Cluster, String> {
    List<Cluster> findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(String name, String district, String state);
}
