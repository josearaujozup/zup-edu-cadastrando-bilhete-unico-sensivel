package br.com.zup.edu.loteria.bilhete;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SorteioRequest {
	
	@NotBlank
    private String descricao;
	
	@Future
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull
    private LocalDate data;

	public SorteioRequest(@NotBlank String descricao, @Future @NotNull LocalDate data) {
		this.descricao = descricao;
		this.data = data;
	}
	
	public SorteioRequest() {
		
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public Sorteio toModel() {
		return new Sorteio(descricao, data);
	}
	
	
	
}
