package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.CotizacionTipo;
import com.proyecto.response.Responses;
import com.proyecto.service.CotizacionTipoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/cotizacionTipo")
public class CotizacionTipoController {

	@Autowired
	private CotizacionTipoService cotizacionTipoService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody CotizacionTipo request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			CotizacionTipo creado = cotizacionTipoService.insertar(request);
			return ResponseEntity
					.ok(new Responses<>("Tipo de cotizacion creado correctamente", HttpStatus.OK.value(), creado));
		} catch (Exception e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			CotizacionTipo cotizacionTipo = cotizacionTipoService.listarUno(id);
			if (cotizacionTipo != null) {
				mensage = ("Tipo de cotizacion: " + id);

			} else {
				mensage = ("Tipo con cotizacion con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionTipo));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<CotizacionTipo> cotizacionTipo = cotizacionTipoService.listar();

			if (!cotizacionTipo.isEmpty()) {
				mensage = ("lista de Tipos de cotizacion");
			} else {
				mensage = ("No hay lista de tipos de cotizacion.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionTipo));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")

	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody CotizacionTipo request,
			BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		CotizacionTipo cotizacionTipo = cotizacionTipoService.guardar(id, request);
		try {
			String mensage;
			if (cotizacionTipo != null) {
				mensage = ("Tipo de cotizacion actualizado");
			} else {
				mensage = ("Tipo de cotizacion actualizado con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), cotizacionTipo));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = cotizacionTipoService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Registro eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Responses<>("Registro no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
