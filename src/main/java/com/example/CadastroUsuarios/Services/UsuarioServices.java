package com.example.CadastroUsuarios.Services;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.CadastroUsuarios.Entities.Usuario;

public interface UsuarioServices extends GenericService<Usuario>, UserDetailsService{

	void alteraStatus(long id) throws Exception;
	
	
}
