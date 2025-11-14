package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.ServicioTipo;
import com.proyecto.repository.ServicioTipoRepository;
import com.proyecto.service.CotizacionEstadoService.ResourceNotFoundException;

@Service
public class ServicioTipoService {
	@Autowired
	private ServicioTipoRepository servicioTipoRepository;
	public ServicioTipo insertar(ServicioTipo request) {

		ServicioTipo nuevo = new ServicioTipo();
		nuevo.setNombre(request.getNombre());
		return servicioTipoRepository.save(nuevo);

	}

	public ServicioTipo guardar(Integer id, ServicioTipo request) {
		Optional<ServicioTipo> servicioTipo = servicioTipoRepository.findById(id);

		if (servicioTipo.isPresent()) {
			ServicioTipo nuevo = servicioTipo.get();
			nuevo.setNombre(request.getNombre());
			return servicioTipoRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<ServicioTipo> listar() {

		return servicioTipoRepository.findAll();

	}

	public ServicioTipo listarUno(Integer id) {
		return servicioTipoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("tipo de servicio no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (servicioTipoRepository.existsById(id)) {
			servicioTipoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
