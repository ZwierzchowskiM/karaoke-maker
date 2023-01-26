package com.karaoke.karaokemaker.model;

import com.karaoke.karaokemaker.enums.ChordType;
import com.karaoke.karaokemaker.enums.Complexity;
import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.enums.SingleNote;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Table(name = "chords")
@Getter
@Setter
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SingleNote singleNote;
    @Enumerated(EnumType.STRING)
    private ChordType type;
    @Enumerated(EnumType.STRING)
    private Length length;
    @Enumerated(EnumType.STRING)
    private Complexity complexity;
    private String path;

    public Chord(Long id, SingleNote singleNote, ChordType type, Length length, Complexity complexity, String path) {
        this.id = id;
        this.singleNote = singleNote;
        this.type = type;
        this.length = length;
        this.complexity = complexity;
        this.path = path;
    }

    public Chord() {
    }
}
