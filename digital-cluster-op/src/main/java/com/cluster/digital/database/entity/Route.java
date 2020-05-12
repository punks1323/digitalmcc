package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.RouteDTOResponse;
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
public class Route extends Auditable<String> {

    private static final String ID_GENERATOR = "route-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "RO-";
    private static final String LEAD_COUNT = "3";

    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR,
            parameters = {@Parameter(name = DConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = DConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Dairy dairy;

    @ManyToOne
    private FieldExecutive fieldExecutive;

    public RouteDTOResponse getResponseDTO() {
        RouteDTOResponse response = new RouteDTOResponse();
        response.setId(this.getId());
        response.setName(this.getName());
        response.setDairyId(this.getDairy() != null ? this.getDairy().getId() : null);
        response.setClusterId((this.getDairy() != null && this.getDairy().getCluster() != null) ? this.getDairy().getCluster().getId() : null);
        response.setFieldExecutiveId(this.getFieldExecutive() != null ? this.getFieldExecutive().getId() : null);
        return response;
    }
}
