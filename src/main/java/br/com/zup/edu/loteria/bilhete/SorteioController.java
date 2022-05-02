package br.com.zup.edu.loteria.bilhete;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/sorteios")
public class SorteioController {
	
	private final SorteioRepository sorteioRepository;
	private final BilheteRepository bilheteRepository;
	
	public SorteioController(SorteioRepository sorteioRepository, BilheteRepository bilheteRepository) {
		this.sorteioRepository = sorteioRepository;
		this.bilheteRepository = bilheteRepository;
	}

	@PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid SorteioRequest request, UriComponentsBuilder uriComponentsBuilder){
		
        Sorteio sorteio = request.toModel();

        sorteioRepository.save(sorteio);

        URI location = uriComponentsBuilder.path("/sorteios/{id}")
                .buildAndExpand(sorteio.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
	
	@PostMapping("/{idSorteio}/bilhetes")
    public ResponseEntity<?> cadastrarContato(@RequestBody @Valid BilheteRequest request, @PathVariable("idSorteio") Long idSorteio, UriComponentsBuilder uriComponentsBuilder){
		
		Sorteio sorteio = sorteioRepository.findById(idSorteio).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sorteio não existe"));
		
		String hashDoCelular = TelefoneUtils.hash(request.getCelular());
		
		if(bilheteRepository.existsByHashDoCelularAndNumeroSorteAndSorteioId(hashDoCelular,request.getNumeroSorte(),idSorteio)) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Numero da sorte com esse celular existente para esse sorteio");
		}
		
        Bilhete bilhete = request.toModel();
        sorteio.adicionar(bilhete);
        
        sorteioRepository.flush();
        

        URI location = uriComponentsBuilder.path("/{idSorteio}/bilhetes/{idBilhete}")
                .buildAndExpand(sorteio.getId(),bilhete.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
	
	
	@ExceptionHandler
	public ResponseEntity<?> handleUniqueConstraintErrors(ConstraintViolationException e){
		
		
		Map<String, Object> body = Map.of(
				"message", "entidade já existente no sistema",
				"timestamp", LocalDateTime.now()
		);
		
		return ResponseEntity.unprocessableEntity().body(body);
	}
	
	
}
