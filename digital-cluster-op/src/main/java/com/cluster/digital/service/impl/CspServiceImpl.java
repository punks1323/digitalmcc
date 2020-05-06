package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Csp;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.repo.MccRepository;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.CspDTORequest;
import com.cluster.digital.model.response.CspDTOResponse;
import com.cluster.digital.service.CspService;
import com.cluster.digital.database.repo.CspRepository;
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
public class CspServiceImpl implements CspService {

    private CspRepository cspRepository;
    private MccRepository mccRepository;
    private FileStorageService fileStorageService;

    public CspServiceImpl(CspRepository cspRepository, MccRepository mccRepository, FileStorageService fileStorageService) {
        this.cspRepository = cspRepository;
        this.mccRepository = mccRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public CspDTOResponse createNewCsp(CspDTORequest request) throws Throwable {
        Optional<Mcc> mccOptional = mccRepository.findById(request.getMccId());
        mccOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No mcc found with id: " + request.getMccId()));

        Csp csp = new Csp();
        csp.setFirstName(request.getFirstName());
        csp.setMiddleName(request.getMiddleName());
        csp.setLastName(request.getLastName());
        csp.setKycType(request.getKycType());
        csp.setKycNumber(request.getKycNumber());
        csp.setMobileNumber(request.getMobileNumber());
        csp.setMcc(mccOptional.get());

        Csp saveCsp = cspRepository.save(csp);

        // save csp image
        String imageUri = fileStorageService.saveFile(FileStorageService.ImageType.CSP_IMAGE, request.getCspImage(), saveCsp.getId());
        saveCsp.setCspImage(imageUri);

        // save kyc image
        String kycImageUri = fileStorageService.saveFile(FileStorageService.ImageType.CSP_KYC_IMAGE, request.getKycImage(), saveCsp.getId());
        saveCsp.setKycImage(kycImageUri);

        return cspRepository.save(saveCsp).getResponseDTO();
    }

    @Override
    public List<CspDTOResponse> getAllCsp() {
        return cspRepository.findAll().stream().map(Csp::getResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CspDTOResponse getCsp(String cspId) throws Throwable {
        Optional<Csp> cspOptional = cspRepository.findById(cspId);
        cspOptional.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No csp dound with id: " + cspId));
        return cspOptional.get().getResponseDTO();
    }
}
