package com.example.authorizationcode.client.dpop;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.dpop.DPoPProofFactory;
import com.nimbusds.oauth2.sdk.dpop.DefaultDPoPProofFactory;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class DpopProofTokenCreator {

    private DPoPProofFactory proofFactory;

    @PostConstruct
    public void init() throws JOSEException {
        ECKey jwk = new ECKeyGenerator(Curve.P_256)
                .keyID("1")
                .generate();

        this.proofFactory = new DefaultDPoPProofFactory(
                jwk,
                JWSAlgorithm.ES256);
    }

    public String createTokenForTokenRequest(URI request) throws JOSEException {
        SignedJWT proof = proofFactory.createDPoPJWT(
                HttpMethod.POST.name(),
                request);
        return proof.serialize();
    }

    public String createTokenForCall(URI request, String accessToken) throws JOSEException {
        AccessToken accessTokenObj = new BearerAccessToken(accessToken);
        SignedJWT proof = proofFactory.createDPoPJWT(
                HttpMethod.GET.name(),
                request,
                accessTokenObj);
        return proof.serialize();
    }

}
