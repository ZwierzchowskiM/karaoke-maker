package com.karaoke.karaokemaker.dto;

import com.karaoke.karaokemaker.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChordDto {

    SingleNote singleNote;
    ChordType type;
    BassNote bassNote;
    Length length;
    Complexity complexity;

}
