package example;

import java.util.Map;
import java.util.function.Function;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcWriter implements Function<Map<String, Object>, String> {

	private static Log logger = LogFactory.getLog(JdbcWriter.class);

	private SimpleJdbcInsert insert;

	public JdbcWriter(DataSource dataSource) {
		this.insert = new SimpleJdbcInsert(dataSource)
				.withTableName("data")
				.usingColumns("name", "description")
				.usingGeneratedKeyColumns("id");
	}

	@Override
	public String apply(Map<String, Object> data) {
		logger.info("Received: " + data);
		Object name = data.get("name");
		Object description = data.get("description");
		logger.info("Inserting into data table: [" + name + ", " + description +"]");
		SqlParameterSource input = new MapSqlParameterSource("name", name).addValue("description", description);
		Number newId = insert.executeAndReturnKey(input);
		logger.info("NewId is: " + newId);
		return "{ \"newId\": " + newId + " }";
	}
}
