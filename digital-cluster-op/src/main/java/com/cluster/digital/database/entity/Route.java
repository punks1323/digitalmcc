package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.RouteDTOResponse;
import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Table
@Data
public class Route extends Auditable<String> {

    private static final String ID_GENERATOR = "route-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "RO-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR,
            parameters = {@Parameter(name = MConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = MConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;
    private String district;
    private String state;

    @ManyToOne
    private Dairy dairy;

    @OneToMany(mappedBy = "route")
    private List<Mcc> mccs;

    public RouteDTOResponse getResponseDTO() {
        RouteDTOResponse routeDTOResponse = new RouteDTOResponse();
        routeDTOResponse.setId(this.getId());
        routeDTOResponse.setName(this.getName());
        routeDTOResponse.setDistrict(this.getDistrict());
        routeDTOResponse.setState(this.getState());
        routeDTOResponse.setDairyId(this.getDairy().getId());
        routeDTOResponse.setMccList(this.getMccs().stream().map(Mcc::getId).collect(Collectors.toList()));
        return routeDTOResponse;
    }
}
