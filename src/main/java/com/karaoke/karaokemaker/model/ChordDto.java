package com.karaoke.karaokemaker.model;


import javax.persistence.*;

@Entity
@Table(name = "chordsDto")
public class ChordDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleNote;
    private String type;        // maj, moll, dominant7, maj9 itp
    private int lenght;
    private String partName;



    public ChordDto() {
    }

    public ChordDto(String singleNote, String type, int lenght, String partName) {
        this.singleNote = singleNote;
        this.type = type;
        this.lenght = lenght;
        this.partName = partName;
    }

    public ChordDto(String singleNote, String type) {
        this.singleNote = singleNote;
        this.type = type;
    }




    public String getSingleNote() {
        return singleNote;
    }

    public void setSingleNote(String singleNote) {
        this.singleNote = singleNote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }


    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Override
    public String toString() {
        return "ChordDto{" +
                "singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + lenght +
                ", partName='" + partName + '\'' +
                '}';
    }
}
