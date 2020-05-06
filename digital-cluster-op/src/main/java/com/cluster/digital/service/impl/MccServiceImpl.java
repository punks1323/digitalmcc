package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.FileStorageService;
import com.cluster.digital.service.MccService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
public class MccServiceImpl implements MccService {
    private MccRepository mccRepository;
    private RouteRepository routeRepository;
    private FileStorageService fileStorageService;

    public MccServiceImpl(MccRepository mccRepository, RouteRepository routeRepository, FileStorageService fileStorageService) {
        this.mccRepository = mccRepository;
        this.routeRepository = routeRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public MccDTOResponse createNewMcc(MccDTORequest mccDTORequest) throws Throwable {
        Optional<Route> route = routeRepository.findById(mccDTORequest.getRouteId());
        route.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No route found with given id: " + mccDTORequest.getRouteId()));

        Mcc mcc = new Mcc();
        mcc.setName(mccDTORequest.getName());
        mcc.setAddress(mccDTORequest.getAddress());
        mcc.setPincode(mccDTORequest.getPincode());
        mcc.setVillage(mccDTORequest.getVillage());
        mcc.setTaluk(mccDTORequest.getTaluk());
        mcc.setLatitude(mccDTORequest.getLatitude());
        mcc.setLongitude(mccDTORequest.getLongitude());
        mcc.setRoute(route.get());

        Mcc savedMcc = mccRepository.save(mcc);
        if (!mccDTORequest.getMccImage().isEmpty()) {
            String fileDownloadUri = fileStorageService.saveFile(FileStorageService.ImageType.MCC_IMAGE, mccDTORequest.getMccImage(), savedMcc.getId());
            mcc.setImage(fileDownloadUri);
            savedMcc = mccRepository.save(mcc);
        }
        return savedMcc.getResponseDTO();
    }

    @Override
    public MccDTOResponse getMcc(String mccId) throws Throwable {
        Optional<Mcc> mccOptional = mccRepository.findById(mccId);
        mccOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No mcc found with id: " + mccId));
        return mccOptional.get().getResponseDTO();
    }

    @Override
    public List<MccDTOResponse> getAllMcc(String query) {
        List<Mcc> mccs = query == null ? mccRepository.findAll() : mccRepository.findByNameIgnoreCaseContainingOrAddressIgnoreCaseContainingOrVillageIgnoreCaseContainingOrTalukIgnoreCaseContainingOrPincodeIgnoreCaseContaining(query, query, query, query, query);
        return mccs.stream().map(Mcc::getResponseDTO).collect(Collectors.toList());
    }
}
