package com.karaoke.karaokemaker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SongPart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    int barNumber;
    String partName;

    public SongPart(int barNumber, String partName) {
        this.barNumber = barNumber;
        this.partName = partName;
    }

    public int getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(int barNumber) {
        this.barNumber = barNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
}
