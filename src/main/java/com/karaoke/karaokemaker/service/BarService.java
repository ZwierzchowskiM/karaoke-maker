package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Bar;
import com.karaoke.karaokemaker.repositories.BarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarService {

    BarRepository barRepository;

    public BarService(BarRepository barRepository) {
        this.barRepository = barRepository;
    }


    @Transactional
    public void add(Bar bar){
        barRepository.save(bar);
    }
}
