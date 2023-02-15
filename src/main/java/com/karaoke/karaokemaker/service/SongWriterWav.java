package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.exceptions.AudioFileNotFoundException;
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
public class SongWriterWav implements Writer {

    private static final int FIRST_SONG_BAR = 0;
    private static final String TEMP_FILE_PATH = "Files\\tmpAudio";
    AudioInputStream firstSongFragment=null;
    AudioInputStream secondSongFragment=null;
    AudioInputStream appendedSongFragments;


    public String writeSong(Song song, String directory) throws UnsupportedAudioFileException, IOException, AudioFileNotFoundException {


        String pathFinalSong= directory + "\\" + song.getName() + ".wav";
        song.setPathWavFile(pathFinalSong);
        List<Chord> chords = song.getChords();
        int numberOfSongFragments = chords.size();

        for (int actualFragment = 0; actualFragment < numberOfSongFragments; actualFragment++) {
            writeAppendedSongFragments(actualFragment,chords);
            writeFile(actualFragment, numberOfSongFragments, pathFinalSong);
            closeActiveSongFragments (firstSongFragment, secondSongFragment);
            deleteTempFile(actualFragment);
        }

        return pathFinalSong;
    }

    private void deleteTempFile(int actualFragment) {
        if (actualFragment > 0) {
            deleteTempSongFragment(actualFragment-1);
        }
    }

    private void writeFile(int actualFragment, int numberOfSongFragments, String pathFinalSong) throws IOException {
        if (actualFragment < numberOfSongFragments - 1) {
            writeTempSongFragment(appendedSongFragments, actualFragment);
        } else {
            writeFinalSong(appendedSongFragments, pathFinalSong);
        }
    }

    private void writeAppendedSongFragments(int actualFragment, List<Chord> chords ) throws UnsupportedAudioFileException, IOException {


        if (actualFragment == FIRST_SONG_BAR) {
            firstSongFragment = getSingleChord(chords.get(FIRST_SONG_BAR));
            appendedSongFragments = new AudioInputStream(firstSongFragment, firstSongFragment.getFormat(),
                    firstSongFragment.getFrameLength());
        } else {
            firstSongFragment = pickTempSongFragment(actualFragment - 1);
            secondSongFragment = getSingleChord(chords.get(actualFragment));
            appendedSongFragments = new AudioInputStream(new SequenceInputStream(firstSongFragment, secondSongFragment),
                    firstSongFragment.getFormat(), firstSongFragment.getFrameLength() + secondSongFragment.getFrameLength());
        }

    }

    private void closeActiveSongFragments(AudioInputStream firstSongFragment, AudioInputStream secondSongFragment) throws IOException {
        if (firstSongFragment!=null) firstSongFragment.close();
        if (secondSongFragment!=null) secondSongFragment.close();
    }

    private void writeFinalSong(AudioInputStream appendedSongFragments, String pathFinalSong) throws IOException {
        AudioSystem.write(appendedSongFragments, AudioFileFormat.Type.WAVE, new File(pathFinalSong));
    }

    private void writeTempSongFragment(AudioInputStream appendedSongFragments, int i) throws IOException {
        AudioSystem.write(appendedSongFragments, AudioFileFormat.Type.WAVE, new File(TEMP_FILE_PATH + i + ".wav"));
    }

    AudioInputStream getSingleChord(Chord chord) throws AudioFileNotFoundException   {

        try {
            return AudioSystem.getAudioInputStream(new File(chord.getPath()));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new AudioFileNotFoundException(chord.getSingleNote(),chord.getType(),chord.getComplexity(),chord.getLength());
        }

    }

    AudioInputStream pickTempSongFragment(int i) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File(TEMP_FILE_PATH + (i) + ".wav"));
    }

    private void deleteTempSongFragment(int i) {
        File f = new File(TEMP_FILE_PATH + (i) + ".wav");
        f.delete();
    }



}
