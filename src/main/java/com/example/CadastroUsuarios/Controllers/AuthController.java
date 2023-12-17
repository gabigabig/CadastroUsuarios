package com.example.CadastroUsuarios.Controllers;

import java.security.SecureRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CadastroUsuarios.Components.JWTTokenProvider;
import com.example.CadastroUsuarios.Controllers.Payload.Signin;
import com.example.CadastroUsuarios.Controllers.Payload.Signup;
import com.example.CadastroUsuarios.Entities.Usuario;
import com.example.CadastroUsuarios.Services.UsuarioServices;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	private final UsuarioServices usuarioServices;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JWTTokenProvider tokenProvider;

	public AuthController(UsuarioServices usuarioServices, PasswordEncoder passwordEncoder,
			AuthenticationManager authManager, JWTTokenProvider tokenProvider) {
		super();
		this.usuarioServices = usuarioServices;
		this.passwordEncoder = passwordEncoder;
		this.authManager = authManager;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/signin")
	public ResponseEntity<Object> login(@RequestBody Signin signin) {
		try {
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(signin.getEmail(), signin.getSenha()));
			SecurityContextHolder.getContext().setAuthentication(auth);
			String jwt = tokenProvider.generateTokenFromEmail(signin.getEmail());
			return ResponseEntity.status(HttpStatus.OK).body(jwt);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> autoCadastro(@RequestBody Signup signup) {
		try {
			Usuario usuarioNovo = new Usuario();
			usuarioNovo.setNome(signup.getNome());
			usuarioNovo.setEmail(signup.getEmail());
			usuarioNovo.setStatus(true);
			String senha = gerarSenhaAleatoria(10);
			usuarioNovo.setSenha(passwordEncoder.encode(senha));
			usuarioServices.save(usuarioNovo);
			return ResponseEntity.status(HttpStatus.OK).body(senha);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	private String gerarSenhaAleatoria(int tamanho) {
		String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
		SecureRandom random = new SecureRandom();
		StringBuilder senha = new StringBuilder(tamanho);

		for (int i = 0; i < tamanho; i++) {
			int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
			senha.append(CARACTERES_PERMITIDOS.charAt(indice));
		}
		return senha.toString();
	}

}