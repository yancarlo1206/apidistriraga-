package com.proyecto.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="Cliente")
public class Cliente {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "el nombre es obligatorio")
	@Size(min = 2, max = 100, message = "Maximo 20 caracteres")
	private String nombre;
	
	@NotBlank(message = "El correo es obligatorio")
	@Size(min = 2, max = 100, message = "Maximo 30 caracteres")
	private String correo;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String celular;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime fecha;
	
	private Integer estado;
}

