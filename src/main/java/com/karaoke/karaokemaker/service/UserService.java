package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.UserRepository;
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

    private final Validator validator;
    UserRepository userRepository;
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final PasswordEncoder passwordEncoder;


    public UserService(Validator validator, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if (!errors.isEmpty()) {
            System.out.println(">Obiekt nie może być dodany, lista błędów:");
            errors.forEach(err ->
                    System.out.printf(">>> %s %s (%s)\n",
                            err.getPropertyPath(),
                            err.getMessage(),
                            err.getInvalidValue())
            );
        } else {
            userRepository.save(user);
            System.out.println(">Obiekt został dodany");
        }
    }



    public List<String> findAllUserEmails() {
        return userRepository.findAllUsersByRole(USER_ROLE)
                .stream()
                .map(User::getEmail)
                .toList();
    }
    public List<String> findAllUsers() {
        return userRepository.findAllUsersByRole(USER_ROLE)
                .stream()
                .map(User::getEmail)
                .toList();
    }

    public Optional<User> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        if (isCurrentUserAdmin()) {
            userRepository.deleteByEmail(email);
        }
    }

        private boolean isCurrentUserAdmin() {
            return SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
        }

    @Transactional
    public void register(User registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        String passwordHash = passwordEncoder.encode(registration.getPassword());
        user.setPassword(passwordHash);
        user.setRole("USER");
        userRepository.save(user);
    }
    }


