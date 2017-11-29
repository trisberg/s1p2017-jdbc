package example;

import java.util.Map;
import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FunctionApp {

	public static void main(String[] args) {
		SpringApplication.run(FunctionApp.class, args);
	}

	@Bean
	Function<Map<String, Object>, String> function(DataSource dataSource) {
		return new JdbcWriter(dataSource);
	}
}
