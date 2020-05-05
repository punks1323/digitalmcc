package com.cluster.digital.service;

import com.cluster.digital.database.entity.AppUser;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
public interface AppUserService {

    AppUser createAdmin(String username, String password);

    AppUser createFieldEngineer(String username);

}
