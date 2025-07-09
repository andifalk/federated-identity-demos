# RCF 9068 compliant Resource Server

This resource server is implemented using [Spring Security]() and uses the recently 
provided validator `org.springframework.security.oauth2.jwt.AtJwtBuilder` that validates a JWT 
according to the [RFC 9068](https://datatracker.ietf.org/doc/rfc9068/) specifying a JWT profile for OAuth 2.0 Access Tokens.

Usually the following parts of a JWT are validated:
* Issuer
* Signature
* Expiration date/time

The RFC 9068 validator extends this to
* Validating that the _typ_ header entry is set to `at+jwt`
* Validation of the _aud_ and _client_id_ claims

### Reference Documentation

For further reference, please consider the following sections:

* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/3.5.3/reference/web/spring-security.html#web.security.oauth2.server)
* [JWT Profile for OAuth 2.0 Access Tokens (RFC 9068)](https://datatracker.ietf.org/doc/rfc9068/)


