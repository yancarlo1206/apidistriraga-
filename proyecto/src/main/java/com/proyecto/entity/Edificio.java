package com.proyecto.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Size(min = 2, max = 50, message = "Maximo 30 caracteres")
	private String nombre;
	
	@NotBlank(message = "la direccion es obligatoria")
	@Size(min = 2, max = 100, message = "Maximo 20 caracteres")
	private String direccion;
	
	@Size(min = 2, max = 50, message = "Maximo 30 caracteres")
	private String ciudad;
	
	@Size(max = 100, message = "Maximo 30 caracteres")
	private String ubicacion;
	
	@Size(max = 100, message = "Maximo 30 caracteres")
	private String administrador;
	
	@NotNull
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String celular;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fecha;
	
	@Size(max = 100, message = "Maximo 30 caracteres")
	private String observacion;
	
	
	private Integer estado;
	
	@OneToMany(mappedBy = "edificio")
	@JsonIgnore
    private List<Apartamento> apartamentos;
	
	
	
}
