package com.karaoke.karaokemaker.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @ManyToMany()
    @JoinTable(name = "songs_chords",
            joinColumns = @JoinColumn(name = "song", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chord", referencedColumnName = "id")
    )
    private List<Chord> chords = new ArrayList<>();
    private Long userId;
    private String path;
    private UUID uuid;



}
