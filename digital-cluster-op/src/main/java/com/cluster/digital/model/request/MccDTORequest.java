package com.cluster.digital.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class MccDTORequest {
    @NotBlank(message = "Name must not be empty")
    private String name;
    private String address;
    private String village;
    private String taluk;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String routeId;
    private MultipartFile file;
}
