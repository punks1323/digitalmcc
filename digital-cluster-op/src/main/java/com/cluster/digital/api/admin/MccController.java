package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.MccDTORequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public void m1(@RequestBody MccDTORequest mccDTORequest) {
        System.out.println(mccDTORequest.getFile().isEmpty());
    }
}
