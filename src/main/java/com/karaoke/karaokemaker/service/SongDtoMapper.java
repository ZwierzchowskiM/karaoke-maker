package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.model.Song;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SongDtoMapper {

    private SongDtoMapper() {
    }


    public List<SongDto> mapToSongDtosList(List<Song> songs) {
        return songs.stream()
                .map(SongDtoMapper::mapToSongDto)
                .toList();
    }

    public static SongDto mapToSongDto(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }

}
