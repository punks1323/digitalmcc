package com.cluster.digital.service.impl;

import com.cluster.digital.exception.ImageDoesNotExistException;
import com.cluster.digital.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    String uploadRootDir;

    public FileStorageServiceImpl(String uploadRootDir) {
        this.uploadRootDir = uploadRootDir;
    }

    @Override
    public String saveFile(ImageType imageType, MultipartFile file, String id) {
        try {
            if (file.isEmpty() || file.getOriginalFilename() == null || !file.getOriginalFilename().contains("."))
                return null;

            File uploadDir = new File(uploadRootDir, imageType.name());
            String fileName = id + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            String downloadUri = File.separator + "image" + File.separator + imageType.name() + File.separator + fileName;

            File uploadFileLocation = new File(uploadDir, fileName);

            if (!uploadDir.exists()) {
                log.info("Dir created: " + uploadDir.mkdirs());
            }

            Path path = Paths.get(uploadFileLocation.getCanonicalPath());
            Files.write(path, file.getBytes());

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(downloadUri)
                    .toUriString();
        } catch (Exception e) {
            log.warn("Failed to save uploaded file", e);
            return null;
        }
    }

    @Override
    public Resource readFile(HttpServletRequest request, ImageType imageType, String fileName) throws ImageDoesNotExistException {
        Resource resource = null;
        try {
            File uploadDir = new File(uploadRootDir, imageType.name());
            Path fileStorageLocation = Paths.get(uploadDir.getCanonicalPath());
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
        } catch (Exception e) {
            log.warn("Failed to read uploaded file", e);
        }

        if (resource != null && resource.exists()) {
            return resource;
        } else {
            throw new ImageDoesNotExistException("File not found imageType: " + imageType + " fileName: " + fileName);
        }
    }

    @Override
    public Boolean deleteFile(ImageType imageType, String fileName) throws ImageDoesNotExistException {
        try {
            File uploadDir = new File(uploadRootDir, imageType.name());
            Path fileStorageLocation = Paths.get(uploadDir.getCanonicalPath());
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Files.delete(filePath);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.warn("Failed to delete uploaded file", e);
        }
        return Boolean.FALSE;
    }
}
