package com.proyecto.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="Apartamento")
public class Apartamento {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "El Nombre es obligatorio")
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String nombre;
	
	@NotNull(message = "Edificio no puede estar vacia")
	private Double precio;
	
	@NotNull(message = "Edificio no puede estar vacia")
	@ManyToOne
	@JoinColumn(name = "edificio", referencedColumnName ="id")
	@JsonIgnore
	private Edificio edificio;
	
	
	@Size(max = 100, message = "Maximo 30 caracteres")
	private String observacion;
	
	
	private Integer estado;

}
