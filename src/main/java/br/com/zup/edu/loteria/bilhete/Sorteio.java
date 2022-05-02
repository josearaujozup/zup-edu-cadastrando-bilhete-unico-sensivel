package br.com.zup.edu.loteria.bilhete;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sorteio {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, length = 150)
    private String descricao;
	
	@OneToMany(mappedBy = "sorteio", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Set<Bilhete> bilhetes = new HashSet<>();
	
	@Column(nullable = false)
    private LocalDate data;

	public Sorteio(String descricao, LocalDate data) {
		this.descricao = descricao;
		this.data = data;
	}
	
	/**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
	public Sorteio() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void adicionar(Bilhete bilhete) {
		this.bilhetes.add(bilhete);
		bilhete.setSorteio(this);
	}
	
}
