-- setup_proyecto.sql
-- Script para crear/rehacer la base de datos `proyecto` y los registros iniciales solicitados.

-- Borrar la base de datos si existe y recrearla
DROP DATABASE IF EXISTS proyecto;
CREATE DATABASE proyecto CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE proyecto;

-- Tabla usuario_tipo
CREATE TABLE IF NOT EXISTS usuario_tipo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla usuario
CREATE TABLE IF NOT EXISTS usuario (
  id INT PRIMARY KEY AUTO_INCREMENT,
  correo VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(100) NOT NULL,
  usuario_tipo INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (usuario_tipo) REFERENCES usuario_tipo(id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla cotizacion_tipo
CREATE TABLE IF NOT EXISTS cotizacion_tipo (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla cotizacion_estado
CREATE TABLE IF NOT EXISTS cotizacion_estado (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Inserts solicitados
-- usuario_tipo: un registro llamado 'Administrador' (se inserta con id=1)
INSERT INTO usuario_tipo (id, nombre) VALUES (1, 'Administrador')
  ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- usuario: correo admin@gmail.com, password ya hasheada, username admin, usuario_tipo 1
INSERT INTO usuario (correo, password, username, usuario_tipo)
VALUES ('admin@gmail.com', '$2a$10$Qrhawd3fBARWXrS3knNaWOyUUX3EfJLhlU0T/tLjDW8WZrWEPDGmi', 'admin', 1)
ON DUPLICATE KEY UPDATE password = VALUES(password), username = VALUES(username), usuario_tipo = VALUES(usuario_tipo);

-- cotizacion_tipo: 1, NORMAL
INSERT INTO cotizacion_tipo (id, nombre) VALUES (1, 'NORMAL')
  ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- cotizacion_estado: 1,REGISTRADA; 2,EN PROCESO; 3,TERMINADA;
INSERT INTO cotizacion_estado (id, nombre) VALUES
  (1, 'REGISTRADA'),
  (2, 'EN PROCESO'),
  (3, 'TERMINADA')
  ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Opcional: mostrar resultados al final (al ejecutar manualmente)
-- SELECT * FROM usuario_tipo;
-- SELECT * FROM usuario;
-- SELECT * FROM cotizacion_tipo;
-- SELECT * FROM cotizacion_estado;
