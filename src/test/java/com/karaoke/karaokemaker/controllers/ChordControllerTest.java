package com.karaoke.karaokemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.ChordService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
//import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ChordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChordService chordService;

    @MockBean
    private ChordRepository chordRepository;


    @Test
    void givenChord_whenGetSingleChord_thenGetChord() throws Exception {

        //given
        Chord chord = new Chord();
        chord.setPath("test/path");
        chord.setId(1L);

        when(chordService.getSingleChord(chord.getId())).thenReturn(Optional.of(chord));

        //when
        mockMvc.perform(get("/api/v1/chords/" + chord.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path", Matchers.is("test/path")));
        //then

    }


    @Test
    public void givenChord_whenPostChord_thenCreateChord() throws Exception {

        Chord newChord = new Chord();
        newChord.setId(1L);
        newChord.setLength(Length.QUARTER_BAR);

        given(chordService.saveChord(Mockito.any())).willReturn(newChord);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/chords/add")
                        .content(asJsonString(newChord))
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.length", is("QUARTER_BAR")));

    }

    @Test
    void given3Chords_whenGetChords_thenReturnJsonArray() throws Exception {

        Chord chord1 = new Chord();
        chord1.setId(1L);
        Chord chord2 = new Chord();
        chord2.setId(2L);
        Chord chord3 = new Chord();
        chord3.setId(3L);

        List<Chord> allChords = Arrays.asList(chord1, chord2, chord3);
        when(chordService.getAllChords()).thenReturn(allChords);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/chords/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1))).
                andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));

        verify(chordService, VerificationModeFactory.times(1)).getAllChords();
        reset(chordService);

        //given
        //then

    }


    @Test
    public void deleteChordById_success() throws Exception {
        Chord chord1 = new Chord();
        chord1.setId(1L);

        when(chordRepository.findById(chord1.getId())).thenReturn(Optional.of(chord1));
        doNothing().when(chordService).deleteChord(chord1.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/chords/" + chord1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    // nie działa
    @Test
    public void updateChordRecord_success() throws Exception {
        Chord chord1 = new Chord();
        chord1.setId(1L);
        chord1.setLength(Length.QUARTER_BAR);
        Chord chord2 = new Chord();
        chord2.setId(2L);
        chord2.setLength(Length.FULL_BAR);


        when(chordService.replaceChord(chord1.getId(), chord2)).thenReturn(Optional.of(chord2));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/chords/" + chord1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(chord2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.length", is("FULL_BAR")));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

//    @Test
//    public void deleteChordById_notFound() throws Exception {
//        Chord chord1 = new Chord();
//        chord1.setId(99L);
//        Mockito.when(chordRepository.findById(chord1.getId())).thenReturn(null);
//
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/v1/chords/" + chord1.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof InvalidRequestException));
//    }


//    Test
//    public void updatePatientRecord_success() throws Exception {

//        PatientRecord updatedRecord = PatientRecord.builder()
//                .patientId(1l)
//                .name("Rayven Zambo")
//                .age(23)
//                .address("Cebu Philippines")
//                .build();
//
//        Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId())).thenReturn(Optional.of(RECORD_1));
//        Mockito.when(patientRecordRepository.save(updatedRecord)).thenReturn(updatedRecord);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.name", is("Rayven Zambo")));
//    }
//
//
