package br.com.jj.model;

import br.com.jj.annotations.Init;
import br.com.jj.annotations.JsonElement;
import br.com.jj.annotations.JsonSerializable;

@JsonSerializable
public class Person {

	@JsonElement
	private String name;
	
	@JsonElement(key = "formattedCpf")
	private String cpf;
	
	@JsonElement
	private String email;
	
	public Person(String name, String cpf, String email) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
	}

	@Init
	private void formatCpf() {
		this.cpf = this.cpf.substring(0, 3) + "." + 
				   this.cpf.substring(3, 6) + "." +
				   this.cpf.substring(6, 9) + "-" +
				   this.cpf.substring(9, 11);
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}
	
}
