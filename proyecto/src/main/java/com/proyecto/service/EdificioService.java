package com.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.Edificio;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.service.UsuarioService.ResourceNotFoundException;

@Service
public class EdificioService {
	
	@Autowired
	private EdificioRepository edificioRepository;

	public Edificio insertar(Edificio request) {

		Edificio nuevo = new Edificio();

		nuevo.setNombre(request.getNombre());
		nuevo.setDireccion(request.getDireccion());
		nuevo.setCiudad(request.getCiudad());
		nuevo.setUbicacion(request.getUbicacion());
		nuevo.setAdministrador(request.getAdministrador());
		nuevo.setCelular(request.getCelular());
		nuevo.setFecha(LocalDateTime.now());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(request.getEstado());
		return edificioRepository.save(nuevo);

	}

	public Edificio guardar(Integer id, Edificio request) {
		Optional<Edificio> edificio = edificioRepository.findById(id);

		if (edificio.isPresent()) {
			Edificio nuevo = edificio.get();
			nuevo.setNombre(request.getNombre());
			nuevo.setDireccion(request.getDireccion());
			nuevo.setCiudad(request.getCiudad());
			nuevo.setUbicacion(request.getUbicacion());
			nuevo.setAdministrador(request.getAdministrador());
			nuevo.setCelular(request.getCelular());
			nuevo.setFecha(LocalDateTime.now());
			nuevo.setObservacion(request.getObservacion());
			nuevo.setEstado(request.getEstado());
			return edificioRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<Edificio> listar() {

		return edificioRepository.findAll();

	}

	public Edificio listarUno(Integer id) {
		return edificioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Edificio no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (edificioRepository.existsById(id)) {
			edificioRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
