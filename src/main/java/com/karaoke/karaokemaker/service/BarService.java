package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Bar;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.ChordDto;
import com.karaoke.karaokemaker.repositories.BarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BarService {

    BarRepository barRepository;
    ChordService chordService;

    public BarService(BarRepository barRepository, ChordService chordService) {
        this.barRepository = barRepository;
        this.chordService = chordService;
    }

    @Transactional
    public void add(Bar bar){
        barRepository.save(bar);
    }




    public void setRequiredChordsAtributes(Bar bar) {


        ChordDto chordDto = new ChordDto();
        String partName = bar.getPartName();
        int requiredChordLenght = 0;
        int chordPosition =0;

        ChordDto[] barChords = bar.getChords();


        for (int i = 0; i < barChords.length; i++) {

            // jest akord w tablicy
            if (barChords[i] != null) {
                chordDto = barChords[i];
                requiredChordLenght=1;
                chordPosition =i;
            }

            // nie ma akordu w tablicy, zwiekszamy wymagana dlugosc
            if (barChords[i] == null) {
                requiredChordLenght++;
            }

            //ostatni przebieg petli wiec zapisujemy akord na jego starym miejscu
            if (i==barChords.length-1) {
                chordDto.setLenght(requiredChordLenght);
                chordDto.setPartName(partName);
                barChords[chordPosition] = chordDto;
                //requiredChords.add(chordDto);
            }

            // nie jestesmy w ostatnim przebiegu i nastepny nie jest pusty - zapisujemy akord
            if ((i<barChords.length-1) && (barChords[i+1] != null)) {
                chordDto.setLenght(requiredChordLenght);
                chordDto.setPartName(partName);
                barChords[chordPosition] = chordDto;
                //requiredChords.add(chordDto);
            }

        }

    }



    public List<Chord> readChordsInBar(Bar bar){

        ChordDto[] barChords = bar.getChords();
        List<ChordDto> chordsDtoInBar = Arrays.stream(barChords).filter(Objects::nonNull).toList();

        List<Chord> chords =  chordService.readFromDataBaseChord(chordsDtoInBar);
        return chords;

    }


}
