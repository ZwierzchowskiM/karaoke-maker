package com.karaoke.karaokemaker.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class SongRequestDto {

    String songName;
    ChordDto[] chordDtos;

}
