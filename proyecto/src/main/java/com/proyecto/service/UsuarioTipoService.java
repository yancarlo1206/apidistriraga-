package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.proyecto.entity.UsuarioTipo;
import com.proyecto.repository.UsuarioTipoRepository;
import com.proyecto.service.UsuarioService.ResourceNotFoundException;

@Service
public class UsuarioTipoService {
	
	@Autowired
	private UsuarioTipoRepository usuarioTipoRepository;
	
	public UsuarioTipo insertar(UsuarioTipo request) {

		UsuarioTipo nuevo = new UsuarioTipo();
		nuevo.setNombre(request.getNombre());
		return usuarioTipoRepository.save(nuevo);

	}

	public UsuarioTipo guardar(Integer id, UsuarioTipo request) {
		Optional<UsuarioTipo> usuarioTipo = usuarioTipoRepository.findById(id);

		if (usuarioTipo.isPresent()) {
			UsuarioTipo nuevo = usuarioTipo.get();
			nuevo.setNombre(request.getNombre());
			return usuarioTipoRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<UsuarioTipo> listar() {

		return usuarioTipoRepository.findAll();

	}

	public UsuarioTipo listarUno(Integer id) {
		return usuarioTipoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UsuarioTipo no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (usuarioTipoRepository.existsById(id)) {
			usuarioTipoRepository.deleteById(id);
			return true;
		}
		return false;
	}


	
}
