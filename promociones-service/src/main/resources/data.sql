-- Insertar promociones de prueba
INSERT INTO promociones (codigo, descripcion, tipo, valor_descuento, monto_minimo,
                         cantidad_usos_maximos, cantidad_usos_actuales, activo,
                         fecha_inicio, fecha_fin, categoria_valida)
VALUES
    ('VERANO2026', 'Descuento de verano 15%', 'PORCENTAJE', 15.00, 1000.00, 1000, 0, true,
     '2026-01-01 00:00:00', '2026-12-31 23:59:59', NULL);

INSERT INTO promociones (codigo, descripcion, tipo, valor_descuento, monto_minimo,
                         cantidad_usos_maximos, cantidad_usos_actuales, activo,
                         fecha_inicio, fecha_fin, categoria_valida)
VALUES
    ('CLIENTE-VIP', 'Descuento VIP 25%', 'PORCENTAJE', 25.00, NULL, NULL, 0, true,
     '2026-01-01 00:00:00', '2027-12-31 23:59:59', NULL);

INSERT INTO promociones (codigo, descripcion, tipo, valor_descuento, monto_minimo,
                         cantidad_usos_maximos, cantidad_usos_actuales, activo,
                         fecha_inicio, fecha_fin, categoria_valida)
VALUES
    ('PRIMERA-COMPRA', 'Descuento primer compra 10%', 'PORCENTAJE', 10.00, 1000.00, 500, 0, true,
     '2026-01-01 00:00:00', '2026-12-31 23:59:59', NULL);

INSERT INTO promociones (codigo, descripcion, tipo, valor_descuento, monto_minimo,
                         cantidad_usos_maximos, cantidad_usos_actuales, activo,
                         fecha_inicio, fecha_fin, categoria_valida)
VALUES
    ('REFERIDO', 'Descuento por referencia $50', 'MONTO_FIJO', 50.00, 500.00, NULL, 0, true,
     '2026-01-01 00:00:00', '2027-12-31 23:59:59', NULL);