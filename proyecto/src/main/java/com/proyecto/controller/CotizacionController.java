package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Cotizacion;
import com.proyecto.response.Responses;
import com.proyecto.service.CotizacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/cotizacion")
public class CotizacionController {

	@Autowired
	private CotizacionService cotizacionService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody Cotizacion request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Cotizacion creado = cotizacionService.insertar(request);
			return ResponseEntity.ok(new Responses<>("Cotizacion creada correctamente", HttpStatus.OK.value(), creado));
		} catch (RuntimeException e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			Cotizacion cotizacion = cotizacionService.listarUno(id);
			if (cotizacion != null) {
				mensage = ("la cotizacion: " + id);

			} else {
				mensage = ("cotizacion con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacion));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<Cotizacion> cotizacion = cotizacionService.listar();

			if (!cotizacion.isEmpty()) {
				mensage = ("lista de cotizaciones");
			} else {
				mensage = ("No hay cotizaciones creados.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacion));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Cotizacion request,
			BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Cotizacion cotizacion = cotizacionService.guardar(id, request);
			String mensage;
			if (cotizacion != null) {
				mensage = ("cotizacion actualizada");
			} else {
				mensage = ("cotizacion con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacion));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = cotizacionService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Cotización eliminada.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new Responses<>("Cotización no encontrada.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
