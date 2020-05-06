package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.CspDTORequest;
import com.cluster.digital.model.response.CspDTOResponse;
import com.cluster.digital.service.CspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-06
 */

@RestController
@RequestMapping("/admin/csp")
public class CspController {

    @Autowired
    CspService cspService;

    @PostMapping
    public CspDTOResponse createNewCsp(@Valid CspDTORequest cspDTORequest) throws Throwable {
        return cspService.createNewCsp(cspDTORequest);
    }

    @GetMapping
    public List<CspDTOResponse> getAllCsp() {
        return cspService.getAllCsp();
    }

    @GetMapping("/{cspId}")
    public CspDTOResponse getCsp(@PathVariable("cspId") String cspId) throws Throwable {
        return cspService.getCsp(cspId);
    }
}
