package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
class ChordServiceTest {

    @Autowired
    ChordService chordService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChordRepository chordRepository;

    @BeforeEach
    public void setUp() {

    }


    @Test
    public void whenValidId_thenChordShouldBeFound() {

        //given
        Chord chord1 = new Chord();
        chord1.setId(1L);
        when(chordRepository.findById(chord1.getId())).thenReturn(Optional.of(chord1));
        //when
        Optional<Chord> fromDb = chordService.getSingleChord(1L);

        //then
        assertThat(fromDb.get().getId()).isEqualTo(1L);
    }


    @Test
    public void whenInValidId_thenChordShouldBeNotFound() {
        //given
        when(chordRepository.findById(-99L)).thenReturn(Optional.empty());
        //when
        Optional<Chord> fromDb = chordService.getSingleChord(-11L);

        //then
        assertThat(fromDb.isEmpty());
    }

    @Test
    public void given3Chords_whengetAllChords_thenReturn3Records() {
       //given
        Chord chord1 = new Chord();
        chord1.setId(1L);
        Chord chord2 = new Chord();
        chord2.setId(2L);
        Chord chord3 = new Chord();
        chord3.setId(3L);
        List<Chord> allChords = Arrays.asList(chord1,chord2,chord3);

        when(chordRepository.findAll()).thenReturn(allChords);

        //when
        List<Chord> chords = chordService.getAllChords();

        //then
        assertThat(chords).hasSize(3).extracting(Chord::getId).contains(1L,2L,3L);
    }


    @Test
    public void whensaveChord_thensaveChord() {
        //given
        Chord chord1 = new Chord();
        chord1.setId(1L);


        when(chordRepository.save(chord1)).thenReturn(chord1);

        //when
        Chord savedChord =  chordService.saveChord(chord1);

        //then
        assertThat(savedChord.getId()).isEqualTo(chord1.getId());
    }



    @Test
    void deleteChord() {
        //given
        Chord chord1 = new Chord();
        chord1.setId(1L);
        chordRepository.save(chord1);

//        when(chordRepository.findById(1L)).thenReturn(Optional.of(chord1));
        doNothing().when(chordRepository).deleteById(chord1.getId());

        chordService.deleteChord(1L);

        verify(chordRepository).deleteById(1L);
    }

    @Test
    void replaceChord() {

        //given
        Chord chord1 = new Chord();
        chord1.setId(1L);
        chord1.setLength(Length.QUARTER_BAR);
        chordRepository.save(chord1);

        when(chordRepository.existsById(chord1.getId())).thenReturn(true);
        when(chordRepository.save(chord1)).thenReturn(chord1);

        chord1.setLength(Length.FULL_BAR);
        Chord savedChord = chordRepository.save(chord1);
        assertThat(savedChord.getLength()).isEqualTo(chord1.getLength());


    }



}