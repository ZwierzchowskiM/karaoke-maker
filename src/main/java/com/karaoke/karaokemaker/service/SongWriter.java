package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.List;

@Service
public class SongWriter {

    AudioInputStream sound1;
    AudioInputStream sound2;
    AudioInputStream appendedFiles;


    public String writeSong(Song song) throws UnsupportedAudioFileException, IOException {

        System.out.println("zapisuje utwor");
        String pathFinalSong = "Files\\" + song.getName() + ".wav";

        List<Chord> chords = song.getChords();

        for (int i = 1; i < chords.size(); i++) {

            if (i == 1) {
                sound1 = pickChordSound(chords.get(0));
            } else {
                sound1 = pickTempSoundFile(i - 1);
            }

            sound2 = pickChordSound(chords.get(i));
            appendedFiles = new AudioInputStream(new SequenceInputStream(sound1, sound2), sound1.getFormat(), sound1.getFrameLength() + sound2.getFrameLength());

            if (i < chords.size() - 1) {
                writeTempFile(i);
            } else {
                writeFinalSong(pathFinalSong);
            }

            sound1.close();
            sound2.close();
            deleteTempFile(i);
        }

        System.out.println("gotowe");
        return pathFinalSong;

    }


    private void writeFinalSong(String pathFinalSong) throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(pathFinalSong));
    }

    private void writeTempFile(int i) throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File("Files\\tmpAudio" + i + ".wav"));
    }

    AudioInputStream pickChordSound(Chord chord) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File(chord.getPath()));
    }

    AudioInputStream pickTempSoundFile(int i) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File("Files\\tmpAudio" + (i) + ".wav"));
    }

    private void deleteTempFile(int i) {
        File f = new File("Files\\tmpAudio" + (i - 1) + ".wav");
        f.delete();
    }


}
