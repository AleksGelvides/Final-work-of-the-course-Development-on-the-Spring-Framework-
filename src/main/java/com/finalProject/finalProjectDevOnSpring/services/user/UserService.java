package com.finalProject.finalProjectDevOnSpring.services.user;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.User;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoleRepository;
import com.finalProject.finalProjectDevOnSpring.models.repository.UserRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.user.UserMapper;
import com.finalProject.finalProjectDevOnSpring.services.CommonService;
import com.finalProject.finalProjectDevOnSpring.services.statistic.StatisticService;
import com.finalProject.finalProjectDevOnSpring.web.dto.search.BaseSearchCriteria;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
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

    private User requestToUser(UserChangeRequest request) {
        return mapper.changeToEntity(request)
                .setPassword(passwordEncoder.encode(request.password()));
    }

    private void updateRoles(Set<RoleType> roles, User user) throws ApplicationNotFoundException {
        for (RoleType roleEnum : roles) {
            var role = roleRepository.findByRole(roleEnum)
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            MessageFormat.format("Role with type {0} not found", roleEnum.name())));
            role.getUsers().add(user);
            roleRepository.save(role);
        }
    }
}
