package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllUsersByRole(String role);
    void deleteByEmail(String email);
}
