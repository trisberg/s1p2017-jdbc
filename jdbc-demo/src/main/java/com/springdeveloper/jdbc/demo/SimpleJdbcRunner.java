package com.springdeveloper.jdbc.demo;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class SimpleJdbcRunner implements CommandLineRunner {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insert;

	private SimpleJdbcCall customerDeleteProc;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

		this.insert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("customers")
				.usingColumns("name", "company_name", "customer_since")
				.usingGeneratedKeyColumns("id");

		this.customerDeleteProc = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("delete_customer");
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("\nUsing a SimpleJdbcInsert to insert data ...");
		SqlParameterSource parameterSource = new MapSqlParameterSource("name", "Rob")
				.addValue("companyName", "Skipjaq")
				.addValue("customerSince", LocalDate.parse("2005-03-01").atStartOfDay());
		this.insert.execute(parameterSource);
		DemoApplication.prettyPrint(this.jdbcTemplate.queryForList("SELECT * FROM customers"));

		System.out.println("\nUsing a SimpleJdbcCall to delete data ...");
		Long id = this.jdbcTemplate.queryForObject("SELECT id FROM customers WHERE name = 'Thomas'", Long.class);
		Map<String, Object> results = this.customerDeleteProc.execute(id);
		System.out.println("Deleted " + results.get("#update-count-1") + " row(s).");
		DemoApplication.prettyPrint(this.jdbcTemplate.queryForList("SELECT * FROM customers"));

	}
}
