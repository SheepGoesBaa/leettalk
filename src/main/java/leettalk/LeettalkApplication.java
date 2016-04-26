package leettalk;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LeettalkApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(LeettalkApplication.class, args);
		System.out.println("Hit 'Enter' to terminate");
		System.in.read();
		context.close();
	}
}
