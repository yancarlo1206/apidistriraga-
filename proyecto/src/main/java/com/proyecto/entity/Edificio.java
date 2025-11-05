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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="Edificio")
public class Edificio {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "El Nombre es obligatorio")
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String nombre;
	
	@NotBlank(message = "la direccion es obligatoria")
	@Size(min = 2, max = 20, message = "Maximo 20 caracteres")
	private String direccion;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String ciudad;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String ubicacion;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String administrador;
	
	@NotNull
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String celular;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime fecha;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String observacion;
	
	
	private Integer estado;
	
	
	
}
