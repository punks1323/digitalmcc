package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.DairyDTOResponse;
import com.cluster.digital.utils.DConstants;
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
public class Dairy extends Auditable<String> {

    private static final String GENERATOR = "dairy-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "DA-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR,
            parameters = {@Parameter(name = DConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = DConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Cluster cluster;

    @OneToMany(mappedBy = "dairy")
    private List<Route> routes;

    private String district;

    private String state;

    public DairyDTOResponse getResponseDTO() {
        DairyDTOResponse dairyDTOResponse = new DairyDTOResponse();
        dairyDTOResponse.setId(this.getId());
        dairyDTOResponse.setName(this.getName());
        dairyDTOResponse.setDistrict(this.getDistrict());
        dairyDTOResponse.setState(this.getState());
        dairyDTOResponse.setClusterId(this.getCluster().getId());
        dairyDTOResponse.setRoutes(this.getRoutes().stream().map(Route::getId).collect(Collectors.toList()));
        return dairyDTOResponse;
    }
}
