package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class SongRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SongRepository songRepository;


    @Test
    public void whenFindAll() {

        // given
        Song song1 = new Song();
        Song song2 = new Song();
        Song song3 = new Song();
        entityManager.persist(song1);
        entityManager.flush();
        entityManager.persist(song2);
        entityManager.flush();
        entityManager.persist(song3);
        entityManager.flush();

        //when
        List<Song> songs = songRepository.findAll();

        //then
        assertThat(songs.size()).isEqualTo(3);

    }

    @Test
    public void whenFindAllByUserId_thenReturnSongs() {

        // given
        long userId = 1;
        Song song1 = new Song();
        song1.setUserId(userId);
        Song song2 = new Song();
        song2.setUserId(userId);
        entityManager.persist(song1);
        entityManager.flush();
        entityManager.persist(song2);
        entityManager.flush();

        //when
        List<Song> songs = songRepository.findAllByUserId(userId);

        //then
        assertThat(songs.size()).isEqualTo(2);

    }


}