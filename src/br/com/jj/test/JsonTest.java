package br.com.jj.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

import br.com.jj.annotations.JsonConverter;
import br.com.jj.annotations.JsonException;
import br.com.jj.model.Person;

public class JsonTest {
	
	private JsonConverter converter;
	
	@Before
	public void setup() {
		this.converter = new JsonConverter();
	}

	@Test
	public void mustConvertTheObjectToJson() {
		Person person = new Person("Maria José", "12345678912", "maria@gmail.com");
		String jsonString = this.converter.convert(person);
		assertThat(jsonString, is("{\"formattedCpf\":\"123.456.789-12\",\"name\":\"Maria José\",\"email\":\"maria@gmail.com\"}"));
	}
	
	@Test
	public void mustThrowExceptionObjectNotAnnotated() {
		Object object = new Object();
		Exception e = assertThrows(JsonException.class, () -> this.converter.convert(object));
		assertThat(e.getMessage(), is("The class java.lang.Object is not annotated with JsonSerializable"));
	}
	
	@Test
	public void mustThrowExceptionNullObject() {
		Object object = null;
		Exception e = assertThrows(JsonException.class, () -> this.converter.convert(object));
		assertThat(e.getMessage(), is("The object cannot be null"));
	}
}
