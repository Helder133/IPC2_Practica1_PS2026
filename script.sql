CREATE DATABASE Practica1 DEFAULT CHARACTER SET = 'utf8mb4';

USE Practica1;

CREATE TABLE IF NOT EXISTS sucursal (
    sucursal_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(250) NOT NULL,
    contacto CHAR(8) NOT NULL,
    ubicacion VARCHAR(250) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    sucursal_id INT,
    nombre VARCHAR(250) NOT NULL ,
    email VARCHAR(250) NOT NULL UNIQUE ,
    contrasena VARCHAR(250) NOT NULL ,
    rol ENUM ('JUGADOR', 'ADMIN_TIENDA', 'SUPER_ADMIN') DEFAULT 'JUGADOR',
    estado BOOL NOT NULL DEFAULT 1,
    CONSTRAINT fk_sucursal1 FOREIGN KEY (sucursal_id) REFERENCES sucursal (sucursal_id)
);

CREATE TABLE IF NOT EXISTS partida (
    partida_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    usuario_id INT NOT NULL ,
    sucursal_id INT NOT NULL ,
    nivel INT NOT NULL DEFAULT 1 CHECK ( nivel BETWEEN 1 AND 3) ,
    puntaje INT NOT NULL DEFAULT 0 ,
    fecha_inicio DATETIME NOT NULL ,
    fecha_fin DATETIME ,
    CONSTRAINT fk_sucursal2 FOREIGN KEY (sucursal_id) REFERENCES sucursal (sucursal_id),
    CONSTRAINT fk_usuario1 FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
);

CREATE TABLE IF NOT EXISTS ingrediente (
    ingrediente_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    nombre VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS producto (
    producto_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    nombre VARCHAR(250) NOT NULL UNIQUE,
    tiempo_base_preparacion INT NOT NULL DEFAULT 60
);

CREATE TABLE IF NOT EXISTS producto_ingrediente (
    producto_id INT NOT NULL ,
    ingrediente_id INT NOT NULL ,
    CONSTRAINT fk_producto1 FOREIGN KEY (producto_id) REFERENCES producto (producto_id),
    CONSTRAINT fk_ingrediente1 FOREIGN KEY (ingrediente_id) REFERENCES ingrediente (ingrediente_id),
    CONSTRAINT pk_producto_ingrediente PRIMARY KEY (producto_id, ingrediente_id)
);

CREATE TABLE IF NOT EXISTS menu (
    menu_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    sucursal_id INT NOT NULL ,
    CONSTRAINT fk_sucursal3 FOREIGN KEY (sucursal_id) REFERENCES sucursal (sucursal_id)
);

CREATE TABLE IF NOT EXISTS menu_producto (
    menu_id INT NOT NULL ,
    producto_id INT NOT NULL ,
    estado BOOL NOT NULL DEFAULT 1,
    CONSTRAINT fk_menu FOREIGN KEY (menu_id) REFERENCES menu (menu_id),
    CONSTRAINT fk_producto2 FOREIGN KEY (producto_id) REFERENCES producto (producto_id),
    CONSTRAINT pk_menu_producto PRIMARY KEY (menu_id, producto_id)
);

CREATE TABLE IF NOT EXISTS sucursal_ingrediente (
    sucursal_id INT NOT NULL ,
    ingrediente_id INT NOT NULL ,
    estado BOOL DEFAULT 1,
    CONSTRAINT fk_sucursal4 FOREIGN KEY (sucursal_id) REFERENCES sucursal (sucursal_id),
    CONSTRAINT fk_ingrediente2 FOREIGN KEY (ingrediente_id) REFERENCES ingrediente (ingrediente_id),
    CONSTRAINT pk_sucursal_ingrediente PRIMARY KEY (sucursal_id, ingrediente_id)
);

CREATE TABLE IF NOT EXISTS pedido (
    pedido_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    partida_id INT NOT NULL ,
    tiempo_limite INT NOT NULL ,
    fecha_creacion DATETIME NOT NULL ,
    fecha_entrega DATETIME ,
    estado ENUM ('Recibido','Preparando','En_Horno','Lista/Entregado','Cancelado','No_Entregado') NOT NULL DEFAULT 'Recibido',
    puntaje_obtenido INT DEFAULT 0,
    CONSTRAINT fk_partida1 FOREIGN KEY (partida_id) REFERENCES partida (partida_id)
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
    pedido_id INT NOT NULL ,
    producto_id INT NOT NULL ,
    cantidad INT NOT NULL DEFAULT 1 ,
    CONSTRAINT fk_pedido1 FOREIGN KEY (pedido_id) REFERENCES pedido (pedido_id),
    CONSTRAINT fk_producto3 FOREIGN KEY (producto_id) REFERENCES producto (producto_id),
    CONSTRAINT pk_detalle_pedido PRIMARY KEY (pedido_id, producto_id)
);

CREATE TABLE IF NOT EXISTS historial_pedido (
    historial_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    pedido_id INT NOT NULL ,
    fecha DATETIME NOT NULL ,
    estado ENUM ('Recibido','Preparando','En_Horno','Lista/Entregado','Cancelado','No_Entregado') NOT NULL DEFAULT 'Recibido',
    CONSTRAINT fk_pedido2 FOREIGN KEY (pedido_id) REFERENCES pedido (pedido_id)
);

CREATE TABLE IF NOT EXISTS configuracion_sistema (
    configuracion_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    tiempo_preparacion INT NOT NULL DEFAULT 60,
    dificultad_nivel INT NOT NULL DEFAULT 10,
    punteo_minimo INT NOT NULL DEFAULT 600 ,
    completo INT NOT NULL DEFAULT 100,
    completo_optimo INT NOT NULL DEFAULT 50,
    completo_eficiente DECIMAL (10,2) DEFAULT 0.5,
    cancelado INT NOT NULL DEFAULT 30 ,
    no_entregado INT NOT NULL DEFAULT 50
);

