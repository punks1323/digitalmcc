package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Route;
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
public interface RouteRepository extends JpaRepository<Route, String> {

    @Query("SELECT id FROM Route WHERE dairy_id = :dairyId")
    List<String> findRouteIdByDairyId(@Param("dairyId") String dairyId);

    List<Route> findByNameIgnoreCaseContaining(String name);

    @Query("SELECT id FROM Route WHERE field_executive_id = :fieldExecutiveId")
    List<String> findRouteIdsByFieldExecutiveIdId(@Param("fieldExecutiveId") String fieldExecutiveId);

}
