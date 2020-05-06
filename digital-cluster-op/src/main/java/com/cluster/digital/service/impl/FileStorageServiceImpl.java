package com.cluster.digital.service.impl;

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

    @Override
    public String saveFile(ImageType imageType, MultipartFile file, String primaryKey) {
        try {
            if (file.isEmpty() || file.getOriginalFilename() == null || !file.getOriginalFilename().contains("."))
                return null;

            File uploadDir = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + imageType.name());
            String fileName = primaryKey + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            String downloadUri = File.separator + "image" + File.separator + imageType.name() + File.separator + fileName;

            File uploadFileLocation = new File(uploadDir, fileName);

            if (!uploadDir.exists()) {
                System.out.println("Dir created: " + uploadDir.mkdirs());
            }

            Path path = Paths.get(uploadFileLocation.getCanonicalPath());
            Files.write(path, file.getBytes());

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(downloadUri)
                    .path(uploadFileLocation.getCanonicalPath())
                    .toUriString();
        } catch (Exception e) {
            log.warn("Failed to save uploaded file", e);
            return null;
        }
    }

    @Override
    public Resource readFile(HttpServletRequest request, ImageType imageType, String fileName) {

        try {
            File uploadDir = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + imageType.name());
            Path fileStorageLocation = Paths.get(uploadDir.getCanonicalPath());
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            return new UrlResource(filePath.toUri());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
