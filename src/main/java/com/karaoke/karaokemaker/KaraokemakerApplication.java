package com.karaoke.karaokemaker;

import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@SpringBootApplication
public class KaraokemakerApplication {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {

		ConfigurableApplicationContext context = SpringApplication.run(KaraokemakerApplication.class);

		SongService songService = context.getBean(SongService.class);
		ChordRepository chordRepository = context.getBean(ChordRepository.class);
		Song song1 = new Song("dmuchawce");
		songService.saveSong(song1);

		System.out.println(chordRepository.findAll());

	}

}
