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

import com.proyecto.entity.Espacio;
import com.proyecto.response.Responses;
import com.proyecto.service.EspacioService;

import jakarta.validation.Valid;

@RequestMapping("api/espacio")
@RestController
public class EspacioController {

	@Autowired
	
	private EspacioService espacioService;
	
	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody Espacio request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Espacio creado = espacioService.insertar(request);
			return ResponseEntity.ok(new Responses<>("Espacio creado correctamente", HttpStatus.OK.value(), creado));
		} catch (Exception e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			Espacio espacio = espacioService.listarUno(id);
			if (espacio != null) {
				mensage = ("el espacio: " + id);

			} else {
				mensage = ("espacio con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), espacio));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<Espacio> espacio =espacioService.listar();

			if (!espacio.isEmpty()) {
				mensage = ("lista de espacio");
			} else {
				mensage = ("No hay espacios creados.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), espacio));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")

	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Espacio request,
			BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		Espacio espacio = espacioService.guardar(id, request);
		try {
			String mensage;
			if (espacio != null) {
				mensage = ("espacio actualizado");
			} else {
				mensage = ("espacio con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), espacio));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Integer id) {

		boolean eliminado = espacioService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok("espacio con ID:" + id + " eliminado correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("espacio con ID:" + id + "no encontrado");
		}

	}
}
