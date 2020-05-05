package com.cluster.digital.repo;

import com.cluster.digital.database.entity.Mcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Repository
public interface MccRepository extends JpaRepository<Mcc, String> {
    Collection<Mcc> findByIdIn(Collection<String> ids);

    Collection<Mcc> findByNameIgnoreCaseContainingOrDistrictIgnoreCaseContainingOrStateIgnoreCaseContaining(String name, String district, String state);
}
