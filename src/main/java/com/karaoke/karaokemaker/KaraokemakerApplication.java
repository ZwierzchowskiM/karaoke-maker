package com.karaoke.karaokemaker;

import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.UUID;

@SpringBootApplication
public class KaraokemakerApplication {

	public static void main(String[] args)  {

		ConfigurableApplicationContext context = SpringApplication.run(KaraokemakerApplication.class);

	}

}
