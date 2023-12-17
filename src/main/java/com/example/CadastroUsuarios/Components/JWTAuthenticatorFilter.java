package com.example.CadastroUsuarios.Components;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.CadastroUsuarios.Entities.Usuario;
import com.example.CadastroUsuarios.Services.UsuarioServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticatorFilter extends OncePerRequestFilter{

	private final JWTTokenProvider tokenProvider;
	
	private final UsuarioServices usuarioServices;
	
	public JWTAuthenticatorFilter(JWTTokenProvider tokenProvider, UsuarioServices usuarioServices) {
		super();
		this.tokenProvider = tokenProvider;
		this.usuarioServices = usuarioServices;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String bearer = tokenProvider.getBearerFromRequest(request);
			if (bearer != null && tokenProvider.validaJwtToken(bearer)) {
				String emailUsuario = tokenProvider.getEmailFromJwtToken(bearer);
				Usuario usuario = (Usuario) usuarioServices.loadUserByUsername(emailUsuario);
				if (usuario.getStatus()) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							usuario, null, usuario.getAuthorities());
					
					//Não é obrigatória para autenticação
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			}
		} catch (Exception e) {

		}

		filterChain.doFilter(request, response);
		
	}

}
