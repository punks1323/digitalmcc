package com.cluster.digital.database.repo;

import com.cluster.digital.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String role);
}
