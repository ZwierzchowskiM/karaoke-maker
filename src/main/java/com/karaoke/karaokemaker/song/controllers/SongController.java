package com.karaoke.karaokemaker.song.controllers;

import com.karaoke.karaokemaker.song.model.Song;
import com.karaoke.karaokemaker.song.repositories.SongRepository;
import com.karaoke.karaokemaker.song.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/songs")
class SongController {

    SongRepository songRepository;
    SongService songService;


    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService= songService;
    }

    @Transactional
    @GetMapping
    public String getSongs(Model model) {

        List<Song> songs = (List<Song>) songRepository.findAll();

        model.addAttribute("songs", songs);
        return "songs";
    }




    @PostMapping("/savesongentity")
    ResponseEntity<Song> saveSongEntity(@RequestBody Song song) {
        Song savedSong = songService.saveSong(song);
        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSong.getId())
                .toUri();
        return ResponseEntity.created(savedSongUri).body(savedSong);
    }


//



    @PostMapping("/save")
    public String saveSong(@RequestParam String name) {
        Song songToSave = new Song(name);
        songService.saveSong(songToSave);

        return "redirect:/";

    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    ResponseEntity<?> replaceSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.replaceSong(id, song)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }



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

    //    zwraca obiekt json z szystkimi songami
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


}