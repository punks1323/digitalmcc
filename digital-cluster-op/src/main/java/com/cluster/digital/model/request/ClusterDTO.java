package com.cluster.digital.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class ClusterDTO {
    @NotBlank(message = "Cluster name must not be empty")
    private String name;
    private String district;
    private String state;
}
