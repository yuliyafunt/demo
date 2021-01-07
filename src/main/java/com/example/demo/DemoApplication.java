package com.example.demo;

import com.example.demo.excel.DocumentCreator;
import com.example.demo.parser.Header;
import com.google.common.collect.ImmutableList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		DocumentCreator bean = ctx.getBean(DocumentCreator.class);

		Header header = new Header(ImmutableList.of("foo", "bar", "gus"));

		Collection<Collection<Object>> v = new ArrayList<>();
		v.add(ImmutableList.of("val1", "val2", "val3"));
		v.add(ImmutableList.of("val3", "val5", "val6"));
		bean.generate(header, v);
//		Parser parser = ctx.getBean(Parser.class);
//		parser.parse();
	}

}
