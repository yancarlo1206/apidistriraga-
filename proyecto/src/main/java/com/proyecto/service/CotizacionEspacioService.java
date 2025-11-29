package com.proyecto.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.dto.EspacioDTO;
import com.proyecto.entity.Cotizacion;
import com.proyecto.entity.CotizacionEspacio;
import com.proyecto.entity.Espacio;
import com.proyecto.repository.CotizacionEspacioRepository;
import com.proyecto.repository.EspacioRepository;
import com.proyecto.service.EdificioService.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CotizacionEspacioService {

	@Autowired
	private CotizacionEspacioRepository cotizacionEspacioRepository;

	@Autowired
	private EspacioRepository espacioRepository;

	public CotizacionEspacio insertar(CotizacionEspacio request) {

		CotizacionEspacio nuevo = new CotizacionEspacio();

		nuevo.setCotizacion(request.getCotizacion());
		nuevo.setEspacio(request.getEspacio());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setEstado(request.getEstado());
		return cotizacionEspacioRepository.save(nuevo);

	}

	public CotizacionEspacio guardar(Integer id, CotizacionEspacio request) {
		Optional<CotizacionEspacio> cotizacionEspacio = cotizacionEspacioRepository.findById(id);

		if (cotizacionEspacio.isPresent()) {
			CotizacionEspacio nuevo = new CotizacionEspacio();
			nuevo.setCotizacion(request.getCotizacion());
			nuevo.setEspacio(request.getEspacio());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setEstado(request.getEstado());
			return cotizacionEspacioRepository.save(nuevo);
		} else {

			return null;
		}

	}

	public List<CotizacionEspacio> listar() {

		return cotizacionEspacioRepository.findAll();

	}

	public CotizacionEspacio listarUno(Integer id) {
		return cotizacionEspacioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cotizacion espacio no encontrado con ID: " + id));
	}

	@ResponseStatus()
	public class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	public boolean eliminar(Integer id) {
		if (cotizacionEspacioRepository.existsById(id)) {
			cotizacionEspacioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Transactional
	public void guardarEspacios(Cotizacion cotizacion, List<EspacioDTO> espaciosDto) {
		if (espaciosDto == null)
			return;

		for (EspacioDTO dto : espaciosDto) {
			Espacio e = espacioRepository.findByNombre(dto.getNombre())
					.orElseThrow(() -> new RuntimeException("Espacio no encontrado: " + dto.getNombre()));

			CotizacionEspacio espacio = new CotizacionEspacio();
			espacio.setPrecio(dto.getTotal());
			espacio.setEstado("1"); 
			espacio.setCotizacion(cotizacion);
			espacio.setEspacio(e);

			cotizacionEspacioRepository.save(espacio);
		}

	}

}
