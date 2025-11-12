# API REST - Sistema de Gestión de Edificios y Apartamentos

API REST desarrollada con Spring Boot para la gestión de edificios, apartamentos, espacios y usuarios.

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Prerequisitos](#prerequisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecutar el Proyecto](#ejecutar-el-proyecto)
- [API Endpoints](#api-endpoints)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Modelos de Datos](#modelos-de-datos)

## 📖 Descripción

Sistema backend que proporciona una API REST para la gestión de:
- **Edificios**: Administración de edificios con información de ubicación y características
- **Apartamentos**: Gestión de apartamentos asociados a edificios
- **Espacios**: Manejo de espacios comunes
- **Usuarios**: Sistema de usuarios con tipos y roles
- **Tipos de Usuario**: Categorización de usuarios

## 🛠 Tecnologías

- **Java**: 21
- **Spring Boot**: 3.5.7
- **Spring Data JPA**: Persistencia de datos
- **Spring Security**: Configuración básica de seguridad
- **Hibernate**: ORM
- **MySQL/MariaDB**: Base de datos
- **Maven**: Gestión de dependencias
- **Lombok**: Reducción de código boilerplate
- **Jakarta Validation**: Validación de datos

## ✅ Prerequisitos

Antes de comenzar, asegúrate de tener instalado:

- **Java Development Kit (JDK) 21** o superior
  ```bash
  java -version
  ```

- **Maven** (incluido en el proyecto con Maven Wrapper)

- **MariaDB/MySQL** (compatible con MySQL 5.5+)
  ```bash
  mysql --version
  ```

## 🚀 Instalación

### 1. Instalar MariaDB/MySQL

#### En Fedora/RHEL/CentOS:
```bash
# Actualizar repositorios
sudo dnf update

# Instalar MariaDB
sudo dnf install mariadb-server mariadb

# Iniciar el servicio
sudo systemctl start mariadb
sudo systemctl enable mariadb

# Configuración inicial segura (opcional pero recomendado)
sudo mysql_secure_installation
```

#### En Ubuntu/Debian:
```bash
sudo apt update
sudo apt install mariadb-server mariadb-client
sudo systemctl start mariadb
sudo systemctl enable mariadb
sudo mysql_secure_installation
```

#### En macOS:
```bash
brew update
brew install mariadb
brew services start mariadb
mysql_secure_installation
```

### 2. Configurar la Base de Datos

Accede a MariaDB/MySQL:
```bash
sudo mysql -u root -p
```

Crea la base de datos y el usuario (si es necesario):
```sql
-- Crear la base de datos
CREATE DATABASE proyecto;

-- Opcional: Crear un usuario específico para el proyecto
CREATE USER 'proyectouser'@'localhost' IDENTIFIED BY 'tu_contraseña';
GRANT ALL PRIVILEGES ON proyecto.* TO 'proyectouser'@'localhost';
FLUSH PRIVILEGES;

-- Verificar que la base de datos fue creada
SHOW DATABASES;

-- Salir
EXIT;
```

### 3. Clonar o Descargar el Proyecto

```bash
cd /ruta/donde/quieres/el/proyecto
git clone <url-del-repositorio>
cd proyecto
```

## ⚙️ Configuración

### Configurar Credenciales de Base de Datos

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.application.name=proyecto

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/proyecto?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

# Configuración de Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Puerto del servidor
server.port=8080
```

**Parámetros importantes:**

- `spring.datasource.username`: Tu usuario de MySQL/MariaDB (por defecto: `root`)
- `spring.datasource.password`: Tu contraseña de MySQL/MariaDB
- `spring.jpa.hibernate.ddl-auto=update`: Actualiza automáticamente el esquema de la BD
- `server.port`: Puerto donde correrá la aplicación (por defecto: `8080`)

### Dar Permisos de Ejecución a Maven Wrapper

```bash
chmod +x mvnw
```

## ▶️ Ejecutar el Proyecto

### Opción 1: Usando Maven Wrapper (Recomendado)

```bash
./mvnw spring-boot:run
```

### Opción 2: En segundo plano con logs

```bash
nohup ./mvnw spring-boot:run > backend.log 2>&1 &
```

Ver logs en tiempo real:
```bash
tail -f backend.log
```

### Opción 3: Compilar y ejecutar JAR

```bash
# Compilar el proyecto
./mvnw clean package

# Ejecutar el JAR generado
java -jar target/proyecto-0.0.1-SNAPSHOT.jar
```

### Verificar que el Servidor está Corriendo

```bash
# Verificar el proceso
ps aux | grep spring-boot

# Probar un endpoint
curl http://localhost:8080/api/Usuario
```

Deberías ver una respuesta JSON como:
```json
{
  "mensaje": "No hay usuarios creados.",
  "data": [],
  "status": 200
}
```

## 🌐 API Endpoints

Base URL: `http://localhost:8080`

### Usuarios (`/api/Usuario`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/Usuario` | Obtener todos los usuarios |
| GET | `/api/Usuario/{id}` | Obtener usuario por ID |
| POST | `/api/Usuario` | Crear nuevo usuario |
| PUT | `/api/Usuario/{id}` | Actualizar usuario |
| DELETE | `/api/Usuario/{id}` | Eliminar usuario |

**Ejemplo de cuerpo para POST:**
```json
{
  "correo": "usuario@ejemplo.com",
  "contrasena": "password123",
  "tipo": {
    "id": 1
  },
  "observacion": "Usuario administrador"
}
```

### Edificios (`/api/edificio`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/edificio` | Obtener todos los edificios |
| GET | `/api/edificio/{id}` | Obtener edificio por ID |
| POST | `/api/edificio` | Crear nuevo edificio |
| PUT | `/api/edificio/{id}` | Actualizar edificio |
| DELETE | `/api/edificio/{id}` | Eliminar edificio |

**Ejemplo de cuerpo para POST:**
```json
{
  "nombre": "Edificio Central",
  "direccion": "Calle Principal 123",
  "ciudad": "Bogotá",
  "ubicacion": "Centro",
  "telefonoAdministrador": "3001234567",
  "correoAdministrador": "admin@edificio.com",
  "observacion": "Edificio residencial"
}
```

### Apartamentos (`/api/apartamento`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/apartamento` | Obtener todos los apartamentos |
| GET | `/api/apartamento/{id}` | Obtener apartamento por ID |
| POST | `/api/apartamento` | Crear nuevo apartamento |
| PUT | `/api/apartamento/{id}` | Actualizar apartamento |
| DELETE | `/api/apartamento/{id}` | Eliminar apartamento |

**Ejemplo de cuerpo para POST:**
```json
{
  "nombre": "Apto 101",
  "precio": 250000,
  "edificio": {
    "id": 1
  },
  "observacion": "Apartamento esquinero"
}
```

### Espacios (`/api/espacio`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/espacio` | Obtener todos los espacios |
| GET | `/api/espacio/{id}` | Obtener espacio por ID |
| POST | `/api/espacio` | Crear nuevo espacio |
| PUT | `/api/espacio/{id}` | Actualizar espacio |
| DELETE | `/api/espacio/{id}` | Eliminar espacio |

### Tipos de Usuario (`/api/UsuarioTipo`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/UsuarioTipo` | Obtener todos los tipos |
| GET | `/api/UsuarioTipo/{id}` | Obtener tipo por ID |
| POST | `/api/UsuarioTipo` | Crear nuevo tipo |
| PUT | `/api/UsuarioTipo/{id}` | Actualizar tipo |
| DELETE | `/api/UsuarioTipo/{id}` | Eliminar tipo |

## 📁 Estructura del Proyecto

```
proyecto/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── proyecto/
│   │   │           ├── ProyectoApplication.java    # Clase principal
│   │   │           ├── config/
│   │   │           │   └── SecurityConfig.java     # Configuración de seguridad
│   │   │           ├── controller/                 # Controladores REST
│   │   │           │   ├── ApartamentoController.java
│   │   │           │   ├── EdificioController.java
│   │   │           │   ├── EspacioController.java
│   │   │           │   ├── UsuarioController.java
│   │   │           │   └── UsuarioTipoController.java
│   │   │           ├── entity/                     # Entidades JPA
│   │   │           │   ├── Apartamento.java
│   │   │           │   ├── Edificio.java
│   │   │           │   ├── Espacio.java
│   │   │           │   ├── Usuario.java
│   │   │           │   └── UsuarioTipo.java
│   │   │           ├── repository/                 # Repositorios JPA
│   │   │           │   ├── ApartamentoRepository.java
│   │   │           │   ├── EdificioRepository.java
│   │   │           │   ├── EspacioRepository.java
│   │   │           │   ├── UsuarioRepository.java
│   │   │           │   └── UsuarioTipoRepository.java
│   │   │           ├── service/                    # Lógica de negocio
│   │   │           │   ├── ApartamentoService.java
│   │   │           │   ├── EdificioService.java
│   │   │           │   ├── EspacioService.java
│   │   │           │   ├── UsuarioService.java
│   │   │           │   └── UsuarioTipoService.java
│   │   │           └── response/
│   │   │               └── Responses.java          # Respuestas estandarizadas
│   │   └── resources/
│   │       └── application.properties              # Configuración
│   └── test/                                       # Tests
├── pom.xml                                         # Dependencias Maven
├── mvnw                                            # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                        # Maven Wrapper (Windows)
└── README.md                                       # Este archivo
```

## 📊 Modelos de Datos

### Usuario
- `id`: Integer (PK, auto-increment)
- `correo`: String (2-30 caracteres)
- `contrasena`: String (2-20 caracteres)
- `tipo`: UsuarioTipo (FK)
- `observacion`: String (opcional, max 30 caracteres)

### Edificio
- `id`: Integer (PK, auto-increment)
- `nombre`: String (2-30 caracteres)
- `direccion`: String (2-20 caracteres)
- `ciudad`: String (opcional, max 30 caracteres)
- `ubicacion`: String (opcional, max 30 caracteres)
- `telefonoAdministrador`: String
- `correoAdministrador`: String
- `fechaRegistro`: LocalDateTime
- `observacion`: String (opcional, max 30 caracteres)

### Apartamento
- `id`: Integer (PK, auto-increment)
- `nombre`: String (2-30 caracteres)
- `precio`: Integer
- `edificio`: Edificio (FK)
- `observacion`: String (opcional, max 30 caracteres)

### Espacio
- `id`: Integer (PK, auto-increment)
- `nombre`: String (2-30 caracteres)
- `edificio`: Edificio (FK)
- `observacion`: String (opcional, max 30 caracteres)

### UsuarioTipo
- `id`: Integer (PK, auto-increment)
- `nombre`: String (2-30 caracteres)
- `observacion`: String (opcional, max 30 caracteres)

## 🔧 Solución de Problemas

### Error: "Access denied for user 'root'@'localhost'"

**Solución:**
1. Verifica las credenciales en `application.properties`
2. Asegúrate de que el usuario tiene permisos:
```sql
GRANT ALL PRIVILEGES ON proyecto.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### Error: "Communications link failure"

**Solución:**
1. Verifica que MariaDB/MySQL está corriendo:
```bash
sudo systemctl status mariadb
```
2. Inicia el servicio si está detenido:
```bash
sudo systemctl start mariadb
```

### Error: "Port 8080 already in use"

**Solución:**
1. Cambia el puerto en `application.properties`:
```properties
server.port=8081
```
2. O detén el proceso que usa el puerto 8080:
```bash
lsof -i :8080
kill -9 <PID>
```

### Ver logs de la aplicación

```bash
# Si ejecutaste con nohup
tail -f backend.log

# Ver últimas 100 líneas
tail -100 backend.log

# Buscar errores
grep -i error backend.log
```

## 📝 Notas Adicionales

- La aplicación crea automáticamente las tablas en la base de datos gracias a `hibernate.ddl-auto=update`
- Spring Security está configurado para permitir todas las peticiones sin autenticación (solo para desarrollo)
- Para producción, se recomienda:
  - Configurar autenticación y autorización adecuadas
  - Usar HTTPS
  - Cambiar `ddl-auto=validate` o `none`
  - Configurar CORS según sea necesario
  - Implementar límite de tasa (rate limiting)

## 👥 Equipo de Desarrollo

Proyecto desarrollado por el equipo FESC

## 📄 Licencia

Este proyecto es privado y confidencial.

---

**¿Necesitas ayuda?** Revisa la sección de [Solución de Problemas](#-solución-de-problemas) o contacta al equipo de desarrollo.
