package com.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Apartamento;
import com.proyecto.response.Responses;
import com.proyecto.service.ApartamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/apartamento")
public class ApartamentoController {
	
	@Autowired
	private ApartamentoService apartamentoService;

	
	 @PostMapping
		public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody Apartamento request, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			try {
				Apartamento creado = apartamentoService.insertar(request);
				return ResponseEntity.ok(new Responses<>("Apartamento creado correctamente", HttpStatus.OK.value(), creado));
			} catch (Exception e) {

				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@GetMapping("{id}")

		public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

			try {
				String mensage;
				Apartamento apartamento = apartamentoService.listarUno(id);
				if (apartamento != null) {
					mensage = ("el apartamento: " + id);

				} else {
					mensage = ("apartamento con id:" + id + "no encontrado");
				}
				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), apartamento));

			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}

		}

		@GetMapping
		public ResponseEntity<Responses<?>> listar() {

			String mensage;

			try {

				List<Apartamento> apartamento =apartamentoService.listar();

				if (!apartamento.isEmpty()) {
					mensage = ("lista de apartamentos");
				} else {
					mensage = ("No hay apartamentos creados.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), apartamento));

			} catch (Exception e) {
				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@PutMapping("/{id}")

		public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody Apartamento request,
				BindingResult bindingResult)

		{
			if (bindingResult.hasErrors()) {

				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			Apartamento apartamento = apartamentoService.guardar(id, request);
			try {
				String mensage;
				if (apartamento != null) {
					mensage = ("apartamento actualizado");
				} else {
					mensage = ("apartamento con ID:" + id + " No encontrado.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), apartamento));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@DeleteMapping("/{id}")
		public ResponseEntity<String> eliminar(@PathVariable Integer id) {

			boolean eliminado = apartamentoService.eliminar(id);
			if (eliminado) {
				return ResponseEntity.ok("apartamento con ID:" + id + " eliminado correctamente");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("apartamento con ID:" + id + "no encontrado");
			}

		}
}
