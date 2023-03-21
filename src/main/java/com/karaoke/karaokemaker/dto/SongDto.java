package com.karaoke.karaokemaker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SongDto  {

    private Long id;
    private String name;

}
