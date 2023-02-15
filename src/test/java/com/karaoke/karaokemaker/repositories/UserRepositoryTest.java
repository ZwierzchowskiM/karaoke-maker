package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindByEmail_thenReturnUser() {

        // given
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@gmail.com")
                .build();

        entityManager.persist(user);
        entityManager.flush();

        //when
        Optional<User> found = userRepository.findByEmail(user.getEmail());

        //then
        assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
    }


    @Test
    public void whenDeleteByEmail_thenReturnNull() {

        // given
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@gmail.com")
                .build();

        entityManager.persist(user);
        entityManager.flush();

        //when
        userRepository.deleteByEmail(user.getEmail());

        //then
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        assertThat(found).isEmpty();
    }



}