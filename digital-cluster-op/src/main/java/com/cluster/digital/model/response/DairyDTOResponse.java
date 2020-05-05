package com.cluster.digital.model.response;

import lombok.Data;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class DairyDTOResponse {
    private String id;
    private String name;
    private String clusterId;
    private String district;
    private String state;
    private List<String> routes;
}
