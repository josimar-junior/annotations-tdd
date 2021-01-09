package br.com.jj.annotations;

public class JsonException extends RuntimeException {

	private static final long serialVersionUID = -5125495039836995694L;

	public JsonException(String message) {
		super(message);
	}
}
