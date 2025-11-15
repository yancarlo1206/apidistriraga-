package com.proyecto.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.proyecto.entity.CotizacionEspacio;
import com.proyecto.repository.CotizacionEspacioRepository;
import com.proyecto.service.EdificioService.ResourceNotFoundException;

@Service
public class CotizacionEspacioService {
	
	@Autowired
	private CotizacionEspacioRepository cotizacionEspacioRepository;
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
}
