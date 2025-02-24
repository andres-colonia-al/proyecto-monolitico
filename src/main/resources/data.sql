INSERT INTO TARJETA_REGALO (codigo_unico, monto, fecha_creacion, fecha_final, status) VALUES
('GC1001XYZ', 50.00, NOW(), NOW() + INTERVAL '1 year', 'ACTIVA'),
('GC1002ABC', 100.00, NOW(), NOW() + INTERVAL '6 months', 'ACTIVA'),
('GC1003DEF', 25.50, NOW(), NOW() + INTERVAL '3 months', 'ACTIVA'),
('GC1004GHI', 200.00, NOW(), NOW() + INTERVAL '1 year', 'REDIMIDA'),
('GC1005JKL', 75.75, NOW(), NOW() + INTERVAL '9 months', 'EXPIRADA'),
('GC1006MNO', 150.00, NOW(), NOW() + INTERVAL '1 year', 'ACTIVA'),
('GC1007PQR', 30.00, NOW(), NOW() + INTERVAL '4 months', 'ACTIVA'),
('GC1008STU', 500.00, NOW(), NOW() + INTERVAL '2 years', 'ACTIVA'),
('GC1009VWX', 10.00, NOW(), NOW() + INTERVAL '2 months', 'REDIMIDA'),
('GC1010YZA', 120.00, NOW(), NOW() + INTERVAL '8 months', 'ACTIVA');

INSERT INTO usuario (cedula, nombre, apellido, fecha_cumpleaños, username, contraseña, email, role) VALUES
(123456789, 'Prueba-User', 'P1', '1996-05-15', 'pruebauser', '$2a$10$kLMLNxkkVCjNFjlAT2oLee4YQ9KYv/rPQcaRSOSHlxjWJiQvSrXNm', 'andres@example.com', 'USER'),
(987654321, 'Prueba-Admin', 'P2', '1995-08-22', 'pruebaadmin', '$2a$10$kLMLNxkkVCjNFjlAT2oLee4YQ9KYv/rPQcaRSOSHlxjWJiQvSrXNm', 'felipe@example.com', 'ADMIN');

