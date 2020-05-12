package com.cluster.digital.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@Data
public class FieldExecutiveDTOResponse {
    private String id;
    private String name;
    private String mobile;
    private List<String> routes;
}
