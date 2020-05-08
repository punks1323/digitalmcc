package com.cluster.digital.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class FieldExecutiveDTORequest {
    @NotBlank(message = "Field executive name can not be empty")
    private String name;
    @NotBlank(message = "Mobile number must not be empty")
    private String mobile;
    private String routeId;
}
