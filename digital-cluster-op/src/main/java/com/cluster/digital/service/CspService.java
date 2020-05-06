package com.cluster.digital.service;

import com.cluster.digital.model.request.CspDTORequest;
import com.cluster.digital.model.response.CspDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
public interface CspService {
    CspDTOResponse createNewCsp(CspDTORequest cspDTORequest) throws Throwable;

    List<CspDTOResponse> getAllCsp();

    CspDTOResponse getCsp(String cspId) throws Throwable;
}
