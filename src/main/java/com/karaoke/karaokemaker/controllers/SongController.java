package com.karaoke.karaokemaker.controllers;


import com.karaoke.karaokemaker.model.Request;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import com.karaoke.karaokemaker.service.SongService;
//import org.ehcache.CacheManager;
//import org.ehcache.config.builders.CacheManagerBuilder;
//import org.ehcache.xml.XmlConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin
class SongController {

    SongRepository songRepository;
    SongService songService;


    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;

    }

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






    @PostMapping("/song/library/add")
//    @ResponseBody
    public String saveSong(@RequestParam String name) {
        Song songToSave = new Song(name);
        songService.saveSong(songToSave);
//        return songService.saveSong(songToSave);

        return "redirect:/song/library";

    }


    @DeleteMapping("/song/{id}")
    ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/song/compose/generate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
//    @RequestMapping(value = "/song/compose/generate", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody

    public ResponseEntity<Song> generateSong(@RequestBody Request request) {
//    public String generateSong(@RequestBody Request request) {

        System.out.println("Tworze utwór: ");
        System.out.println(request.getName());
        System.out.println("Akordy to: ");
        System.out.println(request.getChords());
        Song generatedSong = new Song();
        try {
           generatedSong =  songService.generateSong(request);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

//        System.out.println(Arrays.toString(request.getChords()));


        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(generatedSong.getUuid())
                .toUri();
        System.out.println(savedSongUri);
        return ResponseEntity.created(savedSongUri).body(generatedSong);

//        return "redirect:/song/library";

    }


    @GetMapping("/song/{uuid}")
    @ResponseBody
    public Song getSongFromCache(@PathVariable String uuid) {

        UUID songUuid = UUID.fromString(uuid);
        songService.checkCash(songUuid);
        return songService.getFromCache(songUuid);
    }


// umieszczenie song w cache
    @PostMapping("/song/new")
    @ResponseBody
    public Song postSong() {
        return songService.putSongToCache();
    }


    @ResponseBody
    @GetMapping("song/allsongs")

    public List<Song> getAllSongs(Model model) {

        List<Song> songs = (List<Song>) songRepository.findAll();

        return songs;
    }

//
//    @PostMapping("/song/savesongentity")
//    ResponseEntity<Song> saveSongEntity(@RequestBody Song song) {
//        Song savedSong = songService.saveSong(song);
//        URI savedSongUri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedSong.getId())
//                .toUri();
//        return ResponseEntity.created(savedSongUri).body(savedSong);
//    }



//    @GetMapping("/{uuid}")
//    @Cacheable(value= "songs", key="#uuid")
//    public String getSong(@PathVariable UUID uuid) {
//        log.info(">> User Controller: get user by id: " + id);
//        return userService.getUserById(id);
//    }



//    @PostMapping("/song/save")
//    public String saveSong(@RequestParam String name) {
//        Song songToSave = new Song(name);
//        songService.saveSong(songToSave);
//
//        return "redirect:/song/library";
//
//    }

//--- tworzy URL
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



//--- /przekieroanie na dany url
//        Note noteToSave = new Note(id, note);
//        boolean saved = noteService.save(noteToSave);
//        if (saved) {
//            return UriComponentsBuilder
//                    .fromPath("redirect:note") //ścieżka bazowa
//                    .queryParam("id", id) //dodajemy parametr ?id=XYZ
//                    .build().toString();
//        } else {
//            return "redirect:duplicate";
//        }








//--- zwraca kod odpowiedzei Created 201 -- nie działa przez formularz
//    @PostMapping("/saveform")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void saveSongfromForm(@RequestParam String name) {
//        Song songToSave = new Song(name);
//        songService.saveSong(songToSave);
//
//    }

//--- replace Song
//    @PutMapping("/{id}")
//    ResponseEntity<?> replaceSong(@PathVariable Long id, @RequestBody Song song) {
//        return songService.replaceSong(id, song)
//                .map(c -> ResponseEntity.noContent().build())
//                .orElse(ResponseEntity.notFound().build());
//    }


}
