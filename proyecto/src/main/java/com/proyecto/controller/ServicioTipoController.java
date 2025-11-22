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

import com.proyecto.entity.ServicioTipo;
import com.proyecto.response.Responses;
import com.proyecto.service.ServicioTipoService;

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("api/servicioTipo")
public class ServicioTipoController {
	@Autowired
	private ServicioTipoService servicioTipoService;

	
	 @PostMapping
		public ResponseEntity<Responses<?>> guardar(@Valid @RequestBody ServicioTipo request, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			try {
				ServicioTipo creado = servicioTipoService.insertar(request);
				return ResponseEntity.ok(new Responses<>("tipo de servicio creado correctamente", HttpStatus.OK.value(), creado));
			} catch (Exception e) {

				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@GetMapping("{id}")

		public ResponseEntity<Responses<?>> get(@PathVariable Integer id) {

			try {
				String mensage;
				ServicioTipo servicioTipo = servicioTipoService.listarUno(id);
				if (servicioTipo != null) {
					mensage = ("Servicio Tipo: " + id);

				} else {
					mensage = ("Tipo de servicio con id:" + id + "no encontrado");
				}
				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), servicioTipo));

			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}

		}

		@GetMapping
		public ResponseEntity<Responses<?>> listar() {

			String mensage;

			try {

				List<ServicioTipo> servicioTipo =servicioTipoService.listar();

				if (!servicioTipo.isEmpty()) {
					mensage = ("tipo de servicio de cotizacion");
				} else {
					mensage = ("No hay lista tipos de servicio.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), servicioTipo));

			} catch (Exception e) {
				return ResponseEntity.badRequest()
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@PutMapping("/{id}")

		public ResponseEntity<Responses<?>> actualizar(@PathVariable Integer id, @Valid @RequestBody ServicioTipo request,
				BindingResult bindingResult)

		{
			if (bindingResult.hasErrors()) {

				String errorMsg = bindingResult.getFieldError().getDefaultMessage();
				return ResponseEntity.badRequest().body(new Responses<>(errorMsg, HttpStatus.BAD_REQUEST.value(), null));
			}

			ServicioTipo servicioTipo = servicioTipoService.guardar(id, request);
			try {
				String mensage;
				if (servicioTipo != null) {
					mensage = ("tipo de servicio actualizado");
				} else {
					mensage = ("Tipo de servicio actualizado con ID:" + id + " No encontrado.");
				}

				return ResponseEntity.ok(new Responses<>(mensage, HttpStatus.OK.value(), servicioTipo));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new Responses<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), null));
			}
		}

		@DeleteMapping("/{id}")
		public ResponseEntity<Responses<?>> eliminar(@PathVariable Integer id) {

			boolean eliminado =servicioTipoService.eliminar(id);
			if (eliminado) {
				return ResponseEntity.ok(new Responses<>("Servicio eliminado.", HttpStatus.OK.value(), null));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new Responses<>("Servicio no encontrado para eliminar.", HttpStatus.NOT_FOUND.value(), null));
			}

		}
}
