package com.cluster.digital.api.admin;

import com.cluster.digital.model.request.FieldExecutiveDTORequest;
import com.cluster.digital.model.response.FieldExecutiveDTOResponse;
import com.cluster.digital.service.FieldExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@RestController
@RequestMapping("/admin/field-executive")
public class FieldExecutiveController {

    @Autowired
    FieldExecutiveService fieldExecutiveService;

    @GetMapping
    public List<FieldExecutiveDTOResponse> getAllFieldExecutives(@RequestParam(value = "search", required = false) String query) {
        return fieldExecutiveService.getAllFieldExecutives(query);
    }

    @GetMapping("/{fieldExecutiveId}")
    public List<FieldExecutiveDTOResponse> getFieldExecutive(@PathVariable("fieldExecutiveId") String fieldExecutiveId) throws Throwable {
        return fieldExecutiveService.getFieldExecutive(fieldExecutiveId);
    }

    @PostMapping
    public List<FieldExecutiveDTOResponse> createFieldExecutive(@Valid @RequestBody FieldExecutiveDTORequest fieldExecutiveDTORequest) throws Throwable {
        return fieldExecutiveService.createNewFieldExecutive(fieldExecutiveDTORequest);
    }
}
