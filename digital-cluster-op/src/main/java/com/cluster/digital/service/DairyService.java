package com.cluster.digital.service;

import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface DairyService {
    DairyDTOResponse createNewDairy(DairyDTORequest dairyDTORequest) throws Throwable;

    List<DairyDTOResponse> getAllDairies(String query);

    DairyDTOResponse addRoutesToDairy(String dairyId, List<String> routeIds) throws Throwable;
}
