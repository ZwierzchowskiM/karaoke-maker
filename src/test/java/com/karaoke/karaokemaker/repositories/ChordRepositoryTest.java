package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.model.Chord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ChordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ChordRepository chordRepository;


    @Test
    public void whenFindAll() {

        // given
        Chord chord1 = new Chord();
        Chord chord2 = new Chord();
        entityManager.persist(chord1);
        entityManager.flush();
        entityManager.persist(chord2);
        entityManager.flush();

        //when
        List<Chord> chords = chordRepository.findAll();

        //then
        assertThat(chords.size()).isEqualTo(2);

    }


}