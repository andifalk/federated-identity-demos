# Federated Identity Demos

This repository contains various demonstrations for Federated Identities like OAuth 2 and OpenID Connect.

## Prerequisites

* Java 21 or later
* [Bruno API Client](https://www.usebruno.com)
* [JSON Web Token Toolkit v2 (JWT_Tool)](https://github.com/ticarpi/jwt_tool)
* [Step CLI Tool for building, operating, and automating Public Key Infrastructure (PKI) systems and workflows](https://github.com/smallstep/cli)
* [CyberChef - The Cyber Swiss Army Knife](https://gchq.github.io/CyberChef/)
* [Customized Spring Authorization Server](https://github.com/andifalk/custom-spring-authorization-server)

## Demos

* [Authorization Code Demo](auth-code-demo/README.md): Shows the OAuth 2.1 protocol flow in detail, including
  * Authorization Code Grant
  * PKCE
  * User Info
  * Introspection
  * Token Exchange
  * DPoP
* [Backend for Frontend Pattern](backend-for-frontend/README.md)
* [RFC 9068 Compliant Resource Server](rfc-9068-resource-server/README.md)
* [Token Exchange Demo](token-exchange/README.md)
* [Decoding & Validating JWTs](decode-validate-jwt/README.md)
* [Insecure API (Hacking JWT)](insecure-api/README.md)

## Bruno API Collection

To test the provided APIs with OAuth2/OIDC and JWTs you may use the provided [Bruno]() collection located in folder `bruno/federated-identity-demos`.

## Comparison of OpenID Connect Providers and OAuth 2.x RFC Support

| Provider                        | OAuth 2.1 (Draft) | PKCE (RFC 7636) | RFC 9126 (OAuth Security BCP) | RFC 8705 (Mutual TLS)          | RFC 9449 (DPoP) | RFC 8725 (JWT BCP) | RFC 9068 (JWT Profile for Access Tokens) | RFC 8693 (Token Exchange)   |
|---------------------------------|-------------------|-----------------|-------------------------------|--------------------------------|-----------------|--------------------|------------------------------------------|-----------------------------|
| **Auth0**                       | ✅                 | ✅               | 🔶 Partial                     | 🔶 Enterprise Add-on           | ✅ (Beta)        | ✅                  | 🔶 (Experimental)                        | 🔶 (Beta via Rules/Hooks)   |
| **MS Entra ID**                 | ✅                 | ✅               | ✅                             | 🔶 Confidential Client + Certs | ❌               | ✅                  | ❌                                        | 🔶 (Entra ID - Limited)     |
| **Google Identity**             | ✅                 | ✅               | 🔶 Partial                    | ❌                              | ❌               | ✅                  | ❌                                        | ❌                           |
| **Okta**                        | ✅                 | ✅               | ✅                             | ✅ (with Workflows)             | ✅ (Preview)     | ✅                  | 🔶 (Preview for APIs)                    | 🔶 (Some API Gateways only) |
| **Keycloak**                    | ✅                 | ✅               | ✅                             | ✅                              | ✅ (v24+)        | ✅                  | ✅ (via config)                           | ✅ (v24+)  |
| **ForgeRock**                   | ✅                 | ✅               | ✅                             | ✅                              | ✅               | ✅                  | ✅                                        | ✅                           |
| **Ping Identity**               | ✅                 | ✅               | ✅                             | ✅                              | ✅               | ✅                  | ✅                                        | ✅                           |
| **Curity**                      | ✅                 | ✅               | ✅                             | ✅                              | ✅               | ✅                  | ✅                                        | ✅                           |
| **AWS Cognito**                 | ✅                 | ✅               | 🔶 Partial                    | ❌                              | ❌               | ✅                  | ❌                                        | ❌                           |
| **Spring Authorization Server** | ✅                 | ✅               | ✅                             | ✅                              | ✅               | ✅                  | ✅                                        | ✅                           |

### Legend:
- ✅ = Fully supported
- 🔶 = Partially supported / Preview / Requires configuration or specific SKU
- ❌ = Not supported or not documented