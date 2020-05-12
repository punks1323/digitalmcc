package com.cluster.digital.database.entity;

import com.cluster.digital.model.response.FieldExecutiveDTOResponse;
import com.cluster.digital.utils.DConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldExecutive extends Auditable<String> {

    private static final String GENERATOR = "fe-id-generator";
    private static final String GENERATOR_PACKAGE = "com.cluster.digital.database.entity.id_generators.ApplicationIdGenerator";
    private static final String PREFIX = "FE-";
    private static final String LEAD_COUNT = "3";
    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR,
            parameters = {@Parameter(name = DConstants.ENTITY_ID.PREFIX, value = PREFIX), @Parameter(name = DConstants.ENTITY_ID.LEAD_ZERO_COUNT, value = LEAD_COUNT)},
            strategy = GENERATOR_PACKAGE)
    private String id;

    private String name;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    @Column(unique = true)
    private String mobile;

    public FieldExecutiveDTOResponse getResponseDTO() {
        FieldExecutiveDTOResponse response = new FieldExecutiveDTOResponse();
        response.setId(this.getId());
        response.setName(this.getName());
        response.setMobile(this.getMobile());
        return response;
    }
}
