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

import com.proyecto.entity.UsuarioTipo;
import com.proyecto.response.Responses;
import com.proyecto.service.UsuarioTipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuarioTipo")
public class UsuarioTipoController {
	@Autowired
	private UsuarioTipoService usuarioTipoService;

	@PostMapping
	public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody UsuarioTipo request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		try {
			UsuarioTipo creado = usuarioTipoService.insertar(request);
			return ResponseEntity
					.ok(new Responses<>("tipo de usuario creado correctamente", HttpStatus.OK.value(), creado));
		} catch (Exception e) {

			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@GetMapping("{id}")

	public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

		try {
			String mensage;
			UsuarioTipo usuarioTipo = usuarioTipoService.listarUno(id);
			if (usuarioTipo != null) {
				mensage = ("el tipo de usuario: " + id);

			} else {
				mensage = ("tipo de usuario con id:" + id + "no encontrado");
			}
			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), usuarioTipo));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}

	}

	@GetMapping
	public ResponseEntity<Responses<?>> listar() {

		String mensage;

		try {

			List<UsuarioTipo> usuarioTipo = usuarioTipoService.listar();

			if (!usuarioTipo.isEmpty()) {
				mensage = ("tipo de usuarios listados");
			} else {
				mensage = ("No hay tipos de usuarios creados.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), usuarioTipo));

		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@PutMapping("/{id}")

	public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioTipo request,
			BindingResult bindingResult)

	{
		if (bindingResult.hasErrors()) {

			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
		}

		UsuarioTipo usuario = usuarioTipoService.guardar(id, request);
		try {
			String mensage;
			if (usuario != null) {
				mensage = ("tipo de usuario actualizado");
			} else {
				mensage = ("tipo de usuario con ID:" + id + " No encontrado.");
			}

			return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), usuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

		boolean eliminado = usuarioTipoService.eliminar(id);
		if (eliminado) {
			return ResponseEntity.ok(new Responses<>("Tipo usuario eliminado.", HttpStatus.OK.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new Responses<>("Tipo usuario no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
		}

	}

}
