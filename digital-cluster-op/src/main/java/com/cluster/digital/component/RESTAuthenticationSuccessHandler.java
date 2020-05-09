package com.cluster.digital.component;

import com.cluster.digital.model.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
@Component
@Slf4j
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        writeResponseOnResponse(response, authentication);
        SavedRequest savedRequest
                = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParam != null
                && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }

        clearAuthenticationAttributes(request);

    }

    private void writeResponseOnResponse(HttpServletResponse response, Authentication authentication) {
        try {
            LoginResponse loginResponse = new LoginResponse();
            Optional<Authentication> authentication1 = Optional.ofNullable(authentication);
            if (authentication1.isPresent()) {
                Authentication authentication2 = authentication1.get();
                User appUser = (User) authentication2.getPrincipal();
                loginResponse.setUsername(appUser.getUsername());
                log.info("LOGGED IN : " + appUser.getUsername());

                List<String> roles = authentication2.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                loginResponse.setRoles(roles);
            }

            /**
             * Write response
             */

            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter writer = response.getWriter();

            writer.write(objectMapper.writeValueAsString(loginResponse));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}