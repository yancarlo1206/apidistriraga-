package com.proyecto.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name="Cotizacion")
@Entity
public class Cotizacion {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime fecha;
	
	@NotNull(message = "debe haber referencia de cliente en este campo")
	@ManyToOne
	@JoinColumn(name = "cliente", referencedColumnName ="id")
	private Usuario usuario;
	
	@NotNull(message = "debe haber referencia de apartamento en este campo")
	@ManyToOne
	@JoinColumn(name = "apartamento", referencedColumnName ="id")
	private Apartamento apartamento;
	
	@NotNull(message = "El precio es obligatorio")
	private Integer precio;
	
	@NotNull(message = "debe haber referencia del tipo de cotizacion en este campo")
	@ManyToOne
	@JoinColumn(name = "tipo", referencedColumnName ="id")
	private CotizacionTipo tipo;
	
	private Integer observacion;

    @NotNull(message = "debe haber referencia del estado de cotizacion en este campo")
    @ManyToOne
    @JoinColumn(name = "estado", referencedColumnName ="id")
    private CotizacionEstado estado;
}
