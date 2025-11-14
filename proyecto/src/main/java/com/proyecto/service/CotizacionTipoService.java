package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.CotizacionTipo;
import com.proyecto.repository.CotizacionTipoRepository;
import com.proyecto.service.CotizacionEstadoService.ResourceNotFoundException;

@Service
public class CotizacionTipoService {
	@Autowired
	private CotizacionTipoRepository cotizacionTipooRepository;
	
	public CotizacionTipo insertar(CotizacionTipo request) {

		CotizacionTipo nuevo = new CotizacionTipo();
		nuevo.setNombre(request.getNombre());
		return cotizacionTipooRepository.save(nuevo);

	}

	public CotizacionTipo guardar(Integer id, CotizacionTipo request) {
		Optional<CotizacionTipo> cotizacionEstado = cotizacionTipooRepository.findById(id);

		if (cotizacionEstado.isPresent()) {
			CotizacionTipo nuevo = cotizacionEstado.get();
			nuevo.setNombre(request.getNombre());
			return cotizacionTipooRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<CotizacionTipo> listar() {

		return cotizacionTipooRepository.findAll();

	}

	public CotizacionTipo listarUno(Integer id) {
		return cotizacionTipooRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de cotizacion no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (cotizacionTipooRepository.existsById(id)) {
			cotizacionTipooRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
