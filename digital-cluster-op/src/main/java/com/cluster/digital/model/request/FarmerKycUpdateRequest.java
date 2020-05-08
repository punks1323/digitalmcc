package com.cluster.digital.model.request;

import com.cluster.digital.constants.KycConstants;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-08
 */
@Data
public class FarmerKycUpdateRequest {
    private KycConstants.KycType kycType;

    @NotBlank(message = "Kyc number must not be empty")
    private String kycNumber;

    private MultipartFile kycImage;
}
