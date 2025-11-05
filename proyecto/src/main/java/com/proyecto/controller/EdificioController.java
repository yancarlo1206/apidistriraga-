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

import com.proyecto.entity.Edificio;
import com.proyecto.response.Responses;
import com.proyecto.service.EdificioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/edificio")
public class EdificioController {
	
	 @Autowired
	 private EdificioService edificioService;
	 @PostMapping
		public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody Edificio request, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			try {
				Edificio creado = edificioService.insertar(request);
				return ResponseEntity.ok(new Responses<>("Edificio creado correctamente", HttpStatus.OK.value(), creado));
			} catch (Exception e) {

				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@GetMapping("{id}")

		public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

			try {
				String mensage;
				Edificio edificio = edificioService.listarUno(id);
				if (edificio != null) {
					mensage = ("el edificio: " + id);

				} else {
					mensage = ("edificio con id:" + id + "no encontrado");
				}
				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), edificio));

			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}

		}

		@GetMapping
		public ResponseEntity<Responses<?>> listar() {

			String mensage;

			try {

				List<Edificio> edificio =edificioService.listar();

				if (!edificio.isEmpty()) {
					mensage = ("lista de edificio");
				} else {
					mensage = ("No hay edificios creados.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), edificio));

			} catch (Exception e) {
				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@PutMapping("/{id}")

		public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Edificio request,
				BindingResult bindingResult)

		{
			if (bindingResult.hasErrors()) {

				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Edificio edificio = edificioService.guardar(id, request);
			try {
				String mensage;
				if (edificio != null) {
					mensage = ("edificio actualizado");
				} else {
					mensage = ("edificio con ID:" + id + " No encontrado.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), edificio));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@DeleteMapping("/{id}")
		public ResponseEntity<String> eliminar(@PathVariable Integer id) {

			boolean eliminado = edificioService.eliminar(id);
			if (eliminado) {
				return ResponseEntity.ok("edificio con ID:" + id + " eliminado correctamente");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("edificio con ID:" + id + "no encontrado");
			}

		}
}
