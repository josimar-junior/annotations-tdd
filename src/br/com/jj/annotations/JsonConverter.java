package br.com.jj.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonConverter {

	public String convert(Object object) throws JsonException {
		try {
			validateObject(object);
			initObject(object);
			return getJson(object);
		} catch (Exception e) {
			throw new JsonException(e.getMessage());
		}
	}
	
	private void validateObject(Object object) {
		if(Objects.isNull(object)) {
			throw new JsonException("The object cannot be null");
		}
		
		Class<?> clazz = object.getClass();
		if(!clazz.isAnnotationPresent(JsonSerializable.class)) {
			throw new JsonException("The class " + clazz.getName() + " is not annotated with JsonSerializable");
		}
	}
	
	private void initObject(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = object.getClass();
		for(Method method : clazz.getDeclaredMethods()) {
			if(method.isAnnotationPresent(Init.class)) {
				method.setAccessible(true);
				method.invoke(object);
			}
		}
	}
	
	private String getJson(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Map<String, String> jsonMap = new HashMap<>();
		for(Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(JsonElement.class)) {
				jsonMap.put(getKey(field), (String) field.get(object));
			}
		}
		String jsonString = jsonMap.entrySet()
				.stream()
				.map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
				.collect(Collectors.joining(","));
		return "{" + jsonString + "}";
	}
	
	private String getKey(Field field) {
		String value = field.getAnnotation(JsonElement.class).key();
		return value.isEmpty() ? field.getName() : value;
	}
}
