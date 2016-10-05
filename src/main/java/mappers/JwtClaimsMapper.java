package mappers;

import dto.UserDTO;
import entities.UsersEntity;
import mappers.decorators.JwtClaimsDecorator;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.IOException;

/**
 * Created by steven on 04/09/16.
 */
@Mapper(componentModel = "spring")
@DecoratedWith(JwtClaimsDecorator.class)
public interface JwtClaimsMapper {

    JwtClaims toJwtClaims(UserDTO user) throws IOException;

    UserDTO toUser(JwtClaims jwtClaims) throws MalformedClaimException, IOException;
}
