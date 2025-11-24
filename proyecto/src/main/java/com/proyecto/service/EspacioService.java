package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.Espacio;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.EspacioRepository;
import com.proyecto.service.ApartamentoService.ResourceNotFoundException;

@Service
public class EspacioService {

	@Autowired
	private EspacioRepository espacioRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	public Espacio insertar(Espacio request) {

		Apartamento apartamento = apartamentoRepository.findById(request.getApartamento().getId()).orElseThrow(
				() -> new RuntimeException("Apartamento no encontradado con ID: " + request.getApartamento().getId()));

		Espacio nuevo = new Espacio();

		nuevo.setApartamento(apartamento);
		nuevo.setNombre(request.getNombre());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setAlto(request.getAlto());
		nuevo.setAncho(request.getAncho());
		nuevo.setFactor(request.getFactor());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setEstado(request.getEstado());
		return espacioRepository.save(nuevo);

	}

	public Espacio guardar(Integer id, Espacio request) {

		Apartamento apart = apartamentoRepository.findById(request.getApartamento().getId()).orElseThrow(
				() -> new RuntimeException("Apartamento no encontradado con ID: " + request.getApartamento().getId()));

		Optional<Espacio> apartamento = espacioRepository.findById(id);

		if (apartamento.isPresent()) {
			Espacio nuevo = apartamento.get();
			nuevo.setApartamento(apart);
			nuevo.setNombre(request.getNombre());
			nuevo.setObservacion(request.getObservacion());
			nuevo.setAlto(request.getAlto());
			nuevo.setAncho(request.getAncho());
			nuevo.setFactor(request.getFactor());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setEstado(request.getEstado());
			return espacioRepository.save(nuevo);

		} else {

			return null;
		}

	}

	public List<Espacio> listar() {

		return espacioRepository.findAll();

	}

	public Espacio listarUno(Integer id) {
		return espacioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Espacio no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (espacioRepository.existsById(id)) {
			espacioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Object[]> obtenerNombreYPrecioPorNombres(String nombreEdificio, String nombreApartamento) {
		return espacioRepository.obtenerNombreYPrecioPorNombres(nombreEdificio, nombreApartamento);
	}

}
