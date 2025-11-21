package com.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.dto.AuthRequest;
import com.proyecto.dto.AuthResponse;
import com.proyecto.dto.RegisterRequest;
import com.proyecto.entity.Usuario;
import com.proyecto.entity.UsuarioTipo;
import com.proyecto.repository.UsuarioRepository;
import com.proyecto.repository.UsuarioTipoRepository;
import com.proyecto.security.JwtUtil;
import com.proyecto.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UsuarioRepository userRepository;
    
    @Autowired
    private UsuarioTipoRepository usuarioTipoRepository;
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            final UserDetails userDetails = customUserService.loadUserByUsername(request.getUsername());
            final String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

   
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe");
        }

        if (userRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        UsuarioTipo usuarioTipo = usuarioTipoRepository.findById(request.getUsuarioTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado"));

        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setCorreo(request.getCorreo());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setUsuario_tipo(usuarioTipo);
   
        userRepository.save(user);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }
    
}
