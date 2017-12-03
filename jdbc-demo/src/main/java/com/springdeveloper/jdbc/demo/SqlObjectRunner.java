package com.springdeveloper.jdbc.demo;

import java.time.LocalDate;
import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class SqlObjectRunner implements CommandLineRunner {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private CustomerUpdate customerUpdate;

	private CustomerDeleteProc customerDeleteProc;

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.customerUpdate = new CustomerUpdate(dataSource);
		this.customerDeleteProc = new CustomerDeleteProc(dataSource);
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("\nUsing a SqlUpdate object to update a record ...");
		Customer customer = new Customer("Colin", "Exis RaD", java.sql.Date.valueOf(LocalDate.parse("2001-01-01")));
		Object[] parameters = new Object[] {};
		BeanPropertySqlParameterSource values = new BeanPropertySqlParameterSource(customer);
		namedParameterJdbcTemplate.update(
				"INSERT INTO customers (name, company_name, customer_since) VALUES(:name, :companyName, :customerSince)",
				values);

		RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
		Customer added = namedParameterJdbcTemplate
				.queryForObject("SELECT * FROM customers where name = :name",
						Collections.singletonMap("name", customer.getName()), rowMapper);
		assert added != null;
		System.out.println("Added customer: " + added);

		added.setCustomerSince(java.sql.Date.valueOf(LocalDate.parse("2003-07-01")));
		int updateCount = customerUpdate.update(added);
		System.out.println("Updated  " + updateCount + " row(s)");

		Customer updated = namedParameterJdbcTemplate
				.queryForObject("SELECT * FROM customers where name = :name",
						Collections.singletonMap("name", customer.getName()), rowMapper);
		System.out.println("Updated customer: " + updated);
		DemoApplication.prettyPrint(namedParameterJdbcTemplate.getJdbcOperations()
				.queryForList("SELECT * FROM customers"));

		System.out.println("\nUsing a StoredProcedure object to delete a record ...");
		int deleteCount = customerDeleteProc.execute(updated);
		System.out.println("Deleted  " + deleteCount + " row(s)");
		DemoApplication.prettyPrint(namedParameterJdbcTemplate.getJdbcOperations()
				.queryForList("SELECT * FROM customers"));
	}
}
