package com.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.CotizacionTipo;

public interface CotizacionTipoRepository extends JpaRepository<CotizacionTipo, Integer>{
	 Optional<CotizacionTipo> findByNombre(String nombre);
}
