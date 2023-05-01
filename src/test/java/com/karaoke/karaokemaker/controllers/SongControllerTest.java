package com.karaoke.karaokemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.mapper.SongDtoMapper;
import com.karaoke.karaokemaker.service.SongService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @MockBean
    private SongDtoMapper songDtoMapper;

    @MockBean
    private SongRepository songRepository;


    @Test
    void getSingleSongs_thenGetSongStatusOk() throws Exception {

        Song song1 = new Song();
        song1.setName("Foo");
        song1.setId(1L);

        when(songService.getSingleSong(song1.getId())).thenReturn(Optional.of(song1));

        mockMvc.perform(get("/api/v1/songs/" + song1.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Foo")));
    }


    @Test
    public void createSong_thenReturnSongsStatusCreated() throws Exception {

        Song song1 = new Song();
        song1.setName("Foo");
        song1.setId(1L);

        given(songService.createSong(Mockito.any(SongRequestDto.class))).willReturn(song1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/songs/")
                        .content(asJsonString(song1))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Foo")));

    }


    @Test
    void getAllSongsList_thenReturnJsonArrayAllSongs() throws Exception {

        SongDto song1 = SongDto.builder()
                .name("First")
                .build();
        SongDto song2 = SongDto.builder()
                .name("Second")
                .build();

        List<SongDto> allSongs = Arrays.asList(song1,song2);
        when(songDtoMapper.mapToSongDtosList(Mockito.anyList())).thenReturn(allSongs);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/songs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("First"))).
                andExpect(jsonPath("$[1].name", is("Second")));

        verify(songService, VerificationModeFactory.times(1)).getAllSongs();
        reset(songService);
    }

    @Test
    public void deleteSongById_thenReturnStatusNoContent() throws Exception {
        Song song1 = new Song();

        song1.setId(1L);
        song1.setName("test");

        when(songRepository.findById(song1.getId())).thenReturn(Optional.of(song1));
        doNothing().when(songService).deleteSong(song1.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/songs/" + song1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    public void updateSongRecord_thenReturnStatusOk() throws Exception {
        Song song1 = new Song();
        song1.setId(1L);
        song1.setName("test");
        Song song2 = new Song();
        song2.setId(2L);
        song2.setName("test2");

        when(songService.replaceSong(eq(song1.getId()), Mockito.any())).thenReturn(Optional.of(song2));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/songs/" + song1.getId())
                        .content(asJsonString(song2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test2")));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}