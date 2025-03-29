package com.example.authorizationcode.client.web;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TokenExchangeResponse {

  private String access_token;
  private String issued_token_type;
  private int expires_in;
  private String token_type;
  private String scope;

  public String getAccess_token() {
    return access_token;
  }

  public String getDecodedAccessToken() {
    if (getAccess_token() != null) {
      return new String(Base64.getDecoder().decode(getAccess_token()), UTF_8);
    } else {
      return "N/A";
    }
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getIssued_token_type() {
    return issued_token_type;
  }

  public void setIssued_token_type(String issued_token_type) {
    this.issued_token_type = issued_token_type;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(int expires_in) {
    this.expires_in = expires_in;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }
}
