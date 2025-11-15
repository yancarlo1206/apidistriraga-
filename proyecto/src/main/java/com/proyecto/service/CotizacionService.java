package com.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.proyecto.entity.Cotizacion;
import com.proyecto.repository.CotizacionRepository;
import com.proyecto.service.ApartamentoService.ResourceNotFoundException;

@Service
public class CotizacionService {

	@Autowired
	
	private CotizacionRepository cotizacionRepository;
	
	
	public Cotizacion insertar(Cotizacion request) {

		Cotizacion nuevo = new Cotizacion();
		
		nuevo.setFecha(LocalDateTime.now());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setCliente(request.getCliente());
		nuevo.setApartamento(request.getApartamento());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setTipo(request.getTipo());
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(request.getEstado());
		return cotizacionRepository.save(nuevo);

	}

	public Cotizacion guardar(Integer id, Cotizacion request) {
		Optional<Cotizacion> cotizacion = cotizacionRepository.findById(id);

		if (cotizacion.isPresent()) {
			Cotizacion nuevo = cotizacion.get();
			nuevo.setFecha(LocalDateTime.now());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setCliente(request.getCliente());
			nuevo.setApartamento(request.getApartamento());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setTipo(request.getTipo());
			nuevo.setObservacion(request.getObservacion());
			nuevo.setEstado(request.getEstado());
			return cotizacionRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<Cotizacion> listar() {

		return cotizacionRepository.findAll();

	}

	public Cotizacion listarUno(Integer id) {
		return cotizacionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cotizacion no encontrada con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (cotizacionRepository.existsById(id)) {
			cotizacionRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
