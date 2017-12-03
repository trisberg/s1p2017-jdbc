package com.springdeveloper.jdbc.demo;

import java.sql.Types;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CustomerDeleteProc  extends StoredProcedure {

	private static final String procName = "delete_customer";

	public CustomerDeleteProc(DataSource ds) {
		super(ds, procName);
		declareParameter(new SqlParameter("in_id", Types.BIGINT));
		compile();
	}

	public int execute(Customer cust) {
		Map<String, Object> results = execute(cust.getId());
		Object count = results.get("#update-count-1");
		if (count instanceof Number) {
			return ((Number)count).intValue();
		}
		else {
			return 0;
		}
	}
}
