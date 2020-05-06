package com.cluster.digital.database.entity;

import com.cluster.digital.constants.KycConstants;
import com.cluster.digital.model.response.CspDTOResponse;
import com.cluster.digital.model.response.FarmerDTOResponse;
import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Table
@Data
public class Farmer extends Auditable<String> {

    private static final String ID_GENERATOR = "farmer-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "FAR-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR,
            parameters = {@Parameter(name = MConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = MConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    private String firstName;
    private String middleName;
    private String lastName;

    @Column(unique = true)
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobileNumber;

    private String houseNo;
    private String village;
    private String taluk;

    private String pincode;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    private Mcc mcc;

    @Enumerated(EnumType.ORDINAL)
    private KycConstants.KycType kycType;
    private String kycNumber;
    private String kycImage;

    public FarmerDTOResponse getResponseDTO() {
        FarmerDTOResponse response = new FarmerDTOResponse();
        response.setId(this.getId());
        response.setFirstName(this.getFirstName());
        response.setMiddleName(this.getMiddleName());
        response.setLastName(this.getLastName());
        response.setMobileNumber(this.getMobileNumber());
        response.setHouseNo(this.getHouseNo());
        response.setVillage(this.getVillage());
        response.setTaluk(this.getTaluk());
        response.setPincode(this.getPincode());
        response.setKycType(this.getKycType());
        response.setKycNumber(this.getKycNumber());
        response.setKycImage(this.getKycImage());
        response.setLatitude(this.getLatitude());
        response.setLongitude(this.getLongitude());
        response.setMccId(this.getMcc().getId());
        return response;
    }
}
