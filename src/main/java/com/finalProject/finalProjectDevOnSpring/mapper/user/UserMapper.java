package com.finalProject.finalProjectDevOnSpring.mapper.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.UserRole;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoleRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.BaseMapper;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = BaseMapper.class)
public abstract class UserMapper implements BaseMapper<User, UserDto, UserChangeRequest> {
    @Autowired
    private RoleRepository roleRepository;

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToEnum")
    @Override
    public abstract UserDto toDto(User user);


    @Mapping(target = "role", source = "role", qualifiedByName = "enumToRole")
    @Override
    public abstract User toEntity(UserDto userDto);

    @Mapping(target = "role", source = "role", qualifiedByName = "enumToRole")
    @Override
    public abstract User changeToEntity(UserChangeRequest userChangeRequest);

    @Named("roleToEnum")
    public Set<RoleType> roleToEnum(Set<UserRole> role) {
        return role.stream().map(UserRole::getRole).collect(Collectors.toSet());
    }

    @Named("enumToRole")
    public Set<UserRole> enumToRole(Set<RoleType> role) throws ApplicationNotFoundException {
        Set<UserRole> result = new HashSet<>();
        for (RoleType roleEnum : role) {
            var entityRole = roleRepository.findByRole(roleEnum)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Role with name: {0} not found", roleEnum)));
            result.add(entityRole);
        }
        return result;
    }
}
