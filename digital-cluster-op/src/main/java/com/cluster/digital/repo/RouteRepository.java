package com.cluster.digital.repo;

import com.cluster.digital.database.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
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
    Collection<Route> findByIdIn(Collection<String> ids);

    List<Route> findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(String name, String district, String state);
}
