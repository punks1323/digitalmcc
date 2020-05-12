package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.utils.DConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class Mcc extends Auditable<String> {

    private static final String ID_GENERATOR = "mcc-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "MCC-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR,
            parameters = {@Parameter(name = DConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = DConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    private String name;
    private String address;
    private String village;
    private String taluk;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String image;

    @ManyToOne
    private Route route;

    public MccDTOResponse getResponseDTO() {
        MccDTOResponse response = new MccDTOResponse();
        response.setId(this.getId());
        response.setName(this.getName());
        response.setAddress(this.getAddress());
        response.setPincode(this.getPincode());
        response.setVillage(this.getVillage());
        response.setTaluk(this.getTaluk());
        response.setLatitude(this.getLatitude());
        response.setLongitude(this.getLongitude());
        response.setImage(this.getImage());

        response.setRouteId(this.getRoute() != null ? this.getRoute().getId() : null);
        response.setDairyId((this.getRoute() != null && this.getRoute().getDairy() != null) ? this.getRoute().getDairy().getId() : null);
        response.setClusterId((this.getRoute() != null && this.getRoute().getDairy() != null && this.getRoute().getDairy().getCluster() != null) ? this.getRoute().getDairy().getCluster().getId() : null);
        return response;
    }
}
