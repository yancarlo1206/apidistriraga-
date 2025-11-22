package com.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.dto.RegisterRequest;
import com.proyecto.entity.Usuario;
import com.proyecto.entity.UsuarioTipo;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.repository.UsuarioTipoRepository;
import com.proyecto.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserService customUserService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private UsuarioTipoRepository usuarioTipoRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public String login(String username, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		UserDetails userDetails = customUserService.loadUserByUsername(username);

		Usuario user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		String tipoUsuarioStr = user.getUsuario_tipo().getNombre();

		return jwtUtil.generateToken(userDetails, tipoUsuarioStr);
	}

	public void register(RegisterRequest request) {

		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("El nombre de usuario ya existe");
		}

		if (userRepository.findByCorreo(request.getCorreo()).isPresent()) {
			throw new RuntimeException("El correo ya está registrado");
		}

		UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(request.getUsuarioTipoId())
				.orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

		Usuario user = new Usuario();
		user.setUsername(request.getUsername());
		user.setCorreo(request.getCorreo());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUsuario_tipo(usuarioTipo);

		
		userRepository.save(user);
	}
}
