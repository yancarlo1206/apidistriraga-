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

import com.proyecto.entity.CotizacionEspacio;
import com.proyecto.response.Responses;
import com.proyecto.service.CotizacionEspacioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/cotizacionEspacio")
public class CotizacionEspacioController {

	@Autowired
	private CotizacionEspacioService cotizacionEspacioService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody CotizacionEspacio request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			CotizacionEspacio creado = cotizacionEspacioService.insertar(request);
			return ResponseEntity.ok(new Responses<>("Cotizacion creada correctamente", HttpStatus.OK.value(), creado));
		} catch (Exception e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			CotizacionEspacio cotizacionEspacio = cotizacionEspacioService.listarUno(id);
			if (cotizacionEspacio != null) {
				mensage = ("la cotizacion espacio: " + id);

			} else {
				mensage = ("cotizacion Espacio con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionEspacio));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<CotizacionEspacio> cotizacion = cotizacionEspacioService.listar();

			if (!cotizacion.isEmpty()) {
				mensage = ("lista de cotizacion espacios");
			} else {
				mensage = ("No hay cotizaciones espacios creados.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacion));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")

	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id,
			@Valid @RequestBody CotizacionEspacio request,
			BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		CotizacionEspacio cotizacionEspacio = cotizacionEspacioService.guardar(id, request);
		try {
			String mensage;
			if (cotizacionEspacio != null) {
				mensage = ("cotizacion espacio actualizada");
			} else {
				mensage = ("cotizacion espacio con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionEspacio));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = cotizacionEspacioService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Registro eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new Responses<>("Registro no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
