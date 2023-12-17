package com.example.CadastroUsuarios.Controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CadastroUsuarios.Entities.GenericEntity;
import com.example.CadastroUsuarios.Services.GenericService;


public abstract class GenericController<TEntidade extends GenericEntity>{

	abstract GenericService<TEntidade> getService();
	
	@GetMapping
	public ResponseEntity<Object> findAll(@RequestParam String nome,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable)
			throws Exception {
		try {
			Page<TEntidade> lista = getService().findByNomeContainingIgnoreCase(nome, pageable);
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) throws Exception {
		Optional<TEntidade> entidade = getService().findById(id);
		try {
			if (entidade.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nao encontramos registro com o id " + id);
			}
			return ResponseEntity.status(HttpStatus.OK).body(entidade);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody TEntidade entidade) throws Exception {
		try {
			getService().save(entidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(entidade);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable long id, @RequestBody TEntidade entidade) throws Exception {
		try {
			entidade.setId(id);
			getService().save(entidade);
			return ResponseEntity.status(HttpStatus.OK).body(entidade);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable long id) throws Exception {
		try {
			getService().deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("id deletado com sucesso: " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
