package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.entity.Edificio;

public interface EdificioRepository extends JpaRepository<Edificio, Integer> {
	
	@Query("SELECT DISTINCT e FROM Edificio e LEFT JOIN FETCH e.apartamentos")
    List<Edificio> findAllWithApartamentos();

}
