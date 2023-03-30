package com.karaoke.karaokemaker.model;

import com.karaoke.karaokemaker.enums.*;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "chords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Enumerated(EnumType.STRING)
    private BassNote bassNote;
    private String path;

    @Override
    public String toString() {
        return "Chord: " + singleNote + type + " bottom note: " + bassNote +
                " length: " + length + " complexity: " + complexity;
    }

}
