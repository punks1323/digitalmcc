package com.cluster.digital.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Component
@Slf4j
public class RESTLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            log.info("LOGGED OUT : " + authentication.getName());
        }
        //perform other required operation
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().flush();
    }
} 