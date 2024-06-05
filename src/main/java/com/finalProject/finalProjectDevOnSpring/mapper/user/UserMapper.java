package com.finalProject.finalProjectDevOnSpring.mapper.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.UserRole;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = BaseMapper.class)
public interface UserMapper extends BaseMapper<User, UserDto, UserChangeRequest> {

    @Override
    @Mapping(target = "role", source = "role", qualifiedByName = "roleToEnum")
    UserDto toDto(User user);

    @Override
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto userDto);

    @Override
    @Mapping(target = "role", ignore = true)
    User changeToEntity(UserChangeRequest userChangeRequest);

    @Named("roleToEnum")
    default Set<RoleType> roleToEnum(Set<UserRole> role) {
        return role.stream().map(UserRole::getRole).collect(Collectors.toSet());
    }
}
