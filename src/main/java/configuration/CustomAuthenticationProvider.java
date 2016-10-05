package configuration;

/**
 * Created by steven on 31/08/16.
 */

import dto.UserDTO;
import entities.UsersEntity;
import mappers.JwtClaimsMapper;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableAuthorizationServer
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Inject
    JwtClaimsMapper jwtClaimsMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Get the RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                .getKeyPair("test");
        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setMaxFutureValidityInMinutes(300) // but the  expiration time can't be too crazy
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("VehicleTracker") // whom the JWT needs to have been issued by
                .setExpectedAudience("Users") // to whom the JWT is intended for
                .setVerificationKey(keyPair.getPublic()) // verify the signature with the public key
                .setJweAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256))
                .build(); // create the JwtConsumer instance
        JwtClaims jwtClaims;
        try
        {
            //  Validate the JWT and process it to the Claims
            jwtClaims = jwtConsumer.processToClaims((String)authentication.getPrincipal());
            System.out.println("JWT validation succeeded! " + jwtClaims);
        }
        catch (InvalidJwtException e)
        {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            System.out.println("Invalid JWT! " + e);
            return null;
        }

        UserDTO user;
        try {
            user = jwtClaimsMapper.toUser(jwtClaims);
        } catch (MalformedClaimException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        List<GrantedAuthority> grantedAuthentications = new ArrayList<>();
        grantedAuthentications.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedAuthentications.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), grantedAuthentications);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
