package com.example.CadastroUsuarios.Services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.CadastroUsuarios.Entities.GenericEntity;
import com.example.CadastroUsuarios.Repositories.GenericRepository;

public interface GenericService<TEntidade extends GenericEntity> {
	
	GenericRepository<TEntidade> getRepository();


	default TEntidade save(TEntidade entidade) throws Exception {
		if (this.getRepository().existsByNomeAndIdNot(entidade.getNome(), entidade.getId())) {
			throw new Exception("Ja existe usuario com este nome");
		}
		return this.getRepository().save(entidade);
	}
	
	default Optional<TEntidade> findById(Long id) throws Exception {
		return this.getRepository().findById(id);
	}

	default void deleteById(Long id) throws Exception {
		if (!this.getRepository().existsById(id)) {
			throw new Exception("nao existe usuario com esse id");
		}
		this.getRepository().deleteById(id);
	}

	default Page<TEntidade> findByNomeContainingIgnoreCase(String nome, Pageable pageable) throws Exception {
		return this.getRepository().findByNomeContainingIgnoreCase(nome, pageable);
	}

}
