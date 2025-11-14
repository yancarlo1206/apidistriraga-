package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.CotizacionEstado;
import com.proyecto.repository.CotizacionEstadoRepository;
import com.proyecto.service.ApartamentoService.ResourceNotFoundException;

@Service
public class CotizacionEstadoService {
	
	@Autowired
	private CotizacionEstadoRepository cotizacionEstadoRepository;
	public CotizacionEstado insertar(CotizacionEstado request) {

		CotizacionEstado nuevo = new CotizacionEstado();
		nuevo.setNombre(request.getNombre());
		return cotizacionEstadoRepository.save(nuevo);

	}

	public CotizacionEstado guardar(Integer id, CotizacionEstado request) {
		Optional<CotizacionEstado> cotizacionEstado = cotizacionEstadoRepository.findById(id);

		if (cotizacionEstado.isPresent()) {
			CotizacionEstado nuevo = cotizacionEstado.get();
			nuevo.setNombre(request.getNombre());
			return cotizacionEstadoRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<CotizacionEstado> listar() {

		return cotizacionEstadoRepository.findAll();

	}

	public CotizacionEstado listarUno(Integer id) {
		return cotizacionEstadoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estado de cotizacion no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (cotizacionEstadoRepository.existsById(id)) {
			cotizacionEstadoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
