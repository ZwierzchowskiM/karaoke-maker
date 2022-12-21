package com.karaoke.karaokemaker.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SongPreviewController {


    @GetMapping("/song/compose/preview")
    public String preview(Model model) {
        return "songPreview";
    }

}
