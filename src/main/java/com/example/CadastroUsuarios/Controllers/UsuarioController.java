package com.example.CadastroUsuarios.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CadastroUsuarios.Entities.Usuario;
import com.example.CadastroUsuarios.Services.GenericService;
import com.example.CadastroUsuarios.Services.UsuarioServices;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/usuarios")
public class UsuarioController extends GenericController<Usuario>{

	final UsuarioServices usuarioServices;
	
	public UsuarioController(UsuarioServices usuarioServices) {
		super();
		this.usuarioServices = usuarioServices;
	}


	@PatchMapping("/alterastatus/{id}")
	public ResponseEntity<Object> alteraStatus(@PathVariable long id) throws Exception {
		try {
			usuarioServices.alteraStatus(id);
			return ResponseEntity.status(HttpStatus.OK).body("o status do usuario com id " + id + "foi alterado");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@Override
	GenericService<Usuario> getService() {
		// TODO Auto-generated method stub
		return usuarioServices;
	}
}
