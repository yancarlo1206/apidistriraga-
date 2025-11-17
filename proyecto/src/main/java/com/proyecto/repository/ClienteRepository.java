package com.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
