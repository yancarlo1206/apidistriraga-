package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Edificio;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.service.UsuarioService.ResourceNotFoundException;

@Service
public class ApartamentoService {
	
	@Autowired
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired
	private EdificioRepository edificioRepository;
	
	
	public Apartamento insertar(Apartamento request) {
		
		Edificio edificio = edificioRepository.findById(request.getEdificio().getId()).orElseThrow(
				() -> new RuntimeException("Edificio no encontradao con ID: " + request.getEdificio().getId()));

		Apartamento nuevo = new Apartamento();

		nuevo.setEdificio(edificio);
		nuevo.setNombre(request.getNombre());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(request.getEstado());
		return apartamentoRepository.save(nuevo);

	}

	public Apartamento guardar(Integer id, Apartamento request) {
		
		Edificio edificio = edificioRepository.findById(request.getEdificio().getId()).orElseThrow(
				() -> new RuntimeException("Edificio no encontradao con ID: " + request.getEdificio().getId()));

		
		Optional<Apartamento> apartamento = apartamentoRepository.findById(id);

		if (apartamento.isPresent()) {
			Apartamento nuevo = apartamento.get();
			nuevo.setEdificio(edificio);
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
