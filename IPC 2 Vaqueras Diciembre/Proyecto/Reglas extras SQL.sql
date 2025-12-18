--Evitar compras duplicadas del mismo juego por el mismo gamer
ALTER TABLE Biblioteca_Licencia
ADD CONSTRAINT uq_gamer_juego UNIQUE (gamer_id, juego_id);

--Una sola reseña por gamer por juego
ALTER TABLE Resena
ADD CONSTRAINT uq_resena UNIQUE (gamer_id, juego_id);

--Modulo financiero Ajuste
ALTER TABLE Venta_Detalle
ADD transaccion_id BIGINT NOT NULL,
ADD FOREIGN KEY (transaccion_id)
REFERENCES Cartera_Transaccion(transaccion_id);
