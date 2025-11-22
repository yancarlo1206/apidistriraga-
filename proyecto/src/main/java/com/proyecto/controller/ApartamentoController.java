package com.proyecto.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Edificio;
import com.proyecto.repository.ApartamentoRepository;
import com.proyecto.repository.EdificioRepository;
import com.proyecto.response.Responses;
import com.proyecto.service.ApartamentoService;
import com.proyecto.service.EdificioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/apartamento")
public class ApartamentoController {

	@Autowired
	private ApartamentoService apartamentoService;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Autowired
	private EdificioRepository edificioRepository;

	@Autowired
	private EdificioService edificioService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody Apartamento request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			Apartamento creado = apartamentoService.insertar(request);
			return ResponseEntity
					.ok(new Responses<>("Apartamento creado correctamente", HttpStatus.OK.value(), creado));
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

			List<Apartamento> apartamento = apartamentoService.listar();

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
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = apartamentoService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Apartamento eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new Responses<>("Apartamento no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping("/listPorEdificio/{edificioId}")
	public ResponseEntity<Responses<?>> apartamentorPorEdificio(@PathVariable Integer edificioId) {
		
		
		try {
			String nombreEdificio = edificioService.obtenerNombreEdificio(edificioId);

			List<Apartamento> apartamentos = apartamentoRepository.findByEdificioId(edificioId);

			if (apartamentos.isEmpty()) {
			  return ResponseEntity.ok(new Responses<>("El edificio " + nombreEdificio + " no tiene apartamentos registrados.",
								HttpStatus.OK.value(), apartamentos 
			  ));
			}

			return ResponseEntity.ok(new Responses<>("Lista de apartamentos del edificio " + nombreEdificio + ".",
					HttpStatus.OK.value(), apartamentos));

		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Responses<>(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

}
