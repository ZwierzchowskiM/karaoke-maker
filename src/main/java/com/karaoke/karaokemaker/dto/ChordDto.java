package com.karaoke.karaokemaker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChordDto {
    String singleNote;
    String type;
    int length;
    int complexity;


    @Override
    public String toString() {
        return "ChordRequest{" +
                "singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + length +
                ", complexity=" + complexity +
                '}';
    }
}
