package com.cluster.digital.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class DairyDTORequest {
    @NotBlank(message = "Dairy name must not be empty")
    private String name;
    private String clusterId;
    private String district;
    private String state;
    private List<String> routeIds;
}
