package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.web.dto.UserCreateDto;
import br.unesp.parking.manager.api.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class UserMapper {

    public static User toUser(UserCreateDto createDto) {
        return new ModelMapper().map(createDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<User> Users) {
        return Users.stream().map(UserMapper::toDto).toList();
    }
}
