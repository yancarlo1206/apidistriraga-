package com.proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.proyecto.entity.Espacio;
import com.proyecto.response.Responses;
import com.proyecto.service.EspacioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
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

			List<Espacio> espacio = espacioService.listar();

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

		try {
			Espacio espacio = espacioService.guardar(id, request);
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
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = espacioService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Espacio eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Responses<>("Espacio no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping("/precio/{edificio}/{apartamento}")
	public ResponseEntity<Responses<?>> obtenerNombreYPrecio(@PathVariable String edificio,	@PathVariable String apartamento) {
		try {
			List<Object[]> datos = espacioService.obtenerNombreYPrecioPorNombres(edificio, apartamento);

			List<Map<String, Object>> lista = datos.stream().map(obj -> {
				Map<String, Object> map = new HashMap<>();
				map.put("nombre", obj[0]);
				map.put("precio", obj[1]);
				return map;
			}).toList();

			String mensaje = lista.isEmpty() ? "No hay espacios con ese edificio y apartamento." : "Datos encontrados.";

			return ResponseEntity.ok(new Responses<>(mensaje, HttpStatus.OK.value(), lista));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}
}
