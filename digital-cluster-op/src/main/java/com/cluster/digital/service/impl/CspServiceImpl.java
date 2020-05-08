package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Csp;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.model.request.CspDTORequest;
import com.cluster.digital.model.response.CspDTOResponse;
import com.cluster.digital.service.CspService;
import com.cluster.digital.database.repo.CspRepository;
import com.cluster.digital.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
@Slf4j
public class CspServiceImpl implements CspService {

    private final CspRepository cspRepository;
    private final MccRepository mccRepository;
    private final FileStorageService fileStorageService;

    public CspServiceImpl(CspRepository cspRepository, MccRepository mccRepository, FileStorageService fileStorageService) {
        this.cspRepository = cspRepository;
        this.mccRepository = mccRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public CspDTOResponse createNewCsp(CspDTORequest request) throws Throwable {
        Mcc mcc = check4MccExistence(mccRepository, request.getMccId());

        Csp csp = new Csp();
        csp.setFirstName(request.getFirstName());
        csp.setMiddleName(request.getMiddleName());
        csp.setLastName(request.getLastName());
        csp.setKycType(request.getKycType());
        csp.setKycNumber(request.getKycNumber());
        csp.setMobileNumber(request.getMobileNumber());
        csp.setMcc(mcc);

        Csp saveCsp = cspRepository.save(csp);

        // save csp image
        String imageUri = fileStorageService.saveFile(FileStorageService.ImageType.CSP, request.getCspImage(), saveCsp.getId());
        saveCsp.setCspImage(imageUri);

        // save kyc image
        String kycImageUri = fileStorageService.saveFile(FileStorageService.ImageType.CSP_KYC, request.getKycImage(), saveCsp.getId());
        saveCsp.setKycImage(kycImageUri);

        return cspRepository.save(saveCsp).getResponseDTO();
    }

    @Override
    public List<CspDTOResponse> getAllCsp() {
        return cspRepository.findAll().stream().map(Csp::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CspDTOResponse getCsp(String cspId) throws Throwable {
        return check4CspExistence(cspRepository, cspId).getResponseDTO();
    }

    @Override
    public CspDTOResponse updateImagesForCsp(String cspId, MultipartFile kycImage, MultipartFile cspImage) throws Throwable {

        Csp savedCsp = check4CspExistence(cspRepository, cspId);

        if (kycImage != null && !kycImage.isEmpty()) {
            // delete old csp kyc image
            if (savedCsp.getKycImage() != null && savedCsp.getKycImage().length() > 0) {
                String[] split = savedCsp.getKycImage().split("/");
                String fileName = split[split.length - 1];
                Boolean deleteResult = fileStorageService.deleteFile(FileStorageService.ImageType.CSP_KYC, fileName);
                log.info("Old csp kyc image deleted: " + deleteResult);
            }

            // save new csp kyc image
            String kycImageLocation = fileStorageService.saveFile(FileStorageService.ImageType.CSP_KYC, kycImage, savedCsp.getId());
            savedCsp.setKycImage(kycImageLocation);
        }
        if (cspImage != null && !cspImage.isEmpty()) {
            // delete old csp image
            if (savedCsp.getCspImage() != null && savedCsp.getCspImage().length() > 0) {
                String[] split = savedCsp.getCspImage().split("/");
                String fileName = split[split.length - 1];
                Boolean deleteResult = fileStorageService.deleteFile(FileStorageService.ImageType.CSP, fileName);
                log.info("Old csp image deleted: " + deleteResult);
            }

            // save new csp image
            String cspImageLocation = fileStorageService.saveFile(FileStorageService.ImageType.CSP, cspImage, savedCsp.getId());
            savedCsp.setCspImage(cspImageLocation);
        }

        return cspRepository.save(savedCsp).getResponseDTO();
    }
}
