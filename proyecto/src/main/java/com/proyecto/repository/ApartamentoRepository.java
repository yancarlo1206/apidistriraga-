package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Apartamento;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Integer> {

}
