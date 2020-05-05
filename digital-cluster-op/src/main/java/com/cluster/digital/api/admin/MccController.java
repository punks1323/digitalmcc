package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.service.MccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
@RestController
@RequestMapping("/admin/mcc")
public class MccController {

    @Autowired
    MccService mccService;

    @PostMapping
    public MccDTOResponse createMCC(MccDTORequest mccDTORequest) throws Throwable {
        return mccService.createNewMcc(mccDTORequest);
    }
}
