package com.karaoke.karaokemaker.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SongDto  {


    private Long id;
    private String name;
    private UUID uuid;




}
