package com.springdeveloper.jdbc.demo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void init() {
		this.jdbcTemplate.execute(
				"CREATE PROCEDURE delete_customer(IN in_id BIGINT)" +
						" BEGIN" +
						"   DELETE FROM customers WHERE id = in_id;" +
						" END");
	}

	@Bean
	JdbcTemplateRunner customerRunner() {
		return new JdbcTemplateRunner();
	}

	@Bean
	SimpleJdbcRunner simpleJdbcRunner() {
		return new SimpleJdbcRunner();
	}

	@Bean
	SqlObjectRunner sqlObjectRunner() {
		return new SqlObjectRunner();
	}

	static void prettyPrint(List<Map<String, Object>> input) {
		input.forEach(System.out::println);
	}
}
