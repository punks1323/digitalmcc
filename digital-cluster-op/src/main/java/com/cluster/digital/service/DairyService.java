package com.cluster.digital.service;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.model.request.DairyDTO;

import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface DairyService {
    Dairy createNewDairy(DairyDTO dairyDTO) throws Throwable;

    Collection<Dairy> getAllDairies(String query);
}
