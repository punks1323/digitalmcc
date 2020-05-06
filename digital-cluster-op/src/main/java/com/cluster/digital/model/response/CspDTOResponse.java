package com.cluster.digital.model.response;

import com.cluster.digital.constants.KycConstants;
import lombok.Data;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class CspDTOResponse {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String mccId;
    private KycConstants.KycType kycType;
    private String kycNumber;
    private String kycImage;
    private String cspImage;
}
