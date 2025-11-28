package com.proyecto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.response.Responses;
import com.proyecto.service.DashboardService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/datosCards")
	public ResponseEntity<Responses<?>> getDatosCards() {

		try {
			Map<String, Long> resumen = dashboardService.obtenerDatosCards();
			return ResponseEntity.ok(new Responses<>("Datos principales.", HttpStatus.OK.value(), resumen));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Responses<>(
					"Error al obtener datos del servicio. "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
		}
	}

}
