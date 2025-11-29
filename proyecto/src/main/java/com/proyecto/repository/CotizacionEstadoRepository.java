package com.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.CotizacionEstado;

public interface CotizacionEstadoRepository extends JpaRepository<CotizacionEstado, Integer>{
	 Optional<CotizacionEstado> findByNombre(String nombre);
}
