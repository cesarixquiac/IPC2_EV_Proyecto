CREATE DATABASE OrdenArticuloEjercicio;
USE OrdenArticuloEjercicio;

CREATE TABLE Clientes (
    id_cliente INT PRIMARY KEY,
    nombre_cliente VARCHAR(50) ,
    ciudad VARCHAR(50) 
);

CREATE TABLE Orden (
    id_orden INT PRIMARY KEY,
    fecha DATE ,
    id_cliente INT ,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Telefono (
    id_telefono INT PRIMARY KEY,
    numero VARCHAR(20) 
);

CREATE TABLE OrdenTelefono (
    id_orden_telefono INT PRIMARY KEY, 
    id_orden INT ,
    id_telefono INT ,
    FOREIGN KEY (id_orden) REFERENCES Orden(id_orden),
    FOREIGN KEY (id_telefono) REFERENCES Telefono(id_telefono)
);

CREATE TABLE Articulo (
    id_articulo INT PRIMARY KEY,
    nombre_articulo VARCHAR(50) ,
    precio DECIMAL(10, 2) 
);

CREATE TABLE OrdenArticulo (
    id_orden INT ,
    id_articulo INT ,
    cantidad INT ,
    PRIMARY KEY (id_orden, id_articulo),
    FOREIGN KEY (id_orden) REFERENCES Orden(id_orden),
    FOREIGN KEY (id_articulo) REFERENCES Articulo(id_articulo)
);


INSERT INTO Clientes (id_cliente, nombre_cliente, ciudad) VALUES
(101, 'Luis', 'Quetzaltenango'),
(107, 'Brisa', 'Huehue');

INSERT INTO Orden (id_orden, fecha, id_cliente) VALUES
(2301, '2023-02-11', 101),
(2302, '2025-02-11', 107);

INSERT INTO Telefono (id_telefono, numero) VALUES
(1, '5565794'),
(2, '4896825');

INSERT INTO Articulo (id_articulo, nombre_articulo, precio) VALUES
(3786, 'Lapiz', 35.00),
(4011, 'Lapicero', 65.00);

INSERT INTO OrdenTelefono (id_orden_telefono, id_orden, id_telefono) VALUES
(101, 2301, 1),
(102, 2302, 2);

INSERT INTO OrdenArticulo (id_orden, id_articulo, cantidad) VALUES
(2301, 3786, 3),
(2302, 4011, 6);


-- Consulta para obtener los nombres de los clientes junto con las fechas de sus órdenes
SELECT 
    c.id_cliente,
    c.nombre_cliente,
    o.id_orden,
    o.fecha
FROM Clientes c
INNER JOIN Orden o
    ON c.id_cliente = o.id_cliente;

-- Consulta para obtener los articulos y sus cantidades en cada orden
SELECT 
    o.id_orden,
    a.nombre_articulo,
    oa.cantidad,
    a.precio
FROM Orden o
INNER JOIN OrdenArticulo oa
    ON o.id_orden = oa.id_orden
INNER JOIN Articulo a
    ON oa.id_articulo = a.id_articulo;

-- Consulta para obtener los teléfonos asociados a cada orden
SELECT 
    o.id_orden,
    t.numero
FROM Orden o
INNER JOIN OrdenTelefono ot
    ON o.id_orden = ot.id_orden
INNER JOIN Telefono t
    ON ot.id_telefono = t.id_telefono;


-- Consulta combinada para obtener nombres de clientes, órdenes y teléfonos
SELECT
    c.nombre_cliente,
    o.id_orden,
    t.numero
FROM Clientes c
INNER JOIN Orden o
    ON c.id_cliente = o.id_cliente
INNER JOIN OrdenTelefono ot 
    ON o.id_orden = ot.id_orden
INNER JOIN Telefono t
    ON ot.id_telefono = t.id_telefono;


-- Consulta completa para obtener toda la información relevante
SELECT
    c.nombre_cliente,
    c.ciudad,
    o.id_orden,
    o.fecha, 
    a.nombre_articulo,
    a.precio,
    oa.cantidad
FROM Clientes c
INNER JOIN Orden o
    ON c.id_cliente = o.id_cliente
INNER JOIN OrdenArticulo oa
    ON o.id_orden = oa.id_orden
INNER JOIN Articulo a
    ON oa.id_articulo = a.id_articulo 
    WHERE c.id_cliente = 101;



