package com.example.CadastroUsuarios.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CadastroUsuarios.Entities.Departamento;
import com.example.CadastroUsuarios.Services.DepartamentoServices;
import com.example.CadastroUsuarios.Services.GenericService;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/departamentos")
public class DepartamentoController extends GenericController<Departamento>{

	final DepartamentoServices departamentoServices;

	public DepartamentoController(DepartamentoServices departamentoServices) {
		super();
		this.departamentoServices = departamentoServices;
	}

	@Override
	GenericService<Departamento> getService() {
		return departamentoServices;
	}

	
}
