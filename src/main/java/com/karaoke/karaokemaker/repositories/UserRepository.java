package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
