package springbootclientesApp.models.service;

import java.util.List;

import springbootclientesApp.models.entity.Cliente;

public interface IClienteService {

	public List <Cliente> listarTodos();
	public void guardar (Cliente cliente);
	public Cliente buscarPorId(Long id);
	public void borrar(Long id);
	
	
}
