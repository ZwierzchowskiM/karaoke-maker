package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SongWriterText implements Writer {
    @Override
    public String writeSong(Song song, String directory) {

        String pathFinalSong = directory + "\\" + song.getName() + ".txt";
        song.setPathTextFile(pathFinalSong);
        List<Chord> chords = song.getChords();

        BufferedWriter bw = null;

        StringBuilder content = new StringBuilder();

        for (int i = 0; i < chords.size(); i++) {

            content.append(chords.get(i).toString());
            content.append(System.lineSeparator());

        }
        String finalContent = content.toString();

        try {
            File file = new File(pathFinalSong);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(finalContent);
            System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }
        return pathFinalSong;
    }
}

