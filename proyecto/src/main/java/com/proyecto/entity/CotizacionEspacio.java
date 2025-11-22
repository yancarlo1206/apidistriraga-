package com.proyecto.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Table(name="CotizacionEspacio")
@Entity
public class CotizacionEspacio {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Cotizacion no puede estar vacia")
	@ManyToOne
	@JoinColumn(name = "cotizacion", referencedColumnName ="id")
	private Cotizacion cotizacion;
	
	@NotNull(message = "espacio no puede estar vacia")
	@ManyToOne
	@JoinColumn(name = "espacio", referencedColumnName ="id")
	private Espacio espacio;
	
	@NotNull (message= "precio de factor es obligatoria")
	private Double precio;
	
	private String estado;
	
	
}
