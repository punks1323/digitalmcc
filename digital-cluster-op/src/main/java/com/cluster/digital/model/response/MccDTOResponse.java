package com.cluster.digital.model.response;

import lombok.Data;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class MccDTOResponse {
    private String id;
    private String name;
    private String address;
    private String village;
    private String taluk;
    private String pincode;
    private String image;
    private Double latitude;
    private Double longitude;
    private String routeId;
}
