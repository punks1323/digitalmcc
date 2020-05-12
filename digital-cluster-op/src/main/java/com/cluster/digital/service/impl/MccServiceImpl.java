package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.database.repo.CspRepository;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.database.repo.RouteRepository;
import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.FileStorageService;
import com.cluster.digital.service.MccService;
import com.cluster.digital.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
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
    private final CspRepository cspRepository;
    private final FileStorageService fileStorageService;

    public MccServiceImpl(MccRepository mccRepository, RouteRepository routeRepository, CspRepository cspRepository, FileStorageService fileStorageService) {
        this.mccRepository = mccRepository;
        this.routeRepository = routeRepository;
        this.cspRepository = cspRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<MccDTOResponse> createNewMcc(MccDTORequest mccDTORequest) throws Throwable {
        Route route = check4RouteExistence(routeRepository, mccDTORequest.getRouteId());

        Mcc mcc = BeanUtils.copyBeanProperties(mccDTORequest, new Mcc());
        mcc.setRoute(route);

        Mcc savedMcc = mccRepository.save(mcc);
        if (mccDTORequest.getMccImage() != null && !mccDTORequest.getMccImage().isEmpty()) {
            String fileDownloadUri = fileStorageService.saveFile(FileStorageService.ImageType.MCC, mccDTORequest.getMccImage(), savedMcc.getId());
            log.info("Mcc {} new mcc image received.", savedMcc.getId());
            mcc.setImage(fileDownloadUri);
            savedMcc = mccRepository.save(mcc);
        }

        return attachCspIdAndReturn(Collections.singletonList(savedMcc));
    }

    @Override
    public List<MccDTOResponse> getMcc(String mccId) throws Throwable {
        return attachCspIdAndReturn(Collections.singletonList(check4MccExistence(mccRepository, mccId)));
    }

    @Override
    public List<MccDTOResponse> getAllMcc(String query) {
        List<Mcc> mccs = query == null ? mccRepository.findAll() : mccRepository.findByNameIgnoreCaseContainingOrAddressIgnoreCaseContainingOrVillageIgnoreCaseContainingOrTalukIgnoreCaseContainingOrPincodeIgnoreCaseContaining(query, query, query, query, query);
        return attachCspIdAndReturn(mccs);
    }

    @Override
    public List<MccDTOResponse> updateImage(String mccId, MultipartFile multipartFile) throws Throwable {

        Mcc savedMcc = check4MccExistence(mccRepository, mccId);
        if (multipartFile == null || multipartFile.isEmpty())
            return attachCspIdAndReturn(Collections.singletonList(savedMcc));

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

        return attachCspIdAndReturn(Collections.singletonList(mccRepository.save(savedMcc)));
    }

    public List<MccDTOResponse> attachCspIdAndReturn(List<Mcc> mccList) {
        return mccList.stream()
                .map(m -> {
                    MccDTOResponse response = m.getResponseDTO();
                    response.setCspId(cspRepository.findCspIdByMccId(m.getId()));
                    return response;
                }).collect(Collectors.toList());
    }
}
