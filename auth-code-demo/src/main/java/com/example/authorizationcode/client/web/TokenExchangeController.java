package com.example.authorizationcode.client.web;

import com.example.authorizationcode.client.config.AuthCodeDemoProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class TokenExchangeController {

  private static final String TOKEN_EXCHANGE_GRANT = "urn:ietf:params:oauth:grant-type:token-exchange";
  private static final String TOKEN_TYPE = "urn:ietf:params:oauth:token-type:access_token";

  private final WebClient webClient;

  private final AuthCodeDemoProperties authCodeDemoProperties;

  public TokenExchangeController(WebClient webClient, AuthCodeDemoProperties authCodeDemoProperties) {
    this.webClient = webClient;
    this.authCodeDemoProperties = authCodeDemoProperties;
  }

  @GetMapping("/exchange")
  public Mono<String> tokenExchangeRequest(@RequestParam("access_token") String accessToken, Model model)
      throws URISyntaxException {
    model.addAttribute("token_endpoint", authCodeDemoProperties.getToken().getEndpoint().toString());
    model.addAttribute("grant_type", TOKEN_EXCHANGE_GRANT);
    String tokenRequestBody =
        "subject_token="
            + accessToken
            + "&subject_token_type=" + TOKEN_TYPE
            + "&grant_type=" + TOKEN_EXCHANGE_GRANT
            + (authCodeDemoProperties.getExchange().getAudience() != null ? "&audience=" + authCodeDemoProperties.getExchange().getAudience() : "")
            + "&client_id=" + authCodeDemoProperties.getExchange().getClientId()
            + "&client_secret=" + authCodeDemoProperties.getExchange().getClientSecret();

    return performTokenExchangeRequest(model, tokenRequestBody);
  }

  private Mono<String> performTokenExchangeRequest(Model model, String tokenExchangeBody)
      throws URISyntaxException {
    return webClient
        .post()
        .uri(authCodeDemoProperties.getToken().getEndpoint().toURI())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(tokenExchangeBody), String.class)
        .retrieve()
        .bodyToMono(TokenExchangeResponse.class)
        .map(
            s -> {
              model.addAttribute("exchange_request", "POST " + authCodeDemoProperties.getToken().getEndpoint().toString() + "</br>" + tokenExchangeBody);
              model.addAttribute("access_token", s.getAccess_token());
              model.addAttribute("scope", s.getScope() != null ? s.getScope() : "");
              model.addAttribute("expires_in", s.getExpires_in());
              model.addAttribute("token_type", s.getToken_type());
              model.addAttribute("issued_token_type", s.getIssued_token_type());
              return "token_exchange";
            })
        .onErrorResume(
            p -> p instanceof WebClientResponseException,
            t -> {
              model.addAttribute("error", "Error getting token");
              model.addAttribute(
                  "error_description", ((WebClientResponseException) t).getResponseBodyAsString());
              return Mono.just("error");
            });
  }
}
