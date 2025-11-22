package com.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.proyecto.entity.Cliente;
import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Cotizacion;
import com.proyecto.entity.CotizacionTipo;
import com.proyecto.entity.CotizacionEstado;
import com.proyecto.entity.Edificio;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.ClienteRepository;
import com.proyecto.repository.CotizacionEstadoRepository;
import com.proyecto.repository.CotizacionRepository;
import com.proyecto.repository.CotizacionTipoRepository;
import com.proyecto.service.ApartamentoService.ResourceNotFoundException;

@Service
public class CotizacionService {

	@Autowired	
	private CotizacionRepository cotizacionRepository;
	
	@Autowired	
	private ClienteRepository clienteRepository;
	
	@Autowired	
	private ApartamentoRepository apartamentoRepository;
	
	@Autowired	
	private CotizacionTipoRepository  coTipoRepository;
	
	@Autowired	
	private CotizacionEstadoRepository  coEstadoRepository;
	
	public Cotizacion insertar(Cotizacion request) {
		
		Cliente cliente = clienteRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Cliente no encontradao con ID: " + request.getCliente().getId()));
		
		Apartamento apartamento = apartamentoRepository.findById(request.getApartamento().getId()).orElseThrow(
				() -> new RuntimeException("Apartamento no encontradado con ID: " + request.getApartamento().getId()));
		
		CotizacionTipo cotipo = coTipoRepository.findById(request.getTipo().getId()).orElseThrow(
				() -> new RuntimeException("Tipo cotización no encontradado con ID: " + request.getTipo().getId()));
		
		CotizacionEstado coestado = coEstadoRepository.findById(request.getEstado().getId()).orElseThrow(
				() -> new RuntimeException("Estado cotización no encontradado con ID: " + request.getEstado().getId()));

		Cotizacion nuevo = new Cotizacion();
		
		nuevo.setFecha(LocalDateTime.now());
		nuevo.setPrecio(request.getPrecio());
		nuevo.setCliente(cliente);
		nuevo.setApartamento(apartamento);
		nuevo.setPrecio(request.getPrecio());
		nuevo.setTipo(cotipo);
		nuevo.setObservacion(request.getObservacion());
		nuevo.setEstado(coestado);
		return cotizacionRepository.save(nuevo);

	}

	public Cotizacion guardar(Integer id, Cotizacion request) {
		
		Cliente cliente = clienteRepository.findById(request.getCliente().getId()).orElseThrow(
				() -> new RuntimeException("Cliente no encontradao con ID: " + request.getCliente().getId()));
		
		Apartamento apartamento = apartamentoRepository.findById(request.getApartamento().getId()).orElseThrow(
				() -> new RuntimeException("Apartamento no encontradado con ID: " + request.getApartamento().getId()));
		
		CotizacionTipo cotipo = coTipoRepository.findById(request.getTipo().getId()).orElseThrow(
				() -> new RuntimeException("Tipo cotización no encontradado con ID: " + request.getTipo().getId()));
		
		CotizacionEstado coestado = coEstadoRepository.findById(request.getEstado().getId()).orElseThrow(
				() -> new RuntimeException("Estado cotización no encontradado con ID: " + request.getEstado().getId()));

		
		Optional<Cotizacion> cotizacion = cotizacionRepository.findById(id);

		if (cotizacion.isPresent()) {
			Cotizacion nuevo = cotizacion.get();
			nuevo.setFecha(LocalDateTime.now());
			nuevo.setPrecio(request.getPrecio());
			nuevo.setCliente(cliente);
			nuevo.setApartamento(apartamento);
			nuevo.setPrecio(request.getPrecio());
			nuevo.setTipo(cotipo);
			nuevo.setObservacion(request.getObservacion());
			nuevo.setEstado(coestado);
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
