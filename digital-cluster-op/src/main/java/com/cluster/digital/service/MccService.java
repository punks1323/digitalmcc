package com.cluster.digital.service;

import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
public interface MccService extends BaseInterface{
    MccDTOResponse createNewMcc(MccDTORequest mccDTORequest) throws Throwable;

    MccDTOResponse getMcc(String mccId) throws Throwable;

    List<MccDTOResponse> getAllMcc(String query);

    MccDTOResponse updateImage(String mccId, MultipartFile multipartFile) throws Throwable;
}
