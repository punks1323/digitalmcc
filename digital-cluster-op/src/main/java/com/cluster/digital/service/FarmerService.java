package com.cluster.digital.service;

import com.cluster.digital.model.request.FarmerDTORequest;
import com.cluster.digital.model.response.FarmerDTOResponse;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
public interface FarmerService {
    FarmerDTOResponse createNewFarmer(FarmerDTORequest farmerDTORequest) throws Throwable;

    FarmerDTOResponse getFarmer(String farmerId) throws Throwable;

    List<FarmerDTOResponse> getFarmers(String query);
}
