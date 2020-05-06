package com.cluster.digital.model.request;

import com.cluster.digital.constants.KycConstants;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class CspDTORequest {
    @NotBlank(message = "First name must not be empty")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotBlank(message = "Mobile number is a mandatory field.")
    private String mobileNumber;
    @NotBlank(message = "Mcc id can not be empty.")
    private String mccId;
    private KycConstants.KycType kycType;
    @NotBlank(message = "Kyc number can not be empty.")
    private String kycNumber;
    private MultipartFile kycImage;
    private MultipartFile cspImage;
}
