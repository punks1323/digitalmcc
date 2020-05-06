package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Farmer;
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
public interface FarmerRepository extends JpaRepository<Farmer, String> {
    List<Farmer> findByIdIn(Collection<String> ids);

    List<Farmer> findByFirstNameIgnoreCaseContainingOrMobileNumberIgnoreCaseContainingOrVillageIgnoreCaseContaining(String name, String mobileNumber, String village);
}
