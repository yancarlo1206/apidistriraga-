package com.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.entity.Apartamento;
import com.proyecto.entity.Edificio;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {

	List<Apartamento> findByEdificioId(Integer edificioId);

	@Query("SELECT a FROM Apartamento a WHERE a.nombre = :nombre AND a.edificio = :edificio")
	Optional<Apartamento> findByNombreAndEdificio(@Param("nombre") String nombre, @Param("edificio") Edificio edificio);

}
