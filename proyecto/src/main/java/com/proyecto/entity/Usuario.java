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

@Entity
@Data
@Table(name="Usuario")
public class Usuario {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "El correo es obligatorio")
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String correo;
	
	@NotBlank(message = "la contraseña es obligatoria")
	@Size(min = 2, max = 20, message = "Maximo 20 caracteres")
	private String contrasena;
	
	@NotNull(message = "tipo no puede estar vacia")
	@ManyToOne
	@JoinColumn(name = "tipo", referencedColumnName ="id")
	private UsuarioTipo tipo;
	
	@Size(min = 2, max = 30, message = "Maximo 30 caracteres")
	private String observacion;
	
	private Integer estado;
}
