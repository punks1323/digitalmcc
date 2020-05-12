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
public class RouteDTORequest {
    @NotBlank(message = "Route name must not be empty")
    private String name;
    @NotBlank(message = "Dairy id must not be empty")
    private String dairyId;
    private Collection<String> mccIds = new ArrayList<>();
    private String fieldExecutiveId;
}
