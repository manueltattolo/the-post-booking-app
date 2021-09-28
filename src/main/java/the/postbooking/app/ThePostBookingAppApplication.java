package the.postbooking.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"the.postbooking.app.controller", "the.postbooking.app.service"})
@EntityScan("the.postbooking.app.entity")
@EnableJpaRepositories("the.postbooking.app.repository")
public class ThePostBookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThePostBookingAppApplication.class, args);
	}

}
