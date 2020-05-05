package com.cluster.digital.repo;

import com.cluster.digital.database.entity.Mcc;
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
public interface MccRepository extends JpaRepository<Mcc, String> {
    List<Mcc> findByIdIn(Collection<String> ids);

    Collection<Mcc> findByNameIgnoreCaseContainingOrAddressIgnoreCaseContainingOrVillageIgnoreCaseContainingOrTalukIgnoreCaseContainingOrPincodeIgnoreCaseContaining(String name, String address, String village, String taluk, String pincode);
}
