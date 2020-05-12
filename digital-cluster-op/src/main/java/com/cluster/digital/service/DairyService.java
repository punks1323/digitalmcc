package com.cluster.digital.service;

import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface DairyService extends BaseInterface {
    List<DairyDTOResponse> createNewDairy(DairyDTORequest dairyDTORequest) throws Throwable;

    List<DairyDTOResponse> getAllDairies(String query);

    List<DairyDTOResponse> updateDairy(String dairyId, DairyDTORequest request) throws Throwable;
}
