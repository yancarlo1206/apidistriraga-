package com.proyecto.entity;
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

@Data
@Table(name="Espacio")
@Entity
public class Espacio {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "debe haber referencia de apartamento en este campo")
	@ManyToOne
	@JoinColumn(name = "apartamento", referencedColumnName ="id")
	private Apartamento apartamento;
	
	@NotBlank(message = "El Nombre es obligatorio")
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String nombre;
	
	
	@Size(min = 0, max = 30, message = "Maximo 30 caracteres")
	private String observacion;
	
	@NotNull(message = "medidas de alto es obligatoria")
	private Integer alto;
	
	@NotNull(message = "medidas de ancho es obligatoria")
	private Integer ancho;
	
	@NotNull(message = "medidas de factor es obligatoria")
	private Integer factor;
	
	@NotNull (message= "medidas de factor es obligatoria")
	private Integer precio;
	
	private Integer estado;
}
