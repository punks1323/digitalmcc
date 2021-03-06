package com.cluster.digital.model.request;

import com.cluster.digital.constants.KycConstants;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Data
public class FarmerDTORequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;

    private String houseNo;
    private String village;
    private String taluk;

    private String pincode;
    private Double latitude;
    private Double longitude;
    private String mccId;

    private KycConstants.KycType kycType;

    @NotBlank(message = "Kyc number must not be empty")
    private String kycNumber;
    private MultipartFile kycImage;
}
