package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Song;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Writer {
    String writeSong(Song song, String directory) throws UnsupportedAudioFileException, IOException;
}
