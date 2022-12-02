package com.karaoke.karaokemaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Scanner;

@Configuration
    class AppConfig {

        @Bean
        Scanner scanner() {
            return new Scanner(System.in);
        }




}

