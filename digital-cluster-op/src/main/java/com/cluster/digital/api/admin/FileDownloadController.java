package com.cluster.digital.api.admin;

import com.cluster.digital.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-08
 */
@RestController
@RequestMapping("/admin/download")
public class FileDownloadController {

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/image/{imageType}/{fileName}")
    public ResponseEntity<Resource> downloadImageFor(@PathVariable("imageType") FileStorageService.ImageType imageType, @PathVariable("fileName") String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileStorageService.readFile(request, imageType, fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
