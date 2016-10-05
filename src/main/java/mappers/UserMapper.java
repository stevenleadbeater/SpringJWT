package mappers;

import dto.UserDTO;
import entities.UsersEntity;
import mappers.geometry.PointMapper;
import org.mapstruct.Mapper;

/**
 * Created by steven on 01/09/16.
 */
@Mapper(componentModel = "spring", uses = PointMapper.class)
public interface UserMapper {
    UserDTO toUserDTO(UsersEntity usersEntity);

    UsersEntity toUserEntity(UserDTO userDTO);
}
