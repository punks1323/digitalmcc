package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.ClusterDTOResponse;
import com.cluster.digital.utils.DConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Table
@Data
public class Cluster extends Auditable<String> {

    private static final String ORG_ID_GENERATOR = "cluster-id-generator";
    private static final String ORG_ID_GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String ORG_ID_PREFIX = "CL-";
    private static final String ORG_ID_LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = ORG_ID_GENERATOR)
    @GenericGenerator(name = ORG_ID_GENERATOR,
            parameters = {@Parameter(name = DConstants.ENTITY_ID.PREFIX, value = ORG_ID_PREFIX), @Parameter(name = DConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = ORG_ID_LEAD_COUNT)},
            strategy = ORG_ID_GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;
    private String district;
    private String state;

    @OneToMany(mappedBy = "cluster")
    private Collection<Dairy> dairies = new ArrayList<>();

    public ClusterDTOResponse getResponseDTO() {
        ClusterDTOResponse clusterDTOResponse = new ClusterDTOResponse();
        clusterDTOResponse.setId(this.getId());
        clusterDTOResponse.setName(this.getName());
        clusterDTOResponse.setDistrict(this.getDistrict());
        clusterDTOResponse.setState(this.getState());
        clusterDTOResponse.setDairies(dairies.stream().map(Dairy::getId).collect(Collectors.toList()));
        return clusterDTOResponse;
    }
}
