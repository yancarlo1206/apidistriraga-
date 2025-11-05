package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.Apartamento;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.service.UsuarioService.ResourceNotFoundException;

@Service
public class ApartamentoService {
	
	@Autowired
	private ApartamentoRepository apartamentoRepository;
	
	
	public Apartamento insertar(Apartamento request) {

		Apartamento nuevo = new Apartamento();

		nuevo.setEdificio(request.getEdificio());
		nuevo.setNombre(request.getNombre());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(request.getEstado());
		return apartamentoRepository.save(nuevo);

	}

	public Apartamento guardar(Integer id, Apartamento request) {
		Optional<Apartamento> apartamento = apartamentoRepository.findById(id);

		if (apartamento.isPresent()) {
			Apartamento nuevo = apartamento.get();
			nuevo.setEdificio(request.getEdificio());
			nuevo.setNombre(request.getNombre());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setObservacion(request.getObservacion());
			nuevo.setEstado(request.getEstado());
			return apartamentoRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<Apartamento> listar() {

		return apartamentoRepository.findAll();

	}

	public Apartamento listarUno(Integer id) {
		return apartamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Apartamento no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (apartamentoRepository.existsById(id)) {
			apartamentoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
