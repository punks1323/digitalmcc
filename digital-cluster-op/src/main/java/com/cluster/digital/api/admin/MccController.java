package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.MccService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping
    public List<MccDTOResponse> createMCC(MccDTORequest mccDTORequest) throws Throwable {
        return mccService.createNewMcc(mccDTORequest);
    }

    @GetMapping
    public List<MccDTOResponse> getAllMcc(@RequestParam(value = "search", required = false) String query) {
        return mccService.getAllMcc(query);
    }

    @GetMapping("/{mccId}")
    public List<MccDTOResponse> getMccDetails(@PathVariable("mccId") String mccId) throws Throwable {
        return mccService.getMcc(mccId);
    }

    @PutMapping("/{mccId}")
    public List<MccDTOResponse> updateImageForMcc(@PathVariable("mccId") String mccId, @RequestParam("mccImage") MultipartFile mccImage) throws Throwable {
        return mccService.updateImage(mccId, mccImage);
    }

}
