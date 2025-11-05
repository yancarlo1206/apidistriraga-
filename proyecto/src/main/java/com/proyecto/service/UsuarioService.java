package com.proyecto.service;

import java.util.List;
import java.util.Optional;
import com.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.proyecto.entity.Usuario;

@Service
public class UsuarioService {

	@Autowired
    private  UsuarioRepository usuarioRepository;

	
	public Usuario insertar(Usuario request) {

		Usuario nuevo = new Usuario();

		nuevo.setCorreo(request.getCorreo());
		nuevo.setContrasena(request.getContrasena());
		nuevo.setTipo(request.getTipo());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(request.getEstado());
		return usuarioRepository.save(nuevo);

	}

	public Usuario guardar(Integer id, Usuario request) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isPresent()) {
			Usuario nuevo = usuario.get();
			nuevo.setCorreo(request.getCorreo());
			nuevo.setContrasena(request.getContrasena());
			nuevo.setTipo(request.getTipo());
			nuevo.setObservacion(request.getObservacion());
			nuevo.setEstado(request.getEstado());
			return usuarioRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<Usuario> listar() {

		return usuarioRepository.findAll();

	}

	public Usuario listarUno(Integer id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
