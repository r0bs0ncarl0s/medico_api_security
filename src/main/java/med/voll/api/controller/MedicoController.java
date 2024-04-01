package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
	
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		//Quando o retorno era void
		//repository.save(new Medico(dados));
		
		//Retorno com ResponseEntity
		var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	@GetMapping
	@RequestMapping("/listarTodos")
	public ResponseEntity<List<DadosListagemMedico>> listar(){
		//Quando o retorno era List
		//return repository.findAll().stream().map(DadosListagemMedico::new).toList();
		
		//Retorno com ResponseEntity
		var resposta =  repository.findAll().stream().map(DadosListagemMedico::new).toList();
		return ResponseEntity.ok(resposta);
		
	}
	
	@GetMapping
	@RequestMapping("/listarAtivos")
	public ResponseEntity<List<DadosListagemMedico>> listarAtivos(){
		//Quando o retorno era List
		//return repository.findAllAtivos();
		
		//Retorno com ResponseEntity
		var resposta = repository.findAllAtivos();
		return ResponseEntity.ok(resposta);		
	}
	
	@GetMapping
	@RequestMapping("/listarInativos")
	public  ResponseEntity<List<DadosListagemMedico>> listarInativos(){
		//Quando o retorno era List
		//return repository.findAllInativos();
		
		//Retorno com ResponseEntity
		var resposta = repository.findAllInativos();
		return ResponseEntity.ok(resposta);		
	}
	
	
	@GetMapping
	@RequestMapping("/listarComPaginacao")
	public ResponseEntity<Page<DadosListagemMedico>> listarComPaginacao(@PageableDefault(size=10, sort= {"nome"}) Pageable paginacao){
		//Exemplo de URL
		//http://localhost:8080/medicos/listarComPaginacao?size=1&page=1
		//http://localhost:8080/medicos/listarComPaginacao?sort=nome,desc
		//http://localhost:8080/medicos/listarComPaginacao?size=1&page=1&sort=nome,desc
		
		//Quando o retorno era Page
		//return repository.findAll(paginacao).map(DadosListagemMedico::new);
		
		//Retorno com ResponseEntity
		var resposta = repository.findAll(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(resposta);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		//Quando o retorno era void
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {
		//Quando o retorno era void
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/inativar/{id}")
	@Transactional
	public ResponseEntity inativar(@PathVariable Long id) {
		var medico = repository.getReferenceById(id);
		medico.inativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/ativar/{id}")
	@Transactional
	public ResponseEntity ativar(@PathVariable Long id) {
		var medico = repository.getReferenceById(id);
		medico.ativar();
		
		return ResponseEntity.noContent().build();
	}
}
