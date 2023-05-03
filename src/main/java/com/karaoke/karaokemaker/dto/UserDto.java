package com.karaoke.karaokemaker.dto;

import com.karaoke.karaokemaker.model.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private List<Song> songs = new ArrayList<>();
    private String role;

}
