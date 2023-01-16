package com.karaoke.karaokemaker.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @Fetch(FetchMode.JOIN)
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    @OneToMany
    @JoinColumn(name = "song_id")
    private List<Chord> chords = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
//     private Long userId;

    String path;
    UUID uuid;




    public Song(String name) {
        this.name = name;
    }

    public Song(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Song(String name, List<Chord> chords) {
        this.name = name;
        this.chords = chords;

    }


    @Override
    public String toString() {
        return "Song{" +
                "Id=" + Id + "\n" +
                ", name='" + name + "\n" +
                ", chords=" + chords+ "\n" +
                ", path='" + path + "\n" +
                ", uuid=" + uuid+ "\n"  +
                '}';
    }
}
