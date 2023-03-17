package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.exceptions.ResourceNotFoundException;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.ChordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/chords")
public class ChordController {
    ChordRepository chordRepository;
    ChordService chordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChordController.class);

    public ChordController(ChordRepository chordRepository, ChordService chordService) {
        this.chordRepository = chordRepository;
        this.chordService = chordService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Chord> createChord(@RequestBody Chord chord) {
        LOGGER.info("Create chords");
        return ResponseEntity.ok(chordService.saveChord(chord));
    }

    @GetMapping("/")
    public ResponseEntity<List<Chord>> getChords() {
        LOGGER.info("Getting all chords");
        return ResponseEntity.ok(chordService.getAllChords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSignleChord(@PathVariable Long id) {

        LOGGER.info("Getting single chord");
        Chord chord = chordService.getSingleChord(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chord with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(chord);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteChord(@PathVariable Long id) {

        LOGGER.info("Deleting chord");

        if (chordRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Chord with ID :" + id + " Not Found");
        }
        chordService.deleteChord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> updateChord(@PathVariable Long id, @RequestBody Chord chord) {

        LOGGER.info("Updating chord");

        Chord updatedChord = chordService.replaceChord(id, chord)
                .orElseThrow(() -> new ResourceNotFoundException("Chord with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(updatedChord);
    }

}


