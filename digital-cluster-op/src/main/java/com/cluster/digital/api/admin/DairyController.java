package com.cluster.digital.api.admin;

import com.cluster.digital.database.entity.Dairy;
import com.cluster.digital.model.request.DairyDTO;
import com.cluster.digital.service.DairyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

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
    public Collection<Dairy> getAllDairies(@RequestParam(value = "search", required = false) String query) {
        return dairyService.getAllDairies(query);
    }

    @PostMapping
    public Dairy createNewDairy(@Valid @RequestBody DairyDTO dairyDTO) throws Throwable {
        return dairyService.createNewDairy(dairyDTO);
    }
}
