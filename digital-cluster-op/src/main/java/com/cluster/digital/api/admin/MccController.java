package com.cluster.digital.api.admin;

import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.FileStorageService;
import com.cluster.digital.service.MccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
@Slf4j
@RestController
@RequestMapping("/admin/mcc")
public class MccController {

    @Autowired
    MccService mccService;

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping
    public MccDTOResponse createMCC(MccDTORequest mccDTORequest) throws Throwable {
        return mccService.createNewMcc(mccDTORequest);
    }

    @GetMapping
    public List<MccDTOResponse> getAllMcc(@RequestParam(value = "search", required = false) String query) {
        return mccService.getAllMcc(query);
    }

    @GetMapping("/{mccId}")
    public MccDTOResponse getMccdetails(@PathVariable("mccId") String mccId) throws Throwable {
        return mccService.getMcc(mccId);
    }

    @GetMapping("/image/{imageType}/{fileName}/")
    public ResponseEntity<Resource> downloadImageForMcc(@PathVariable("imageType") FileStorageService.ImageType imageType, @PathVariable("fileName") String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.readFile(request, imageType, fileName);
            if (resource != null && resource.exists()) {
                String contentType = null;
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

                // Fallback to the default content type if type could not be determined
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new NotFoundException("File not found ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
