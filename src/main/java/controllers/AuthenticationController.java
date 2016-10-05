package controllers;

import dto.AuthenticationPayload;
import dto.JWTToken;
import dto.UserDTO;
import entities.UsersEntity;
import mappers.JwtClaimsMapper;
import mappers.UserMapper;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.app.UserRepository;

import javax.inject.Inject;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by steven on 01/09/16.
 */
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    JwtClaimsMapper jwtClaimsMapper;

    @RequestMapping(method = RequestMethod.POST)
    public JWTToken authenticate(@RequestBody AuthenticationPayload user){
        try {
            UsersEntity authenticatedUser = userRepository.authenticate(user.getUserName(), user.getPassword());

            // Get the RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
            KeyPair keyPair = new KeyStoreKeyFactory(
                    new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                    .getKeyPair("test");
            RsaJsonWebKey rsaJsonWebKey = new RsaJsonWebKey((RSAPublicKey) keyPair.getPublic());
            rsaJsonWebKey.setPrivateKey(keyPair.getPrivate());
            // Give the JWK a Key ID (kid), which is just the polite thing to do
            rsaJsonWebKey.setKeyId("k1");
            UserDTO userDTO = userMapper.toUserDTO(authenticatedUser);

            JwtClaims claims = jwtClaimsMapper.toJwtClaims(userDTO);

            JsonWebSignature jws = new JsonWebSignature();

            // The payload of the JWS is JSON content of the JWT Claims
            jws.setPayload(claims.toJson());

            // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

            jws.setKey(rsaJsonWebKey.getPrivateKey());
            return new JWTToken(jws.getCompactSerialization());
        } catch (Throwable ex){
            throw new UnauthorizedUserException("username or password incorrect", ex);
        }
    }
}
