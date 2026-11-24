-- ========================================================
-- DATOS SEMILLA: CENTRO SANAESENCIA
-- ========================================================

-- Información Institucional
INSERT INTO about_info (id, center_title, description, address, phone, email)
VALUES (
    1,
    'Centro Integrativo Sanaesencia',
    'Salud mental infanto-juvenil, familia y bienestar integrativo. Atendemos de forma presencial en nuestros 8 boxes de Los Vilos y online para todo Chile y el extranjero.',
    'Caupolicán 220, Los Vilos',
    '+56 9 2366 5073',
    'contacto@centrosanaesencia.cl'
) ON CONFLICT (id) DO NOTHING;

-- Boxes 1 al 8
INSERT INTO boxes (box_number, name, status, description) VALUES
(1, 'Box 1 - Medicina General', 'disponible', 'Box equipado para evaluación clínica integral'),
(2, 'Box 2 - Fonoaudiología', 'disponible', 'Box acústico para terapia fonoaudiológica'),
(3, 'Box 3 - Psicología Infantil', 'disponible', 'Box lúdico para terapia infanto-juvenil'),
(4, 'Box 4 - Psicología Adultos', 'disponible', 'Espacio terapéutico para salud mental'),
(5, 'Box 5 - Terapia Ocupacional', 'disponible', 'Box con equipamiento sensorial motor'),
(6, 'Box 6 - Nutrición y Bienestar', 'disponible', 'Box de evaluación nutricional'),
(7, 'Box 7 - Terapias Florales', 'disponible', 'Espacio para terapias holísticas y bienestar'),
(8, 'Box 8 - Consulta Multidisciplinaria', 'disponible', 'Box de atención familiar y consultas compartidas')
ON CONFLICT (box_number) DO NOTHING;

-- Usuarios Iniciales (Contraseñas hasheadas con BCrypt: 'admin', '1234', 'Admin123' y 'Pass123')
INSERT INTO users (run, name, email, phone, password_hash, role, specialty) VALUES
('admin', 'Administrador Centro Sanaesencia', 'admin@sanaesencia.cl', '+56923665073', '$2a$10$4CXqaQqpEDrzryJuEeBJRODdzAqT0V/hDGb1zBKTOuXpmTMk.n9/.', 'ADMIN', 'Gestión Clínica'),
('11111111-1', 'Administrador Sanaesencia (RUT)', 'admin.rut@sanaesencia.cl', '+56923665073', '$2a$10$5qQhZ5xDHIG0Y0vfHZZU5.RiM43ou33Xa.QtIYzlnDpwelng.OzMC', 'ADMIN', 'Gestión Clínica'),
('20000001-3', 'Dra. Keilen Sánchez Ríos', 'keilen.sanchez@sanaesencia.cl', '+56923665073', '$2a$10$X1KAlbCLRFpEzBCadDTPg.Fp.yqhEc2PudK7JZakAuY9ISrAK/YIG', 'PROFESIONAL', 'Medicina General y Salud Mental'),
('15234567-8', 'Ps. Rodrigo Morales', 'rmorales@sanaesencia.cl', '+56912345678', '$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm', 'PROFESIONAL', 'Psicología Infanto-Juvenil'),
('16345678-9', 'Flga. Camila Vega', 'cvega@sanaesencia.cl', '+56987654321', '$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm', 'PROFESIONAL', 'Fonoaudiología'),
('17456789-0', 'Nut. Valentina Araya', 'varaya@sanaesencia.cl', '+56976543210', '$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm', 'PROFESIONAL', 'Nutrición Integrativa'),
('20000100-1', 'Paciente de Prueba', 'paciente@correo.com', '+56987654321', '$2a$10$X1KAlbCLRFpEzBCadDTPg.Fp.yqhEc2PudK7JZakAuY9ISrAK/YIG', 'PACIENTE', ''),
('18567890-1', 'Constanza Silva', 'paciente@test.cl', '+56911223344', '$2a$10$KnoTGcvA1U2BhvMgrIi7Mu254HdudzFdLUI17MoPMx.zOPgPYjLzm', 'PACIENTE', '')
ON CONFLICT (run) DO NOTHING;

-- Catálogo de Productos Terapéuticos
INSERT INTO products (name, description, price, is_available, image_url, stock) VALUES
('Aceite Esencial Lavanda Francesa (15ml)', '100% puro para relajación del sistema nervioso y mejora del descanso nocturno.', 12990.00, true, 'https://centrosanaesencia.cl/img/lavanda.jpg', 25),
('Jabón Artesanal de Ruda y Romero', 'Elaborado con aceites vegetales nutritivos y hierbas recolectadas del valle.', 4500.00, true, 'https://centrosanaesencia.cl/img/jabon_ruda.jpg', 40),
('Infusión Herbal Digestiva & Calmante (80g)', 'Mezcla sinérgica de melisa, manzanilla, menta negra y cedrón orgánico.', 6200.00, true, 'https://centrosanaesencia.cl/img/infusion_calmante.jpg', 30),
('Bruma Áurica Armonizadora (100ml)', 'Spray ambiental con esencias florales y aceites cítricos para limpiar espacios.', 8900.00, true, 'https://centrosanaesencia.cl/img/bruma.jpg', 15),
('Roll-on Anti-Estrés y Foco (10ml)', 'Aplicador de aromaterapia con bergamota, incienso y jojoba para sienes y muñecas.', 7500.00, true, 'https://centrosanaesencia.cl/img/rollon.jpg', 50)
ON CONFLICT (id) DO NOTHING;

-- Reseñas y Testimonios
INSERT INTO reviews (patient_name, content, rating, date) VALUES
('María José Pinto', 'La atención en Los Vilos con la fonoaudióloga fue excelente para mi hijo. La calidez del espacio y los boxes es insuperable.', 5, '2026-11-15'),
('Cristóbal Valenzuela', 'Muy agradecido del enfoque integral de salud mental. Excelente coordinación y puntualidad.', 5, '2026-11-20')
ON CONFLICT (id) DO NOTHING;
