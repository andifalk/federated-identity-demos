package com.example.authorizationcode.client.web;

import com.example.authorizationcode.client.config.AuthCodeDemoProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CodeCallbackController {

  private final ProofKeyForCodeExchange proofKeyForCodeExchange;
  private final AuthCodeDemoProperties authCodeDemoProperties;

  public CodeCallbackController(ProofKeyForCodeExchange proofKeyForCodeExchange, AuthCodeDemoProperties authCodeDemoProperties) {
    this.proofKeyForCodeExchange = proofKeyForCodeExchange;
    this.authCodeDemoProperties = authCodeDemoProperties;
  }

  @GetMapping(path = "/callback")
  public String oauthCallBack(
      @RequestParam(name = "code", required = false) String code,
      @RequestParam(name = "state", required = false) String state,
      @RequestParam(name = "error", required = false) String error,
      @RequestParam(name = "error_description", required = false) String error_description,
      Model model) {

    if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state)) {
      model.addAttribute("token_endpoint", authCodeDemoProperties.getToken().getEndpoint().toString());
      model.addAttribute("grant_type", "authorization_code");
      model.addAttribute("code", code);
      model.addAttribute("state", state);
      model.addAttribute("redirect_uri", authCodeDemoProperties.getRedirectUri().toString());
      model.addAttribute("pkce", authCodeDemoProperties.isPkce() ? "On" : "Off");
      model.addAttribute("client_id", authCodeDemoProperties.getClientId());
      model.addAttribute("client_secret", authCodeDemoProperties.getToken().getClientSecret());
      model.addAttribute("tokenRequest", new TokenRequest());
      model.addAttribute("gen_token_request", targetTokenRequest(code, state));
      model.addAttribute("code_verifier", proofKeyForCodeExchange.getCodeVerifier() != null ?
              proofKeyForCodeExchange.getCodeVerifier() : "n/a");
      return "authcode";
    } else {
      model.addAttribute("error", error);
      model.addAttribute("error_description", error_description);
      return "error";
    }
  }

  private String targetTokenRequest(String code, String state) {
    return  "POST " + authCodeDemoProperties.getToken().getEndpoint().toString() + "</br>"
                    + "Content-Type: application/x-www-form-urlencoded</br>"
                    + "Accept: application/json</br>"
                    + "</br>"
            + "<b>grant_type</b>=authorization_code&<b>code</b>="
                    + code
                    + "&<b>state</b>="
                    + state
                    + "&<b>redirect_uri</b>="
                    + authCodeDemoProperties.getRedirectUri().toString()
                    + "&<b>client_id</b>="
                    + authCodeDemoProperties.getClientId()
                    + (authCodeDemoProperties.isPkce() ?
                      "&<b>code_verifier</b>="
                              + proofKeyForCodeExchange.getCodeVerifier() : "&<b>client_secret</b>="
                              + authCodeDemoProperties.getToken().getClientSecret());
  }
}
