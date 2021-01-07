package com.example.demo;

import com.example.demo.test.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		Parser parser = ctx.getBean(Parser.class);
		parser.parse();
	}

}
