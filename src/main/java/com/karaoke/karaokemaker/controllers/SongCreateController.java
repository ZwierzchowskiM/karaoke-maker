package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.model.Bar;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/song-create")
class SongCreateController {

    SongRepository songRepository;
    SongService songService;


    public SongCreateController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }


    @GetMapping
    String createSong( Model model) {

        return "song-create";
    }



    @PostMapping("/addBars")
    public String addBars(@RequestParam Integer number) {
        return "redirect:/";
    }


}

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