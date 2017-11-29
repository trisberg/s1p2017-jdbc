package example;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:test"})
public class FunctionAppTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	JdbcWriter writer;

 	@Test
	public void test() {
 		Map<String, Object> data = new HashMap<>();
 		data.put("name", "Bob");
 		data.put("description", "testing");
		assertTrue(writer.apply(data).contains("\"newId\":"));
		assertEquals(1, (int)jdbcTemplate.queryForObject(
				"select count(*) from data where name='Bob' and description='testing'", Integer.class));
	}
}
