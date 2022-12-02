package com.karaoke.karaokemaker;

import com.karaoke.karaokemaker.model.Bar;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import com.karaoke.karaokemaker.repositories.UserRepository;
import com.karaoke.karaokemaker.service.ChordService;
import com.karaoke.karaokemaker.service.SongService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KaraokemakerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(KaraokemakerApplication.class);
		SongService songService = context.getBean(SongService.class);
		songService.createSong();
		System.out.println("Zapisano w bazie song");

		ChordService chordService = context.getBean(ChordService.class);
		Chord chord1 = new Chord("C","MAJ");
		Chord chord2 = new Chord("D","MOLL");
		Chord chord3 = new Chord("C","DOMINANT7");
		chordService.add(chord1);
		chordService.add(chord2);
		chordService.add(chord3);
		Chord chord4 = new Chord("C","MAJ");
		Chord chord5 = new Chord("D","MOLL");
		Chord chord6 = new Chord("C","DOMINANT7");

		ChordRepository chordRepository = context.getBean(ChordRepository.class);
		chordRepository.save(chord4);
		chordRepository.save(chord5);
		chordRepository.save(chord6);

		chordService.findChordsByNote("F").forEach(System.out::println);


		Song song1 = new Song("Dmuchawce latawce");
		Song song2 = new Song("Konik na biegunach");
		Song song3 = new Song("Winda do nieba");
		songService.saveSong(song1);
		songService.saveSong(song2);
		songService.saveSong(song3);



		Bar bar1 = new Bar();
		Bar bar2 = new Bar();


		bar1.addChord(chord1);
		bar1.addChord(chord1);
		bar1.addChord(chord4);
		bar2.addChord(chord6);
		bar2.addChord(chord4);


		song1.addBar(bar1);
		song1.addBar(bar2);




	}

}
