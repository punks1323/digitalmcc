package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.FarmerDTORequest;
import com.cluster.digital.model.response.FarmerDTOResponse;
import com.cluster.digital.service.FarmerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
@Slf4j
@RestController
@RequestMapping("/admin/farmer")
public class FarmersController {

    @Autowired
    FarmerService farmerService;

    @PostMapping
    public FarmerDTOResponse createNewFarmer(@Valid FarmerDTORequest farmerDTORequest) throws Throwable {
        return farmerService.createNewFarmer(farmerDTORequest);
    }

    @GetMapping
    public List<FarmerDTOResponse> getAllFarmers(@RequestParam(value = "search", required = false) String query) {
        return farmerService.getFarmers(query);
    }

    @GetMapping("/{farmerId}")
    public FarmerDTOResponse getMccdetails(@PathVariable("farmerId") String farmerId) throws Throwable {
        return farmerService.getFarmer(farmerId);
    }
}
