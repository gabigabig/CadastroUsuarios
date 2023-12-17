package com.example.CadastroUsuarios.ServicesImplementation;

import org.springframework.stereotype.Service;
import com.example.CadastroUsuarios.Repositories.DepartamentoRepository;
import com.example.CadastroUsuarios.Services.DepartamentoServices;

@Service
public class DepartamentoServicesImplementation implements DepartamentoServices {

	private final DepartamentoRepository departamentoRepository;

	public DepartamentoServicesImplementation(DepartamentoRepository departamentoRepository) {
		super();
		this.departamentoRepository = departamentoRepository;
	}

	@Override
	public DepartamentoRepository getRepository() {
		// TODO Auto-generated method stub
		return departamentoRepository;
	}

}
