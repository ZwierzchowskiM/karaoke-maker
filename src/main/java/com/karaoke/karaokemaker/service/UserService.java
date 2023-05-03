package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.dto.UserDto;
import com.karaoke.karaokemaker.dto.UserRegistrationDto;
import com.karaoke.karaokemaker.exceptions.ResourceNotFoundException;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.UserRepository;
import com.karaoke.karaokemaker.service.mapper.UserDtoMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final Validator validator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;


    public UserService(Validator validator, UserRepository userRepository, PasswordEncoder passwordEncoder, UserDtoMapper userDtoMapper) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
    }

    public List<UserDto> findUsers() {

        List<User> users = (List<User>) userRepository.findAll();
        List<UserDto> usersDto = userDtoMapper.fromList(users);
        return usersDto;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private boolean isCurrentUserAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
    }

    public Long currentUserId() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return findCredentialsByEmail(userName).get().getId();
    }

    public String currentUserName() {

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Transactional
    public User register(UserRegistrationDto registration) {
        User savedUser = new User();
        savedUser.setFirstName(registration.getFirstName());
        savedUser.setLastName(registration.getLastName());
        savedUser.setEmail(registration.getEmail());
        String passwordHash = passwordEncoder.encode(registration.getPassword());
        savedUser.setPassword(passwordHash);
        savedUser.setRole(registration.getRole());
        userRepository.save(savedUser);
        return savedUser;
    }

    public Optional<User> replaceUser(Long id, UserRegistrationDto userDto) {

        if (!userRepository.existsById(id)) {
            return Optional.empty();
        }
        User modifiedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));
        modifiedUser.setFirstName(userDto.getFirstName());
        modifiedUser.setLastName(userDto.getLastName());
        modifiedUser.setEmail(userDto.getEmail());
        User updatedEntity = userRepository.save(modifiedUser);
        return Optional.of(updatedEntity);

    }


}


