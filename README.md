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