package com.cluster.digital.service;

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
        MCC_IMAGE, CSP_IMAGE, CSP_KYC_IMAGE
    }

    String saveFile(ImageType imageType, MultipartFile multipartFile, String mccId);

    Resource readFile(HttpServletRequest request, ImageType imageType, String mccId);
}
