package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Farmer;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.repo.FarmerRepository;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.FarmerDTORequest;
import com.cluster.digital.model.response.FarmerDTOResponse;
import com.cluster.digital.service.FarmerService;
import com.cluster.digital.service.FileStorageService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
public class FarmerServiceImpl implements FarmerService {
    private MccRepository mccRepository;
    private FarmerRepository farmerRepository;
    private FileStorageService fileStorageService;

    public FarmerServiceImpl(MccRepository mccRepository, FarmerRepository farmerRepository, FileStorageService fileStorageService) {
        this.mccRepository = mccRepository;
        this.farmerRepository = farmerRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public FarmerDTOResponse createNewFarmer(FarmerDTORequest request) throws Throwable {

        Optional<Mcc> mccOptional = mccRepository.findById(request.getMccId());

        mccOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No mcc found with id: " + request.getMccId()));

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
        farmer.setMcc(mccOptional.get());

        Farmer savedFarmer = farmerRepository.save(farmer);
        String farmerKycImgLocation = fileStorageService.saveFile(FileStorageService.ImageType.FARMER_IMAGE, request.getKycImage(), savedFarmer.getId());
        savedFarmer.setKycImage(farmerKycImgLocation);
        return farmerRepository.save(savedFarmer).getResponseDTO();
    }

    @Override
    public FarmerDTOResponse getFarmer(String farmerId) throws Throwable {
        Optional<Farmer> farmerOptional = farmerRepository.findById(farmerId);
        farmerOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No farmer found with id: " + farmerId));
        return farmerOptional.get().getResponseDTO();
    }

    @Override
    public List<FarmerDTOResponse> getFarmers(String query) {
        List<Farmer> farmers = query == null ? farmerRepository.findAll() : farmerRepository.findByFirstNameIgnoreCaseContainingOrMobileNumberIgnoreCaseContainingOrVillageIgnoreCaseContaining(query, query, query);
        return farmers.stream().map(Farmer::getResponseDTO).collect(Collectors.toList());
    }
}
