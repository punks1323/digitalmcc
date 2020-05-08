package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.FileStorageService;
import com.cluster.digital.service.MccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
@Slf4j
public class MccServiceImpl implements MccService {
    private final MccRepository mccRepository;
    private final RouteRepository routeRepository;
    private final FileStorageService fileStorageService;

    public MccServiceImpl(MccRepository mccRepository, RouteRepository routeRepository, FileStorageService fileStorageService) {
        this.mccRepository = mccRepository;
        this.routeRepository = routeRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public MccDTOResponse createNewMcc(MccDTORequest mccDTORequest) throws Throwable {
        Route route = check4RouteExistence(routeRepository, mccDTORequest.getRouteId());

        Mcc mcc = new Mcc();
        mcc.setName(mccDTORequest.getName());
        mcc.setAddress(mccDTORequest.getAddress());
        mcc.setPincode(mccDTORequest.getPincode());
        mcc.setVillage(mccDTORequest.getVillage());
        mcc.setTaluk(mccDTORequest.getTaluk());
        mcc.setLatitude(mccDTORequest.getLatitude());
        mcc.setLongitude(mccDTORequest.getLongitude());
        mcc.setRoute(route);

        Mcc savedMcc = mccRepository.save(mcc);
        if (mccDTORequest.getMccImage() != null && !mccDTORequest.getMccImage().isEmpty()) {
            String fileDownloadUri = fileStorageService.saveFile(FileStorageService.ImageType.MCC, mccDTORequest.getMccImage(), savedMcc.getId());
            log.info("Mcc {} new mcc image received.", savedMcc.getId());
            mcc.setImage(fileDownloadUri);
            savedMcc = mccRepository.save(mcc);
        }
        return savedMcc.getResponseDTO();
    }

    @Override
    public MccDTOResponse getMcc(String mccId) throws Throwable {
        return check4MccExistence(mccRepository, mccId).getResponseDTO();
    }

    @Override
    public List<MccDTOResponse> getAllMcc(String query) {
        List<Mcc> mccs = query == null ? mccRepository.findAll() : mccRepository.findByNameIgnoreCaseContainingOrAddressIgnoreCaseContainingOrVillageIgnoreCaseContainingOrTalukIgnoreCaseContainingOrPincodeIgnoreCaseContaining(query, query, query, query, query);
        return mccs.stream().map(Mcc::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public MccDTOResponse updateImage(String mccId, MultipartFile multipartFile) throws Throwable {

        Mcc savedMcc = check4MccExistence(mccRepository, mccId);
        if (multipartFile == null || multipartFile.isEmpty())
            return savedMcc.getResponseDTO();

        // delete old image
        if (savedMcc.getImage() != null && savedMcc.getImage().length() > 0) {
            String[] split = savedMcc.getImage().split("/");
            String fileName = split[split.length - 1];
            Boolean deleteResult = fileStorageService.deleteFile(FileStorageService.ImageType.MCC, fileName);
            log.info("Old mcc image deleted: {} for mcc: {}", deleteResult, savedMcc.getId());
        }
        // save nrw mcc image
        String uploadedFileLocation = fileStorageService.saveFile(FileStorageService.ImageType.MCC, multipartFile, savedMcc.getId());
        log.info("Mcc {} mcc image updated.", savedMcc.getId());
        savedMcc.setImage(uploadedFileLocation);
        return mccRepository.save(savedMcc).getResponseDTO();
    }
}
