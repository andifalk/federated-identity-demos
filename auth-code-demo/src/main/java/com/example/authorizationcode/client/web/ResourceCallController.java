package com.example.authorizationcode.client.web;

import com.example.authorizationcode.client.config.AuthCodeDemoProperties;
import com.example.authorizationcode.client.dpop.DpopProofTokenCreator;
import com.example.authorizationcode.client.jwt.JsonWebToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
public class ResourceCallController {

  private static final String TOKEN_EXCHANGE_GRANT = "urn:ietf:params:oauth:grant-type:token-exchange";
  private static final String TOKEN_TYPE = "urn:ietf:params:oauth:token-type:access_token";

  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private final AuthCodeDemoProperties authCodeDemoProperties;
  private final DpopProofTokenCreator dpopProofTokenCreator;

  public ResourceCallController(WebClient webClient, ObjectMapper objectMapper, AuthCodeDemoProperties authCodeDemoProperties, DpopProofTokenCreator dpopProofTokenCreator) {
    this.webClient = webClient;
      this.objectMapper = objectMapper;
      this.authCodeDemoProperties = authCodeDemoProperties;
      this.dpopProofTokenCreator = dpopProofTokenCreator;
  }

  @GetMapping("/resource")
  public Mono<String> resourceCallRequest(@RequestParam("access_token") String accessToken, Model model)
          throws URISyntaxException, JOSEException {
    model.addAttribute("resource_endpoint", authCodeDemoProperties.getResource().getEndpoint().toString());
    model.addAttribute("access_token", accessToken);
    JsonWebToken jwt = new JsonWebToken(objectMapper,  accessToken);
    if (jwt.isJwt()) {
        model.addAttribute("jwt_header", jwt.getHeader());
        model.addAttribute("jwt_payload", jwt.getPayload());
        model.addAttribute("jwt_signature", jwt.getSignature());
    } else {
        model.addAttribute("jwt_header", "--");
        model.addAttribute("jwt_payload", "--");
        model.addAttribute("jwt_signature", "--");
    }
    String dpop = null;
    if (authCodeDemoProperties.isDpop()) {
        dpop = dpopProofTokenCreator.createTokenForCall(
                authCodeDemoProperties.getResource().getEndpoint().toURI(),
                accessToken);
    }

    model.addAttribute("dpop", (dpop != null ? dpop : ""));
    JsonWebToken dpopJwt = new JsonWebToken(objectMapper,  dpop);
    if (dpopJwt.isJwt()) {
        model.addAttribute("dpop_header", dpopJwt.getHeader());
        model.addAttribute("dpop_payload", dpopJwt.getPayload());
        model.addAttribute("dpop_signature", dpopJwt.getSignature());
    } else {
        model.addAttribute("dpop_header", "--");
        model.addAttribute("dpop_payload", "--");
        model.addAttribute("dpop_signature", "--");
    }

    return performResourceCallRequest(model, authCodeDemoProperties.getResource().getEndpoint(), accessToken, dpop);
  }

  private Mono<String> performResourceCallRequest(Model model, URL endpoint, String accessToken, String dpop)
      throws URISyntaxException {
    return webClient
        .get()
        .uri(endpoint.toURI())
        .accept(MediaType.APPLICATION_JSON)
        .headers(httpHeaders -> {
            if (dpop != null) {
                httpHeaders.add("Authorization", "DPoP " + accessToken);
                httpHeaders.add("DPoP", dpop);
            } else {
                httpHeaders.setBearerAuth(accessToken);
            }
        })
        .retrieve()
        .bodyToMono(String.class)
        .map(
            s -> {
              model.addAttribute("resource_response", s);
              return "resource_response";
            })
        .onErrorResume(
            p -> p instanceof WebClientResponseException,
            t -> {
              model.addAttribute("error", "Error calling resource");
              model.addAttribute(
                  "error_description", ((WebClientResponseException) t).getResponseBodyAsString());
              return Mono.just("error");
            });
  }
}
