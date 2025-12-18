-- ==========================================================
-- SCRIPT DE BASE DE DATOS: PLATAFORMA DE VIDEOJUEGOS
-- FECHA: Diciembre 2025
-- TIPO: Proyecto Académico Robusto
-- ==========================================================

DROP DATABASE IF EXISTS GameStoreDB;
CREATE DATABASE GameStoreDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE GameStoreDB;

-- ==========================================================
-- 1. MÓDULO DE SEGURIDAD Y ACCESO
-- ==========================================================

-- Tabla Padre: Credenciales y Roles
CREATE TABLE Usuario_Auth (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'EMPRESA', 'GAMER') NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla Hija 1: Perfil del Jugador (1:1 con Usuario_Auth)
CREATE TABLE Perfil_Gamer (
    gamer_id INT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    pais_iso CHAR(3) NOT NULL DEFAULT 'GTM',
    telefono VARCHAR(15),
    avatar_url VARCHAR(255),
    saldo_actual DECIMAL(10,2) NOT NULL DEFAULT 0.00 CHECK (saldo_actual >= 0),
    FOREIGN KEY (gamer_id) REFERENCES Usuario_Auth(user_id) ON DELETE CASCADE
);

-- ==========================================================
-- 2. MÓDULO DE NEGOCIO (EMPRESAS)
-- ==========================================================

-- Entidad: Configuración Singleton (Solo 1 fila)
CREATE TABLE Configuracion_Sistema (
    id INT PRIMARY KEY,
    comision_global_pct DECIMAL(5,2) NOT NULL DEFAULT 15.00,
    ultima_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Insertar la configuración inicial obligatoria
INSERT INTO Configuracion_Sistema (id, comision_global_pct) VALUES (1, 15.00);

-- Entidad: Empresa Desarrolladora
CREATE TABLE Empresa_Desarrolladora (
    empresa_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_comercial VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    -- NULL significa que usa la global. Si tiene valor, usa este.
    comision_pactada DECIMAL(5,2) NULL, 
    fecha_alta DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Hija 2: Perfil Empleado de Empresa (1:1 con Usuario_Auth)
CREATE TABLE Perfil_Empleado (
    empleado_id INT PRIMARY KEY,
    empresa_id INT NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    FOREIGN KEY (empleado_id) REFERENCES Usuario_Auth(user_id) ON DELETE CASCADE,
    FOREIGN KEY (empresa_id) REFERENCES Empresa_Desarrolladora(empresa_id)
);

-- ==========================================================
-- 3. MÓDULO DE CATÁLOGO
-- ==========================================================

CREATE TABLE Categoria (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Videojuego (
    juego_id INT AUTO_INCREMENT PRIMARY KEY,
    empresa_id INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio_base DECIMAL(10,2) NOT NULL CHECK (precio_base >= 0),
    clasificacion_edad ENUM('E', 'T', 'M', 'AO') NOT NULL, -- E=Everyone, T=Teen, M=Mature...
    recursos_minimos TEXT,
    estado_venta ENUM('ACTIVO', 'SUSPENDIDO') DEFAULT 'ACTIVO',
    fecha_publicacion DATE,
    FOREIGN KEY (empresa_id) REFERENCES Empresa_Desarrolladora(empresa_id)
);

-- Tabla Intermedia: Muchos a Muchos (Juegos <-> Categorías)
CREATE TABLE Juego_Categoria (
    juego_id INT,
    categoria_id INT,
    PRIMARY KEY (juego_id, categoria_id),
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES Categoria(categoria_id)
);

CREATE TABLE Recurso_Multimedia (
    media_id INT AUTO_INCREMENT PRIMARY KEY,
    juego_id INT NOT NULL,
    url_archivo VARCHAR(255) NOT NULL,
    tipo ENUM('PORTADA', 'GALERIA', 'VIDEO') NOT NULL,
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id) ON DELETE CASCADE
);

-- ==========================================================
-- 4. MÓDULO TRANSACCIONAL Y FINANCIERO (CRÍTICO)
-- ==========================================================

-- Historial de movimientos de dinero (Auditoría)
CREATE TABLE Cartera_Transaccion (
    transaccion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gamer_id INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL, -- Puede ser negativo para retiros/compras
    tipo_movimiento ENUM('RECARGA', 'COMPRA', 'REEMBOLSO') NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gamer_id) REFERENCES Perfil_Gamer(gamer_id)
);

-- Entidad Asociativa: La "Copia" digital del juego
CREATE TABLE Biblioteca_Licencia (
    licencia_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gamer_id INT NOT NULL, -- Dueño
    juego_id INT NOT NULL, -- Juego
    fecha_adquisicion DATETIME DEFAULT CURRENT_TIMESTAMP,
    origen ENUM('COMPRA_PROPIA', 'REGALO') DEFAULT 'COMPRA_PROPIA',
    FOREIGN KEY (gamer_id) REFERENCES Perfil_Gamer(gamer_id),
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id)
);

-- Detalle de Venta: Snapshot de precios históricos
CREATE TABLE Venta_Detalle (
    venta_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    licencia_id BIGINT NOT NULL UNIQUE, -- 1 a 1 con Licencia
    precio_momento DECIMAL(10,2) NOT NULL,
    comision_global_snapshot DECIMAL(5,2) NOT NULL,
    comision_especifica_snapshot DECIMAL(5,2) NULL,
    ganancia_neta_empresa DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (licencia_id) REFERENCES Biblioteca_Licencia(licencia_id)
);

-- ==========================================================
-- 5. MÓDULO SOCIAL Y FAMILIAR
-- ==========================================================

CREATE TABLE Grupo_Familiar (
    grupo_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    creador_gamer_id INT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (creador_gamer_id) REFERENCES Perfil_Gamer(gamer_id)
);

-- Tabla Intermedia: Miembros del grupo
CREATE TABLE Miembro_Grupo (
    grupo_id INT,
    gamer_id INT,
    fecha_union DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (grupo_id, gamer_id),
    FOREIGN KEY (grupo_id) REFERENCES Grupo_Familiar(grupo_id),
    FOREIGN KEY (gamer_id) REFERENCES Perfil_Gamer(gamer_id)
);

-- Tabla Lógica: Control de Préstamos (La regla de 1 instalado)
CREATE TABLE Control_Prestamo (
    prestamo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gamer_solicitante_id INT NOT NULL, -- Quien pide prestado
    licencia_prestada_id BIGINT NOT NULL, -- Qué copia usa
    estado_instalacion ENUM('INSTALADO', 'NO_INSTALADO') NOT NULL DEFAULT 'NO_INSTALADO',
    fecha_ultimo_cambio DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (gamer_solicitante_id) REFERENCES Perfil_Gamer(gamer_id),
    FOREIGN KEY (licencia_prestada_id) REFERENCES Biblioteca_Licencia(licencia_id)
);

CREATE TABLE Resena (
    resena_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gamer_id INT NOT NULL,
    juego_id INT NOT NULL,
    calificacion TINYINT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    visible_texto BOOLEAN DEFAULT TRUE,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gamer_id) REFERENCES Perfil_Gamer(gamer_id),
    FOREIGN KEY (juego_id) REFERENCES Videojuego(juego_id)
);