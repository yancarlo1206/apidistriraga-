package com.proyecto.service;

import java.util.List;
import java.util.Optional;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.repository.UsuarioTipoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.UsuarioTipo;
import com.proyecto.entity.Usuario;

@Service
public class UsuarioService {

	@Autowired
    private  UsuarioRepository usuarioRepository;
    
	@Autowired
	private UsuarioTipoRepository usuarioTipoRepository;
	
	public Usuario insertar(Usuario request) {

		UsuarioTipo usuario_tipo = usuarioTipoRepository.findById(request.getUsuario_tipo().getId())
				.orElseThrow(() -> new RuntimeException( "Tipo de usuario no encontrado con ID: " + request.getUsuario_tipo().getId()));

		request.setUsuario_tipo(usuario_tipo);

		return usuarioRepository.save(request);
	}

	public Usuario guardar(Integer id, Usuario request) {
		Usuario actual = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

		UsuarioTipo usuario_tipo = usuarioTipoRepository.findById(request.getUsuario_tipo().getId())
				.orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado con ID: " + request.getUsuario_tipo().getId()));

		actual.setUsuario_tipo(usuario_tipo);
		actual.setCorreo(request.getCorreo());

		return usuarioRepository.save(actual);

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
