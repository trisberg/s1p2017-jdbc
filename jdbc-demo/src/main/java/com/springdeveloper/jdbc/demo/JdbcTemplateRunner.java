package com.springdeveloper.jdbc.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JdbcTemplateRunner implements CommandLineRunner {

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("\nUsing a JdbcTemplate to update and query ...");
		Object[] parameters = new Object[] {"Rod", "Atomist", LocalDate.parse("2002-11-01").atStartOfDay()};
		jdbcTemplate.update(
				"INSERT INTO customers (name, company_name, customer_since) VALUES(?, ?, ?)",
				parameters);
		DemoApplication.prettyPrint(jdbcTemplate.queryForList("SELECT * FROM customers"));

		System.out.println("\nUsing a NamedParameterJdbcTemplate to update and query ...");
		SqlParameterSource parameterSource = new MapSqlParameterSource("name", "Juergen")
				.addValue("companyName", "Pivotal")
				.addValue("customerSince", LocalDate.parse("2003-01-01").atStartOfDay());
		namedParameterJdbcTemplate.update(
				"INSERT INTO customers (name, company_name, customer_since) " +
						"VALUES(:name, :companyName, :customerSince)",
				parameterSource);
		DemoApplication.prettyPrint(namedParameterJdbcTemplate.getJdbcOperations()
				.queryForList("SELECT * FROM customers"));

	}
}
