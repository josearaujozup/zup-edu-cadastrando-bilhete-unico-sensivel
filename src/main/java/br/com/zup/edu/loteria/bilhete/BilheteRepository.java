package br.com.zup.edu.loteria.bilhete;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BilheteRepository extends JpaRepository<Bilhete, Long>{

	public boolean existsByHashDoCelularAndNumeroSorteAndSorteioId(String hashDoCelular, Integer numeroSorte, Long idSorteio);

}
