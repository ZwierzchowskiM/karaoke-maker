package com.karaoke.karaokemaker.dto;

import com.karaoke.karaokemaker.enums.ChordType;
import com.karaoke.karaokemaker.enums.Complexity;
import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.enums.SingleNote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChordDto {
    SingleNote singleNote;
    ChordType type;
    Length length;
    Complexity complexity;


}
