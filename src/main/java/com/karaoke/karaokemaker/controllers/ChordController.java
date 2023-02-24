package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.exceptions.ResourceNotFoundException;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.ChordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/chords")
public class ChordController {
    ChordRepository chordRepository;
    ChordService chordService;

    public ChordController(ChordRepository chordRepository, ChordService chordService) {
        this.chordRepository = chordRepository;
        this.chordService = chordService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Chord> createChord(@RequestBody Chord chord) {
        return ResponseEntity.ok(chordService.saveChord(chord));
    }

    @GetMapping("/")
    public ResponseEntity<List<Chord>> getChords() {
        return ResponseEntity.ok(chordService.getAllChords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSignleChord(@PathVariable Long id) {

        Chord chord = chordService.getSingleChord(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chord with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(chord);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteChord(@PathVariable Long id) {

        if (chordRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Chord with ID :" + id + " Not Found");
        }
        chordService.deleteChord(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> updateChord(@PathVariable Long id, @RequestBody Chord chord) {
        Chord updatedChord = chordService.replaceChord(id, chord)
                .orElseThrow(() -> new ResourceNotFoundException("Chord with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(updatedChord);
    }

}


