package com.karaoke.karaokemaker.controllers;


import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.exceptions.AudioFileNotFoundException;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
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
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.karaoke.karaokemaker.service.SongDtoMapper.mapToSongDtosList;

@RestController
@RequestMapping("/api/v1/songs")
class SongController {

    SongRepository songRepository;
    SongService songService;
    UserService userService;

    public SongController(SongRepository songRepository, SongService songService, UserService userService) {
        this.songRepository = songRepository;
        this.songService = songService;
        this.userService = userService;
    }


    @PostMapping("/generate")
    public ResponseEntity<?> postSong(@RequestBody SongRequestDto request) {


        Song generatedSong = new Song();
        try {
            generatedSong = songService.saveSong(request);
        } catch (UnsupportedAudioFileException | IOException | AudioFileNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }

        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(generatedSong.getUuid())
                .toUri();
        System.out.println(savedSongUri);

        return ResponseEntity.created(savedSongUri).body(generatedSong);
    }

    @GetMapping("/generate/{uuid}")
    @ResponseBody
    public ResponseEntity<?> getSongPreviewDownload(@PathVariable String uuid) throws IOException, UnsupportedAudioFileException {

        Song generatedSong = songService.getFromCache(uuid);

        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        String songPath;
        songPath = songService.getSongPath(generatedSong, "WAV");

        if (songService.checkIsFilePresent(songPath)) {
            songPath = generatedSong.getPathWavFile();
        } else {
            songPath = songService.generateSong(generatedSong,"WAV");
        }


        File downloadFile = new File(songPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }


    @PostMapping("/generate/{uuid}/save")
    @ResponseBody
    public ResponseEntity<?> postSongPreview(@PathVariable String uuid) {

        Song generatedSong = songService.getFromCache(uuid);
        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(songService.saveSong(generatedSong));
    }


    @GetMapping("/")
    public ResponseEntity<List<SongDto>> getSongs() {
        return ResponseEntity.ok(mapToSongDtosList(songService.getUserSongs()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return ResponseEntity.ok(mapToSongDtosList(songService.getAllSongs()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleSong(@PathVariable Long id) {

        return songService.getSingleSong(id)
                .map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping(path = "/{id}/download")
    public ResponseEntity<Resource> getSongDownload(@PathVariable Long id, @RequestParam String format) throws Exception {


        Song song = songService.getSingleSong(id).orElseThrow();
        String songPath = songService.getSongPath(song, format);
        String downloadPath;
        //czy to jest potrzebne? czy od razu tworzyć nowy plik?
//        if (songService.checkIsFilePresent(songPath)) {
//            downloadPath = songPath;
//        } else {
//        }
        downloadPath = songService.generateSong(song, format);
        File downloadFile = new File(downloadPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));


        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


    @PutMapping("/{id}")
    ResponseEntity<?> putSong(@PathVariable Long id, @RequestBody SongRequestDto song) throws UnsupportedAudioFileException, IOException {


        Optional<Song> replacedSong = null;
        try {
            replacedSong = songService.replaceSong(id, song);

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }


        return replacedSong
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


}
