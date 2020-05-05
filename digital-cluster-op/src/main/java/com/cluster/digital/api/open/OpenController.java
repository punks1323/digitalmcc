package com.cluster.digital.api.open;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-03
 */
@RestController
@RequestMapping("/public")
public class OpenController {
    @GetMapping("/t")
    public String m1() {
        return "PUBLIC";
    }
}
