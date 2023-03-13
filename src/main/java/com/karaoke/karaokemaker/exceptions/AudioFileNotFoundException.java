package com.karaoke.karaokemaker.exceptions;

import com.karaoke.karaokemaker.enums.ChordType;
import com.karaoke.karaokemaker.enums.Complexity;
import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.enums.SingleNote;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@ResponseStatus(value = NOT_FOUND)
public class AudioFileNotFoundException extends RuntimeException {

    public AudioFileNotFoundException(SingleNote singleNote, ChordType type, Complexity complexity, Length length) {
        super("Audio File for chord " + singleNote + type + " complexity: " + complexity + " length: " + length + " is not found");
    }
}