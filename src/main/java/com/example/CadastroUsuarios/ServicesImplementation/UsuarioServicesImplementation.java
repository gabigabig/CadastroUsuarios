package com.example.CadastroUsuarios.ServicesImplementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.CadastroUsuarios.Entities.Usuario;
import com.example.CadastroUsuarios.Repositories.UsuarioRepository;
import com.example.CadastroUsuarios.Services.UsuarioServices;

@Service
public class UsuarioServicesImplementation implements UsuarioServices {

	private final UsuarioRepository usuarioRepository;

	public UsuarioServicesImplementation(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UsuarioRepository getRepository() {
		// TODO Auto-generated method stub
		return usuarioRepository;
	}

	@Override
	public void alteraStatus(long id) throws Exception {
		if (!usuarioRepository.existsById(id)) {
			throw new Exception("nao existe usuario com esse id");
		}
		usuarioRepository.alteraStatus(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível localizar o usuário com este e-mail"));
		return usuario;
	}

	
}
