package com.proyecto.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.ClienteRepository;
import com.proyecto.repository.CotizacionRepository;
import com.proyecto.repository.EdificioRepository;

@Service
public class DashboardService {

	@Autowired
	private EdificioRepository edificioRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CotizacionRepository cotizacionRepository;

	public Map<String, Long> obtenerDatosCards() {
		Map<String, Long> data = new HashMap<>();
		data.put("totalEdificios", edificioRepository.count());
		data.put("totalApartamentos", apartamentoRepository.count());
		data.put("totalClientes", clienteRepository.count());
		data.put("totalCotizaciones", cotizacionRepository.count());
		return data;
	}

}
