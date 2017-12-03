package com.springdeveloper.jdbc.demo;

import java.sql.Types;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class CustomerUpdate extends SqlUpdate{

	private static final String sql = "UPDATE customers SET customer_since = ? WHERE id = ?";

	public CustomerUpdate(DataSource ds) {
		super(ds, sql);
		declareParameter(new SqlParameter("customer_since", Types.DATE));
		declareParameter(new SqlParameter("id", Types.BIGINT));
		compile();
	}

	public int update(Customer customer) {
		Object[] params = new Object[] {customer.getCustomerSince(), customer.getId()};
		return update(params);
	}
}
