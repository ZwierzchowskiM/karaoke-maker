package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.ChordService;
import org.springframework.http.ResponseEntity;
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


    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public Chord postChord(@RequestBody Chord chord) {

        System.out.println("Dodaje akord ");

        Chord addedChord = new Chord();
        addedChord= chordService.add(chord);

        return addedChord;


    }

    @GetMapping("all")
    public List<Chord> getChords() {
        return chordService.getChords();
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteChord(@PathVariable Long id) {
        chordService.deleteChord(id);
        System.out.println("chord deleted");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> putChord(@PathVariable Long id, @RequestBody Chord chord) {
        return chordService.replaceChord(id, chord)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

}


