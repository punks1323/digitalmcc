package com.cluster.digital.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class DairyDTO {
    @NotBlank(message = "Dairy name must not be empty")
    private String name;
    @NotBlank(message = "Dairy cluster name can not be empty")
    private String clusterId;
    private String district;
    private String state;
    private Collection<String> routeIds = new ArrayList<>();
}
