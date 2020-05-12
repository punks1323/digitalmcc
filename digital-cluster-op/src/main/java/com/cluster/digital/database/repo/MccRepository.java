package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Mcc;
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
public interface MccRepository extends JpaRepository<Mcc, String> {
    List<Mcc> findByIdIn(Collection<String> ids);

    List<Mcc> findByNameIgnoreCaseContainingOrAddressIgnoreCaseContainingOrVillageIgnoreCaseContainingOrTalukIgnoreCaseContainingOrPincodeIgnoreCaseContaining(String name, String address, String village, String taluk, String pincode);

    @Query("SELECT id FROM Mcc WHERE route_id = :routeId")
    List<String> findMccIdByRouteId(@Param("routeId") String routeId);
}
