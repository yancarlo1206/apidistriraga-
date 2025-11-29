package com.proyecto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.Cliente;
import com.proyecto.dto.CotizacionChatDTO;
import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Cotizacion;
import com.proyecto.entity.CotizacionTipo;
import com.proyecto.entity.Edificio;
import com.proyecto.entity.CotizacionEstado;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.ClienteRepository;
import com.proyecto.repository.CotizacionEspacioRepository;
import com.proyecto.repository.CotizacionEstadoRepository;
import com.proyecto.repository.CotizacionRepository;
import com.proyecto.repository.CotizacionTipoRepository;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.repository.EspacioRepository;

import jakarta.transaction.Transactional;

@Service
public class CotizacionService {

	@Autowired
	private CotizacionRepository cotizacionRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Autowired
	private CotizacionTipoRepository coTipoRepository;

	@Autowired
	private CotizacionEstadoRepository coEstadoRepository;

	@Autowired
	private EdificioRepository edificioRepository;
	
	@Autowired
	private CotizacionEspacioService cotizacionEspacioService;

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
				.orElseThrow(() -> new RuntimeException("Cotizacion no encontrada con ID: " + id));
	}

	public boolean eliminar(Integer id) {
		if (cotizacionRepository.existsById(id)) {
			cotizacionRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Transactional
	public Cotizacion insertarChat(CotizacionChatDTO request) {

		Cliente cliente = clienteRepository.findByNombre(request.getCliente().trim())
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + request.getCliente()));

		Edificio edificio = edificioRepository.findByNombre(request.getEdificio().trim())
				.orElseThrow(() -> new RuntimeException("Edificio no encontrado: " + request.getEdificio()));

		Apartamento apartamento = apartamentoRepository.findByNombreAndEdificio(request.getApartamento().trim(), edificio)
				.orElseThrow(() -> new RuntimeException("Apartamento " +request.getApartamento()+ " no encontrado, o NO esta relacionado con el edificio "+ request.getEdificio()));

		CotizacionTipo cotipo = coTipoRepository.findByNombre("Tipo alcoba")
				.orElseThrow(() -> new RuntimeException("Tipo de cotización no encontrado"));
        
		CotizacionEstado coestado = coEstadoRepository.findById(1).orElseThrow(
				() -> new RuntimeException("Estado cotización no encontradado con ID: 1"));

		
		Cotizacion nuevo = new Cotizacion();
		nuevo.setFecha(LocalDateTime.now());
		nuevo.setPrecio(request.getTotal());
		nuevo.setCliente(cliente);
		nuevo.setApartamento(apartamento);
		nuevo.setTipo(cotipo);
		nuevo.setEstado(coestado);
		nuevo.setObservacion("Que observaciones van");

		Cotizacion guardada = cotizacionRepository.save(nuevo);

		// Guardar espacios de la cotizacion
        cotizacionEspacioService.guardarEspacios(guardada, request.getEspacio());

		return guardada;
	}
}