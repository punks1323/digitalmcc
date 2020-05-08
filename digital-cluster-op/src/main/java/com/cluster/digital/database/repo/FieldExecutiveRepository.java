package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.FieldExecutive;
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
public interface FieldExecutiveRepository extends JpaRepository<FieldExecutive, String> {
    List<FieldExecutive> findByIdIn(Collection<String> ids);

    List<FieldExecutive> findByNameIgnoreCaseContainingOrMobileIgnoreCaseContaining(String name, String mobile);
}
