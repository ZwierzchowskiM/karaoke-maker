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
public class SongGenerator {

    SongService songService;

    public SongGenerator(SongService songService) {
        this.songService = songService;
    }

    String pathFinalSong = "Files\\finalSong.wav";


    AudioInputStream sound1;
    AudioInputStream sound2;
    AudioInputStream appendedFiles;


    public void generateSong(Song song) throws UnsupportedAudioFileException, IOException {

        System.out.println("zapisuje utwor");

        List<Chord> chords = songService.readChordsFromDataBase(song);


        int songLenght = chords.size();

        for (int i = 1; i < songLenght ; i++) {

            if (i == 1) {
                sound1 = pickChord(chords.get(0));
            } else {
                sound1 = pickTempFile(i-1);
            }

            sound2 = pickChord(chords.get(i));

            appendedFiles = new AudioInputStream(new SequenceInputStream(sound1, sound2), sound1.getFormat(), sound1.getFrameLength() + sound2.getFrameLength());

            if (i < songLenght - 1) {
                writeTempFile(i);
            } else {
                writeFinalSong();
            }

            sound1.close();
            sound2.close();
            deleteTempFile(i);

        }

        System.out.println("gotowe");
    }

    private void writeFinalSong() throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(pathFinalSong));
    }

    private void writeTempFile(int i) throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File("Files\\tmpAudio" + i + ".wav"));
    }

    AudioInputStream pickChord(Chord chord) throws UnsupportedAudioFileException, IOException {
//        return AudioSystem.getAudioInputStream(new File(chord.getPath()));
        return AudioSystem.getAudioInputStream(new File(chord.getPath()));
    }

    AudioInputStream pickTempFile (int i) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File("Files\\tmpAudio" + (i) + ".wav"));
    }

    private void deleteTempFile(int i) {
        File f= new File("Files\\tmpAudio" + (i -1) + ".wav");
        f.delete();
    }

}
