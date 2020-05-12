package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Dairy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Repository
public interface DairyRepository extends JpaRepository<Dairy, String> {
    List<Dairy> findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(String name, String district, String state);

    @Query("SELECT id FROM Dairy WHERE cluster_id = :clusterId")
    List<String> findDairyIdByClusterId(@Param("clusterId") String clusterId);
}
