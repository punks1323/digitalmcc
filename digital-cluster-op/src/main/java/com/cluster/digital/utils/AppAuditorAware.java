package com.cluster.digital.utils;

import com.cluster.digital.utils.security.SecurityUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AppAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtil.getUsername());
    }
}