package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Data
public class Mcc extends Auditable<String> {

    private static final String ID_GENERATOR = "mcc-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "MCC-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR,
            parameters = {@Parameter(name = MConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = MConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;
    private String address;
    private String village;
    private String taluk;
    private String pincode;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    private Route route;
    private String image;

    public MccDTOResponse getResponseDTO() {
        MccDTOResponse mccDTOResponse = new MccDTOResponse();
        mccDTOResponse.setId(this.getId());
        mccDTOResponse.setName(this.getName());
        mccDTOResponse.setAddress(this.getAddress());
        mccDTOResponse.setPincode(this.getPincode());
        mccDTOResponse.setVillage(this.getVillage());
        mccDTOResponse.setTaluk(this.getTaluk());
        mccDTOResponse.setLatitude(this.getLatitude());
        mccDTOResponse.setLongitude(this.getLongitude());
        mccDTOResponse.setImage(this.getImage());
        return mccDTOResponse;
    }
}
