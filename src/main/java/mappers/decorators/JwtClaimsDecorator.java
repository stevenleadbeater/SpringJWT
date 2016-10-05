package mappers.decorators;

import dto.UserDTO;
import entities.UsersEntity;
import mappers.JwtClaimsMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by steven on 04/09/16.
 */
@Component
public abstract class JwtClaimsDecorator implements JwtClaimsMapper {

    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    @Inject
    @Qualifier("delegate")
    private JwtClaimsMapper delegate;

    @Override
    public JwtClaims toJwtClaims(UserDTO user) throws IOException {
        // Prepare claims
        ObjectMapper mapper = new ObjectMapper();

        JwtClaims claims = delegate.toJwtClaims(user);
        claims.setIssuer("VehicleTracker");
        claims.setAudience("Users");
        claims.setExpirationTimeMinutesInTheFuture(2);
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject(user.getUserName());
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        try {
            claims.setClaim("user", mapper.writeValueAsString(user)); // additional claims/attributes about the subject can be added
        } catch (IOException e) {
            e.printStackTrace();
        }
        return claims;
    }

    @Override
    public UserDTO toUser(JwtClaims jwtClaims) throws MalformedClaimException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO user = mapper.readValue(jwtClaims.getClaimValue("user").toString(), UserDTO.class);
        return user;
    }
}
