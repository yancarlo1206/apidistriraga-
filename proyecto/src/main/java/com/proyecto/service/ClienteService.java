package com.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;


import com.proyecto.entity.Cliente;
import com.proyecto.repository.ClienteRepository;
import com.proyecto.service.UsuarioService.ResourceNotFoundException;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	
	public Cliente insertar(Cliente request) {

		Cliente nuevo = new Cliente();

		
		nuevo.setNombre(request.getNombre());
		nuevo.setCorreo(request.getCorreo());
		nuevo.setCelular(request.getCelular());
		nuevo.setFecha(LocalDateTime.now());
		nuevo.setEstado(request.getEstado());
		return clienteRepository.save(nuevo);

	}

	public Cliente guardar(Integer id, Cliente request) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			Cliente nuevo = cliente.get();
			nuevo.setNombre(request.getNombre());
			nuevo.setCorreo(request.getCorreo());
			nuevo.setCelular(request.getCelular());
			nuevo.setFecha(LocalDateTime.now());
			nuevo.setEstado(request.getEstado());
			return clienteRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<Cliente> listar() {

		return clienteRepository.findAll();

	}

	public Cliente listarUno(Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (clienteRepository.existsById(id)) {
			clienteRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
