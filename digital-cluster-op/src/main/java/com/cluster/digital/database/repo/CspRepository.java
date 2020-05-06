package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Csp;
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
public interface CspRepository extends JpaRepository<Csp, String> {
    List<Csp> findByIdIn(Collection<String> ids);

    List<Csp> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContainingOrMobileNumberIgnoreCaseContainingOrKycNumberIgnoreCaseContaining(String firstName, String lastName, String mobileNumber, String kycNumber);
}
