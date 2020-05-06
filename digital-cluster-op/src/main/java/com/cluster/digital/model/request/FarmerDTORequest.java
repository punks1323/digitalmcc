package com.cluster.digital.model.request;

import com.cluster.digital.constants.KycConstants;
import com.cluster.digital.database.entity.Auditable;
import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.model.response.CspDTOResponse;
import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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
    private String kycNumber;
    private MultipartFile kycImage;
}
