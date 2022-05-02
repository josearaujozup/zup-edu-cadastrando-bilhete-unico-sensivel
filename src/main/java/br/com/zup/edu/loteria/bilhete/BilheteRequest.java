package br.com.zup.edu.loteria.bilhete;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BilheteRequest {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$")
    private String celular;
	
	
	@NotNull
	@Min(1)
	@Max(9999)
    private Integer numeroSorte;


	public BilheteRequest(@NotBlank String nome, @NotBlank @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}$") String celular,
			@NotNull @Min(1) @Max(9999) Integer numeroSorte) {
		this.nome = nome;
		this.celular = celular;
		this.numeroSorte = numeroSorte;
	}
	
	public BilheteRequest() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getCelular() {
		return celular;
	}

	public Integer getNumeroSorte() {
		return numeroSorte;
	}

	public Bilhete toModel() {
		return new Bilhete(nome, celular, numeroSorte);
	}
	
}
