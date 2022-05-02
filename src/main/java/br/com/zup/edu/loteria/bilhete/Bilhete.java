package br.com.zup.edu.loteria.bilhete;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UK_celular_numeroSorte_sorteio", columnNames = {"hashDoCelular", "numeroSorte","sorteio_id"})
})
@Entity
public class Bilhete {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private String nome;
	
	@Column(nullable = false)
    private String celular;
	
	@Column(nullable = false, length = 64)
    private String hashDoCelular;
	
	@ManyToOne(optional = false)
	private Sorteio sorteio;
	
	@Column(nullable = false)
    private Integer numeroSorte;
	
	@Column(nullable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

	public Bilhete(String nome, String celular, Integer numeroSorte) {
		this.nome = nome;
		this.celular = TelefoneUtils.anonymize(celular);
		this.hashDoCelular = TelefoneUtils.hash(celular);
		this.numeroSorte = numeroSorte;
	}
	
	/**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
	public Bilhete() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
	}
	
	
}
