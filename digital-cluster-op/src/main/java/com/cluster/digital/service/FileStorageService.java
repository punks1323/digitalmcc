package com.cluster.digital.service;

import com.cluster.digital.exception.ImageDoesNotExistException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
public interface FileStorageService {

    enum ImageType {
        MCC, CSP, CSP_KYC, FARMER_KYC
    }

    String saveFile(ImageType imageType, MultipartFile multipartFile, String id);

    Resource readFile(HttpServletRequest request, ImageType imageType, String id) throws ImageDoesNotExistException;

    Boolean deleteFile(ImageType imageType, String id) throws ImageDoesNotExistException;
}
