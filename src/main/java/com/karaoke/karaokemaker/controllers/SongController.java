package com.karaoke.karaokemaker.controllers;


import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static com.karaoke.karaokemaker.service.SongDtoMapper.mapToSongDtos;

@RestController
@RequestMapping("/api/songs")
class SongController {

    SongRepository songRepository;
    SongService songService;


    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;

    }



    @RequestMapping(value = "/generate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public ResponseEntity<Song> postSong(@RequestBody SongRequestDto request) {

        System.out.println("Tworze utwór: ");
        System.out.println(request.getName());
        System.out.println("Akordy to: ");
        System.out.println(Arrays.toString(request.getChords()));


        Song generatedSong = new Song();
        try {
           generatedSong =  songService.generateSong(request);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        songService.saveSong(generatedSong);

        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(generatedSong.getUuid())
                .toUri();
        System.out.println(savedSongUri);

        return ResponseEntity.created(savedSongUri).body(generatedSong);


    }


    @GetMapping("/all")
    public List<SongDto> getSongs() {
        return mapToSongDtos(songService.getSongs());
    }


    @GetMapping("/{id}")
    public Song getSingleSong(@PathVariable Long id) {
        return songService.getSingleSong(id);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> putSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.replaceSong(id, song)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/song/{uuid}")
//    @ResponseBody
//    public Song getSongFromCache(@PathVariable String uuid) {
//
//        UUID songUuid = UUID.fromString(uuid);
//        songService.checkCash(songUuid);
//        return songService.getFromCache(songUuid);
//    }




}
