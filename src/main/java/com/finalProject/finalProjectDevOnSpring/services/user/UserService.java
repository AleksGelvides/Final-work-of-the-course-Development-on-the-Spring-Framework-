package com.finalProject.finalProjectDevOnSpring.services.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.UserRole;
import com.finalProject.finalProjectDevOnSpring.repository.RoleRepository;
import com.finalProject.finalProjectDevOnSpring.repository.UserRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.user.UserMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.services.statistic.StatisticService;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService extends CommonService<UserDto, UserChangeRequest, User, Long> {
    private final StatisticService statisticService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       StatisticService statisticService) {
        super(userRepository, userMapper, null);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.statisticService = statisticService;
    }

    public UserDto findByUsername(String username) throws ApplicationNotFoundException {
        return mapper.toDto(((UserRepository) repository).findByUsername(username)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        MessageFormat.format("User with username: {0} not found", username))
                ));
    }

    public User findUserById(Long id) throws ApplicationNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(
                MessageFormat.format("User with id: {0} not found", id))
        );
    }

    protected User findUserByUsername(String username) throws ApplicationNotFoundException {
        return ((UserRepository) repository).findByUsername(username).orElseThrow(() -> new ApplicationNotFoundException(
                MessageFormat.format("User with username: {0} not found", username))
        );
    }

    @Override
    @Transactional
    public UserDto saveOrUpdate(Long id, UserChangeRequest request) throws ApplicationException, ApplicationNotFoundException {
        UserDto result;
        User user;

        if (Objects.isNull(id)) {
            if (checkExistUserExistByUsernameAndEmail(request.username(), request.email())) {
                throw new ApplicationException(
                        MessageFormat.format("User with username {0} and email {1} was registered", request.username(), request.email())
                );
            }
            user = repository.save(requestToUser(request));
            result = mapper.toDto(user);
            statisticService.createRegistrationEvent(result.id());
        } else {
            var updateUserEntity = mapper.merge(
                    mapper.toEntity(findById(id)),
                    requestToUser(request));
            user = repository.save(updateUserEntity);
            result = mapper.toDto(user);
        }
        updateRoles(request.role(), user);
        return result;
    }

    private Boolean checkExistUserExistByUsernameAndEmail(String username, String email) {
        return ((UserRepository) repository).existsByUsernameAndEmail(username, email);
    }

    private User requestToUser(UserChangeRequest request) throws ApplicationNotFoundException {
        return mapper.changeToEntity(request)
                .setRole(enumToRole(request.role()))
                .setPassword(passwordEncoder.encode(request.password()));
    }

    private void updateRoles(Set<RoleType> roles, User user) throws ApplicationNotFoundException {
        for (RoleType roleEnum : roles) {
            var role = getRole(roleEnum);
            role.getUsers().add(user);
            roleRepository.save(role);
        }
    }

    private Set<UserRole> enumToRole(Set<RoleType> role) throws ApplicationNotFoundException {
        Set<UserRole> result = new HashSet<>();
        for (RoleType roleEnum : role) {
            var entityRole = getRole(roleEnum);
            result.add(entityRole);
        }
        return result;
    }

    private UserRole getRole(RoleType roleType) throws ApplicationNotFoundException {
        return roleRepository.findByRole(roleType)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        MessageFormat.format("Role with name: {0} not found", roleType)));
    }
}
