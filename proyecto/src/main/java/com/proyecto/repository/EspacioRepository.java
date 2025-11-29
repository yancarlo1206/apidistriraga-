package com.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.entity.Espacio;

public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

	@Query("""
			SELECT e.nombre, e.precio
			FROM Espacio e
			WHERE e.apartamento.nombre = :nombreApartamento
			AND e.apartamento.edificio.nombre = :nombreEdificio
			""")
	List<Object[]> obtenerNombreYPrecioPorNombres(@Param("nombreEdificio") String nombreEdificio,
			@Param("nombreApartamento") String nombreApartamento);
	
	Optional<Espacio> findByNombre(String nombre);
}
