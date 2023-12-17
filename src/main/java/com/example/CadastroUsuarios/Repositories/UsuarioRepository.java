package com.example.CadastroUsuarios.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.CadastroUsuarios.Entities.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepository extends GenericRepository<Usuario> {

	@Query(value = "update usuario set status = not status where id = :id ", nativeQuery = true)
	@Modifying
	@Transactional
	void alteraStatus(long id);

	boolean existsByEmailAndIdNot(String email, long id);

	Optional<Usuario> findByEmail(String email);
}
