package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.ChordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/chords")
public class ChordController {
    ChordRepository chordRepository;
    ChordService chordService;

    public ChordController(ChordRepository chordRepository, ChordService chordService) {
        this.chordRepository = chordRepository;
        this.chordService = chordService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Chord>  postChord(@RequestBody Chord chord) {
        return ResponseEntity.ok (chordService.add(chord));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Chord>>  getChords() {
        return ResponseEntity.ok (chordService.getChords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSignleChord(@PathVariable Long id) {

        return  chordService.getSingleChord(id)
                .map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteChord(@PathVariable Long id) {
        chordService.deleteChord(id);
        System.out.println("chord deleted");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> putChord(@PathVariable Long id, @RequestBody Chord chord) {
        return chordService.replaceChord(id, chord)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

}


