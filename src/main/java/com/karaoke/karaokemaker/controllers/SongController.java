package com.karaoke.karaokemaker.controllers;

import com.karaoke.karaokemaker.domain.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;


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
    @GetMapping("/songs")
    public String getSongs(Model model) {

        List<Song> songs = (List<Song>) songRepository.findAll();

        model.addAttribute("songs", songs);
        return "songs";
    }

    @Transactional
    @GetMapping("/home")
    public String home(Model model) {

        return "index";
    }


    @PostMapping("/save")
    public String saveSong(@RequestParam String name) {
        Song songToSave = new Song(name);
        songService.saveSong(songToSave);

        return "redirect:songs";

//            return UriComponentsBuilder
//                    .fromPath("redirect:songs") //ścieżka bazowa
////                    .queryParam("id", id) //dodajemy parametr ?id=XYZ
//                    .build().toString();

    }

    @GetMapping("/wiki")
    String wiki() {
        //...
        return "redirect:https://www.wikipedia.org";
    }

}