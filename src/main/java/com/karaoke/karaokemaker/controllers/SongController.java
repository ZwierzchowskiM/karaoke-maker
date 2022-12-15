package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.ChordRequest;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
class SongController {

    SongRepository songRepository;
    SongService songService;


    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService= songService;
    }

    @Transactional
    @GetMapping ("/song/library")
    public String getSongs(Model model) {

        List<Song> songs = (List<Song>) songRepository.findAll();

        model.addAttribute("songs", songs);
        return "songLibrary";
    }

    @GetMapping("/song/compose")
    public String compose(Model model) {
        return "songCompose";
    }



    @PostMapping("/song/savesongentity")
    ResponseEntity<Song> saveSongEntity(@RequestBody Song song) {
        Song savedSong = songService.saveSong(song);
        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSong.getId())
                .toUri();
        return ResponseEntity.created(savedSongUri).body(savedSong);
    }


//



    @PostMapping("/song/save")
    public String saveSong(@RequestParam String name) {
        Song songToSave = new Song(name);
        songService.saveSong(songToSave);

        return "redirect:/song/library";

    }


    @DeleteMapping("/song/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }





//    @RequestMapping(value = "/song/compose/generate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
//    @ResponseBody
//    public ResponseEntity<Song> generateSong(@RequestBody ChordRequest[] chords) {
//
//        System.out.println(Arrays.toString(chords));
//        Song generatedSong = new Song();
//        try {
//            generatedSong = songService.generateSong(chords);
//        } catch (UnsupportedAudioFileException | IOException  | NullPointerException e) {
//            System.out.println("Niepowodzenie");
//        }
//
//        Song savedSong = songService.saveSong(generatedSong);
//        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedSong.getId())
//                .toUri();
//        return ResponseEntity.created(savedSongUri).body(savedSong);
//
//    }

//@RequestMapping(value = "/song/compose/generate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
//    @ResponseBody
//    public List<String> generateSong(@RequestBody ChordRequest[] chords) {
//        List<String> response = new ArrayList<>();
//        for (ChordRequest chord: chords) {
//            response.add("Saved chord: " + chord);
//        }
//        return response;
//    }


    @RequestMapping(value = "/song/compose/generate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    @ResponseBody
    public ChordRequest[] generateSong(@RequestBody ChordRequest[] chords) {

        System.out.println(Arrays.toString(chords));
        return chords;
    }






//
//    //        zwraca obiekt json z szystkimi songami
//    @Transactional
//    @ResponseBody
//    @GetMapping("/test")
//    public List<Song> getSongsTest(Model model) {
//
//        List<Song> songs = (List<Song>) songRepository.findAll();
//
//        return songs;
//    }
//



    //    @Transactional
//    @GetMapping("/home")
//    public String home(Model model) {
//
//        return "index";
//    }




///// zwraca kod odpowiedzei Created 201 -- nie działa przez formularz
//    @PostMapping("/saveform")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveSongfromForm(@RequestParam String name) {
//        Song songToSave = new Song(name);
//        songService.saveSong(songToSave);
//
//    }


//    @PutMapping("/{id}")
//    ResponseEntity<?> replaceSong(@PathVariable Long id, @RequestBody Song song) {
//        return songService.replaceSong(id, song)
//                .map(c -> ResponseEntity.noContent().build())
//                .orElse(ResponseEntity.notFound().build());
//    }


}
