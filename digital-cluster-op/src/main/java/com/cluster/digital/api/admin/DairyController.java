package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.DairyDTORequest;
import com.cluster.digital.model.response.DairyDTOResponse;
import com.cluster.digital.service.DairyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/admin/dairy")
public class DairyController {

    @Autowired
    DairyService dairyService;

    @GetMapping
    public List<DairyDTOResponse> getAllDairies(@RequestParam(value = "search", required = false) String query) {
        return dairyService.getAllDairies(query);
    }

    @PostMapping
    public DairyDTOResponse createNewDairy(@Valid @RequestBody DairyDTORequest dairyDTORequest) throws Throwable {
        return dairyService.createNewDairy(dairyDTORequest);
    }

    @PatchMapping("/{dairyId}")
    public DairyDTOResponse addRoutesToDairy(@PathVariable("dairyId") String dairyId, @RequestBody List<String> routeIds) throws Throwable {
        return dairyService.addRoutesToDairy(dairyId, routeIds);
    }
}
