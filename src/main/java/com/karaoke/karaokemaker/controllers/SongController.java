package com.karaoke.karaokemaker.controllers;


import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.dto.SongRequestDto;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.karaoke.karaokemaker.service.SongDtoMapper.mapToSongDtosList;

@RestController
@RequestMapping("/api/songs")
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
           generatedSong =  songService.generateSong(request);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No audio file");

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
    public ResponseEntity<?> getSongPreview(@PathVariable String uuid) throws FileNotFoundException {

        Song generatedSong = songService.getFromCache(uuid);

        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        String songPath = generatedSong.getPath();
        File downloadFile = new File(songPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }



    @PostMapping("/generate/{uuid}/save")
    @ResponseBody
    public  ResponseEntity<?> postSongPreview(@PathVariable String uuid) {

        Song generatedSong = songService.getFromCache(uuid);
        if (generatedSong == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(songService.saveSong(generatedSong));
    }


    @GetMapping("/")
    public ResponseEntity<List<SongDto>> getSongs()
    {
        return ResponseEntity.ok(mapToSongDtosList(songService.getUserSongs()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity <List<SongDto>> getAllSongs() {
        return ResponseEntity.ok(mapToSongDtosList(songService.getAllSongs()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleSong(@PathVariable Long id) {

        return  songService.getSingleSong(id)
                .map(c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping(path = "/{id}/download")
    public ResponseEntity<Resource> getSingleSongDownload(@PathVariable Long id) throws Exception {

        String songPath = songService.getSongPath(id);
        File downloadFile = new File(songPath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
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


        return  replacedSong
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }







}
