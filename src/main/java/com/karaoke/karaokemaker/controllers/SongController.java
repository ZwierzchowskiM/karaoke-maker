package com.karaoke.karaokemaker.controllers;


import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.exceptions.ResourceNotFoundException;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.mapper.SongDtoMapper;
import com.karaoke.karaokemaker.service.SongService;
import com.karaoke.karaokemaker.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("${api.path}/songs")
class SongController {

    SongRepository songRepository;
    SongService songService;
    UserService userService;
    SongDtoMapper songDtoMapper;

    public SongController(SongRepository songRepository, SongService songService, UserService userService, SongDtoMapper songDtoMapper) {
        this.songRepository = songRepository;
        this.songService = songService;
        this.userService = userService;
        this.songDtoMapper = songDtoMapper;
    }

    @PostMapping("/")
    public ResponseEntity<?> createSong(@RequestBody SongRequestDto request) {
        Song generatedSong = songService.createSong(request);

        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(generatedSong.getUuid())
                .toUri();
        System.out.println(savedSongUri);

        return ResponseEntity.created(savedSongUri).body(generatedSong);
    }

    @ResponseBody
    @GetMapping("/preview/{uuid}/generate")
    public ResponseEntity<?> generateSongWavPreview(@PathVariable String uuid) {

        Song generatedSong = songService.getFromCache(uuid);

        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        String songPath = null;
        try {
            songPath = songService.generateSong(generatedSong, "WAV");
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        generatedSong.setPathWavFile(songPath);
        return ResponseEntity.ok()
                .body(generatedSong);
    }

    @GetMapping("/preview/{uuid}/download")
    @ResponseBody
    public ResponseEntity<?> downloadSongPreview(@PathVariable String uuid) {

        Song generatedSong = songService.getFromCache(uuid);
        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        String songPath = generatedSong.getPathWavFile();
        File downloadFile = new File(songPath);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(downloadFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


    @PostMapping("/preview/{uuid}/save")
    @ResponseBody
    public ResponseEntity<?> saveSongPreviewToDatabase(@PathVariable String uuid) {

        Song generatedSong = songService.getFromCache(uuid);
        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(songService.saveSong(generatedSong));
    }


    @GetMapping("/user")
    public ResponseEntity<List<SongDto>> getSongsList() {
        return ResponseEntity.ok(songDtoMapper.mapToSongDtosList(songService.getUserSongs()));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SongDto>> getAllSongsList() {
        return ResponseEntity.ok(songDtoMapper.mapToSongDtosList(songService.getAllSongs()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id) {

        Song song = songService.getSingleSong(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(song);

    }

    @GetMapping(path = "/{id}/download")
    public ResponseEntity<Resource> downloadSong(@PathVariable Long id, @RequestParam String format) {

        Song song = songService.getSingleSong(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));
        String songPath = songService.getSongPath(song, format);

        File downloadFile = new File(songPath);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(downloadFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @GetMapping(path = "/{id}/generate")
    public ResponseEntity<String> generateSongFile(@PathVariable Long id, @RequestParam String format) {

        Song song = songService.getSingleSong(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));
        String songPath = null;

        try {
            songPath = songService.generateSong(song, format);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(songPath);
    }

    @PutMapping("/{id}")
    ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody SongRequestDto song) {

        Song replacedSong = songService.replaceSong(id, song)
                .orElseThrow(() -> new ResourceNotFoundException("Song with ID :" + id + " Not Found"));

        return ResponseEntity.ok().body(replacedSong);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {

        if (songRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Song with ID :" + id + " Not Found");
        }
        songService.deleteSong(id);

        return ResponseEntity.noContent().build();
    }


}
