package org.example.rfc9068.resourceserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class DemoApi {

    private static final Logger LOG = LoggerFactory.getLogger(DemoApi.class);

    @GetMapping
    public String helloWorld(
            @AuthenticationPrincipal(errorOnInvalidType = true) Jwt jwt,
            @RequestHeader HttpHeaders headers
    ) {
        LOG.info("Principal {}", jwt);
        LOG.info("Headers {}", headers);
        if (headers.containsKey("DPoP")) {
            LOG.info("DPoP JWT {}", headers.get("DPoP"));
        }

        return "Hello "
                + jwt.getClaimAsString("family_name")
                + ", "
                + jwt.getClaimAsString("given_name")
                + " from Messages API";
    }
}
