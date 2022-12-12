package com.karaoke.karaokemaker.bar.model;


import com.karaoke.karaokemaker.model.ChordDto;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "bar_id")
    @OrderColumn
    private ChordDto[] chords =  new ChordDto[4];

    private String partName;





    public Bar() {
    }

    public void addChord(ChordDto chord, int number) {

        chords[number] = chord;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public ChordDto[] getChords() {
        return chords;
    }

    public void setChords(ChordDto[] chords) {
        this.chords = chords;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "chords=" + Arrays.toString(chords) +
                ", partName='" + partName + '\'' +
                '}';
    }
}
