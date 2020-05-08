package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Farmer;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.repo.FarmerRepository;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.model.request.FarmerDTORequest;
import com.cluster.digital.model.request.FarmerKycUpdateRequest;
import com.cluster.digital.model.response.FarmerDTOResponse;
import com.cluster.digital.service.FarmerService;
import com.cluster.digital.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
@Slf4j
public class FarmerServiceImpl implements FarmerService {
    private final MccRepository mccRepository;
    private final FarmerRepository farmerRepository;
    private final FileStorageService fileStorageService;

    public FarmerServiceImpl(MccRepository mccRepository, FarmerRepository farmerRepository, FileStorageService fileStorageService) {
        this.mccRepository = mccRepository;
        this.farmerRepository = farmerRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public FarmerDTOResponse createNewFarmer(FarmerDTORequest request) throws Throwable {

        Mcc mcc = check4MccExistence(mccRepository, request.getMccId());

        Farmer farmer = new Farmer();
        farmer.setFirstName(request.getFirstName());
        farmer.setMiddleName(request.getMiddleName());
        farmer.setLastName(request.getLastName());
        farmer.setMobileNumber(request.getMobileNumber());
        farmer.setHouseNo(request.getHouseNo());
        farmer.setVillage(request.getVillage());
        farmer.setTaluk(request.getTaluk());
        farmer.setPincode(request.getTaluk());
        farmer.setLatitude(request.getLatitude());
        farmer.setLongitude(request.getLongitude());
        farmer.setKycNumber(request.getKycNumber());
        farmer.setKycType(request.getKycType());
        farmer.setMcc(mcc);

        Farmer savedFarmer = farmerRepository.save(farmer);
        String farmerKycImgLocation = fileStorageService.saveFile(FileStorageService.ImageType.FARMER, request.getKycImage(), savedFarmer.getId());
        savedFarmer.setKycImage(farmerKycImgLocation);
        return farmerRepository.save(savedFarmer).getResponseDTO();
    }

    @Override
    public FarmerDTOResponse getFarmer(String farmerId) throws Throwable {
        return check4FarmerExistence(farmerRepository, farmerId).getResponseDTO();
    }

    @Override
    public List<FarmerDTOResponse> getFarmers(String query) {
        List<Farmer> farmers = query == null ? farmerRepository.findAll() : farmerRepository.findByFirstNameIgnoreCaseContainingOrMobileNumberIgnoreCaseContainingOrVillageIgnoreCaseContaining(query, query, query);
        return farmers.stream().map(Farmer::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public FarmerDTOResponse updateFarmerKyc(String farmerId, FarmerKycUpdateRequest kycUpdateRequest) throws Throwable {
        Farmer savedFarmer = check4FarmerExistence(farmerRepository, farmerId);

        savedFarmer.setKycType(kycUpdateRequest.getKycType());
        savedFarmer.setKycNumber(kycUpdateRequest.getKycNumber());

        if (kycUpdateRequest.getKycImage() != null && !kycUpdateRequest.getKycImage().isEmpty()) {
            // delete old farmer image
            if (savedFarmer.getKycImage() != null && savedFarmer.getKycImage().length() > 0) {
                String[] split = savedFarmer.getKycImage().split("/");
                String fileName = split[split.length - 1];
                Boolean deleteResult = fileStorageService.deleteFile(FileStorageService.ImageType.FARMER, fileName);
                log.info("Old csp kyc image deleted: " + deleteResult);
            }

            // save new farmer image
            String kycImageLocation = fileStorageService.saveFile(FileStorageService.ImageType.FARMER, kycUpdateRequest.getKycImage(), savedFarmer.getId());
            savedFarmer.setKycImage(kycImageLocation);
        }

        return farmerRepository.save(savedFarmer).getResponseDTO();
    }
}
