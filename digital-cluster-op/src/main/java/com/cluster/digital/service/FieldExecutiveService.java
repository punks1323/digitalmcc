package com.cluster.digital.service;

import com.cluster.digital.model.request.ClusterDTORequest;
import com.cluster.digital.model.request.FieldExecutiveDTORequest;
import com.cluster.digital.model.response.ClusterDTOResponse;
import com.cluster.digital.model.response.FieldExecutiveDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
public interface FieldExecutiveService extends BaseInterface {
    FieldExecutiveDTOResponse createNewFieldExecutive(FieldExecutiveDTORequest fieldExecutiveDTORequest) throws Throwable;

    List<FieldExecutiveDTOResponse> getAllFieldExecutives(String query);

    FieldExecutiveDTOResponse getFieldExecutive(String fieldExecutiveId) throws Throwable;
}
