package com.example.authorizationcode.client.web;

import com.example.authorizationcode.client.config.AuthCodeDemoProperties;
import com.example.authorizationcode.client.dpop.DpopProofTokenCreator;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;

@Controller
public class CodeCallbackController {

  private final ProofKeyForCodeExchange proofKeyForCodeExchange;
  private final AuthCodeDemoProperties authCodeDemoProperties;
  private final DpopProofTokenCreator dpopProofTokenCreator;

  public CodeCallbackController(ProofKeyForCodeExchange proofKeyForCodeExchange, AuthCodeDemoProperties authCodeDemoProperties, DpopProofTokenCreator dpopProofTokenCreator) {
    this.proofKeyForCodeExchange = proofKeyForCodeExchange;
    this.authCodeDemoProperties = authCodeDemoProperties;
    this.dpopProofTokenCreator = dpopProofTokenCreator;
  }

  @GetMapping(path = "/callback")
  public String oauthCallBack(
      @RequestParam(name = "code", required = false) String code,
      @RequestParam(name = "state", required = false) String state,
      @RequestParam(name = "error", required = false) String error,
      @RequestParam(name = "error_description", required = false) String error_description,
      Model model) throws URISyntaxException, JOSEException {

    if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state)) {
      String dpopJwt = "";
      if (authCodeDemoProperties.isDpop()) {
        dpopJwt = dpopProofTokenCreator.createTokenForTokenRequest(authCodeDemoProperties.getToken().getEndpoint().toURI());
      }
      model.addAttribute("token_endpoint", authCodeDemoProperties.getToken().getEndpoint().toString());
      model.addAttribute("grant_type", "authorization_code");
      model.addAttribute("code", code);
      model.addAttribute("state", state);
      model.addAttribute("redirect_uri", authCodeDemoProperties.getRedirectUri().toString());
      model.addAttribute("pkce", authCodeDemoProperties.isPkce() ? "On" : "Off");
      model.addAttribute("client_id", authCodeDemoProperties.getClientId());
      model.addAttribute("client_secret", authCodeDemoProperties.getToken().getClientSecret());
      model.addAttribute("tokenRequest", new TokenRequest());
      model.addAttribute("gen_token_request", targetTokenRequest(code, state, dpopJwt));
      model.addAttribute("code_verifier", proofKeyForCodeExchange.getCodeVerifier() != null ?
              proofKeyForCodeExchange.getCodeVerifier() : "n/a");
      model.addAttribute("dpop_jwt", dpopJwt);
      return "authcode";
    } else {
      model.addAttribute("error", error);
      model.addAttribute("error_description", error_description);
      return "error";
    }
  }

  private String targetTokenRequest(String code, String state, String dpopJwt) {
    return  "POST " + authCodeDemoProperties.getToken().getEndpoint().toString() + "</br>"
                    + "Content-Type: application/x-www-form-urlencoded</br>"
                    + "Accept: application/json</br>"
                    + ((authCodeDemoProperties.isDpop()) ? "DPoP: " + dpopJwt : "")
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
