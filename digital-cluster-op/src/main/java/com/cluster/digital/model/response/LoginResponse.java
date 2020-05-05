package com.cluster.digital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by pankaj on 01,July,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
@Setter
@Getter
public class LoginResponse {
    String username;
    Collection<String> roles;
}
