package com.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	  Optional<Usuario> findByUsername(String username);
	  Optional<Usuario> findByCorreo(String correo);

}
