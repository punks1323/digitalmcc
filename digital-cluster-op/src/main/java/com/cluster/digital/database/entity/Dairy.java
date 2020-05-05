package com.cluster.digital.database.entity;

import com.cluster.digital.utils.MConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


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
            parameters = {@Parameter(name = MConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = MConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Cluster cluster;

    @OneToMany(mappedBy = "dairy")
    private Collection<Route> routes = new ArrayList<>();

    private String district;

    private String state;

}
