package com.udacity.jwdnd.c1.javawebdev;

import com.udacity.jwdnd.c1.javawebdev.service.ChatMessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavawebdevApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavawebdevApplication.class, args);
	}

//	PREVIOUS LESSON (Bean Creation Order)
	@Bean
	public String message()  {
		System.out.println(" -> Message Bean created");
		return "Hello World";
	}

	@Bean
	public String uppercaseMessage(ChatMessageService service)  {
		System.out.println(" -> UppercaseMessage Bean created");
		return service.messageUpper();
	}

	@Bean
	public String lowercaseMessage(ChatMessageService service)  {
		System.out.println(" -> LowercaseMessage Bean created");
		return service.messageLower();
	}

}
