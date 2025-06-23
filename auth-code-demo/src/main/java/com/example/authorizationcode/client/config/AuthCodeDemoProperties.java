package com.example.authorizationcode.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "democlient")
public class AuthCodeDemoProperties {

    @NotEmpty
    private String clientId;

    @NotNull
    private URL redirectUri;

    @NotNull
    private Boolean pkce;

    @NotNull
    private Boolean dpop;

    @Valid
    private Authorization authorization;

    @Valid
    private Token token;

    @Valid
    private Introspection introspection;

    @Valid
    private UserInfo userInfo;

    @Valid
    private Exchange exchange;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public URL getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(URL redirectUri) {
        this.redirectUri = redirectUri;
    }

    public boolean isPkce() {
        return pkce;
    }

    public void setPkce(boolean pkce) {
        this.pkce = pkce;
    }

    public Boolean isDpop() {
        return dpop;
    }

    public void setDpop(Boolean dpop) {
        this.dpop = dpop;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Introspection getIntrospection() {
        return introspection;
    }

    public void setIntrospection(Introspection introspection) {
        this.introspection = introspection;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public String toString() {
        return "AuthCodeDemoProperties{" +
                "clientId='" + clientId + '\'' +
                ", redirectUri=" + redirectUri +
                ", pkce=" + pkce +
                ", dpop=" + dpop +
                ", authorization=" + authorization +
                ", token=" + token +
                ", introspection=" + introspection +
                ", userinfo=" + userInfo +
                ", exchange=" + exchange +
                '}';
    }

    public static class UserInfo {
        @NotNull
        private URL endpoint;

        public URL getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(URL endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "endpoint=" + endpoint +
                    '}';
        }
    }

    public static class Authorization {
        @NotNull
        private URL endpoint;

        @NotEmpty
        private String responseType;

        @NotEmpty
        private List<String> scope = new ArrayList<>();

        private String prompt;

        public URL getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(URL endpoint) {
            this.endpoint = endpoint;
        }

        public String getResponseType() {
            return responseType;
        }

        public void setResponseType(String responseType) {
            this.responseType = responseType;
        }

        public List<String> getScope() {
            return scope;
        }

        public void setScope(List<String> scope) {
            this.scope = scope;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        @Override
        public String toString() {
            return "Authorization{" +
                    "endpoint='" + endpoint + '\'' +
                    ", responseType='" + responseType + '\'' +
                    ", scope=" + scope +
                    ", prompt='" + prompt + '\'' +
                    '}';
        }
    }

    public static class Token {
        @NotNull
        private URL endpoint;

        private String clientSecret;

        public URL getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(URL endpoint) {
            this.endpoint = endpoint;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "endpoint='" + endpoint + '\'' +
                    ", clientSsecret='" + clientSecret + '\'' +
                    '}';
        }
    }

    public static class Introspection {
        @NotNull
        private URL endpoint;

        public URL getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(URL endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public String toString() {
            return "Introspection{" +
                    "endpoint='" + endpoint + '\'' +
                    '}';
        }
    }

    public static class Exchange {
        @NotNull
        private String clientId;

        @NotNull
        private String clientSecret;

        private String audience;

        @NotEmpty
        private List<String> scope = new ArrayList<>();

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getAudience() {
            return audience;
        }

        public void setAudience(String audience) {
            this.audience = audience;
        }

        public List<String> getScope() {
            return scope;
        }

        public void setScope(List<String> scope) {
            this.scope = scope;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "clientId='" + clientId + '\'' +
                    ", clientSecret='" + clientSecret + '\'' +
                    ", audience='" + audience + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }

}
