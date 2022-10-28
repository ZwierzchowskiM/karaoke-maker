package com.karaoke.karaokemaker;

import com.karaoke.karaokemaker.controllers.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KaraokemakerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(KaraokemakerApplication.class);
		MainController messageController = context.getBean(MainController.class);
		messageController.mainLoop();

	}

}
