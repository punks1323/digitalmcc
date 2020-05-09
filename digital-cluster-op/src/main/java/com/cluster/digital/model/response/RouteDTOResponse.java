package com.cluster.digital.model.response;

import com.cluster.digital.database.entity.Mcc;
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
public class RouteDTOResponse {
    private String id;
    private String name;
    private String district;
    private String state;
    private List<String> mccList;
    private String dairyId;
    private String clusterId;
}
