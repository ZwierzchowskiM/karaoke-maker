package com.karaoke.karaokemaker;

import com.karaoke.karaokemaker.model.*;
import com.karaoke.karaokemaker.service.SongService;
import com.karaoke.karaokemaker.service.SongGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class KaraokemakerApplication {

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException {

		ConfigurableApplicationContext context = SpringApplication.run(KaraokemakerApplication.class);

		SongGenerator songGenerator = context.getBean(SongGenerator.class);
		SongService songService = context.getBean(SongService.class);


		Song song1 = new Song();
		song1.setName("Pierwszy utwor");

		for (int i = 0; i < 8; i++) {
			Bar bar1 = new Bar();

			ChordDto chord3 = new ChordDto();
			chord3 = new ChordDto("C","MAJ");
			ChordDto chord4 = new ChordDto();
			chord4 =  new ChordDto("D","MAJ");

				if (i%2==0) {
					bar1.addChord(chord3,0);
				} else {
					bar1.addChord(chord4, 0);
				}
			song1.addBar(bar1);
		}


		SongPart part1 = new SongPart(0,"ZW");
		SongPart part2 = new SongPart(4,"REF");

		song1.addPart(part1);
		song1.addPart(part2);

		System.out.println("Budowa utworu - tylko dodane akordy i struktura - to co wpisuje klient");
		song1.getBars().forEach(System.out::println);

		// dodaje znaczniki struktury do taktów
		songService.organizeSong(song1);
		System.out.println("Budowa utworu - uporzadkowana struktura");
		song1.getBars().forEach(System.out::println);

		// dodaje znaczniki do akordow
		songService.setBarAtributes(song1);
		System.out.println("Budowa utworu - uporzadkowana struktura + dodane dlugosci akordow ");
		song1.getBars().forEach(System.out::println);

		System.out.println("Wszystkie akordy w utworze ");
		List<Chord> songChords = songService.readChordsFromDataBase(song1);
		System.out.println(songChords);

		songGenerator.generateSong(song1);

		songService.saveSong(song1);




	}

}
