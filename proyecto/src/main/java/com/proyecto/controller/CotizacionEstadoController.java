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

import com.proyecto.entity.CotizacionEstado;
import com.proyecto.response.Responses;
import com.proyecto.service.CotizacionEstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/cotizacionEstado")
public class CotizacionEstadoController {
	@Autowired
	private CotizacionEstadoService cotizacionEstadoService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody CotizacionEstado request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			CotizacionEstado creado = cotizacionEstadoService.insertar(request);
			return ResponseEntity
					.ok(new Responses<>("Estado de cotizacion creado correctamente", HttpStatus.OK.value(), creado));
		} catch (Exception e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			CotizacionEstado cotizacionEstado = cotizacionEstadoService.listarUno(id);
			if (cotizacionEstado != null) {
				mensage = ("estado de cotizacion: " + id);

			} else {
				mensage = ("estado con cotizacion con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionEstado));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<CotizacionEstado> cotizacionEstado = cotizacionEstadoService.listar();

			if (!cotizacionEstado.isEmpty()) {
				mensage = ("lista de estados de cotizacion");
			} else {
				mensage = ("No hay lista de estados de cotizacion.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionEstado));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")

	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id,
			@Valid @RequestBody CotizacionEstado request, BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		CotizacionEstado cotizacionEstado = cotizacionEstadoService.guardar(id, request);
		try {
			String mensage;
			if (cotizacionEstado != null) {
				mensage = ("Estado de cotizacion actualizado");
			} else {
				mensage = ("Estado de cotizacion actualizado con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionEstado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = cotizacionEstadoService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Registro eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Responses<>("Registro no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}
}
