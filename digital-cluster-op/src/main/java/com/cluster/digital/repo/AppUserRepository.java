package com.cluster.digital.repo;

import com.cluster.digital.database.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    Boolean existsByUsername(String username);

    Optional<AppUser> findByUsername(String username);

    @Query("SELECT u.username FROM AppUser u where u.id = :id")
    String findUsernameByUserId(@Param("id") String id);
}
