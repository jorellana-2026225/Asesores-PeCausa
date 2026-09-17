USE AsesoresPeCausa_in4av;
INSERT INTO usuario (
    id_usuario,
    nombre_usuario,
    clave,
    rol
)
VALUES
(
    '11111111-1111-1111-1111-111111111111',
    'admin',
    'admin123',
    'ADMINISTRADOR'
),
(
    '22222222-2222-2222-2222-222222222222',
    'abogado1',
    'abogado123',
    'ABOGADO'
),
(
    '33333333-3333-3333-3333-333333333333',
    'abogado2',
    'abogado456',
    'ABOGADO'
);
INSERT INTO cliente (
    id_cliente,
    tipo_persona,
    nombre_completo,
    documento_identidad,
    nit_empresa,
    telefono_principal,
    correo_electronico,
    direccion_fisica
)
VALUES
(
    'AAAAAAA1-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    'INDIVIDUAL',
    'Carlos Lopez',
    '1234567890101',
    NULL,
    '55554444',
    'carlos@gmail.com',
    'Zona 1, Ciudad de Guatemala'
),
(
    'AAAAAAA2-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    'EMPRESA',
    'Comercial XYZ S.A.',
    '987654321',
    '1234567-8',
    '22223333',
    'contacto@xyz.com',
    'Zona 10, Ciudad de Guatemala'
),
(
    'AAAAAAA3-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    'INDIVIDUAL',
    'Maria Perez',
    '4567891230101',
    NULL,
    '44445555',
    'maria@gmail.com',
    'Zona 7, Ciudad de Guatemala'
);
INSERT INTO expediente (
    id_expediente,
    id_cliente,
    id_abogado,
    numero_expediente,
    titulo,
    descripcion,
    estado,
    fecha
)
VALUES
(
    'EXP11111-1111-1111-1111-111111111111',
    'AAAAAAA1-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    '22222222-2222-2222-2222-222222222222',
    'EXP-001',
    'Caso Laboral',
    'Demanda por despido injustificado',
    'ABIERTO',
    '2026-01-10'
),
(
    'EXP22222-2222-2222-2222-222222222222',
    'AAAAAAA2-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    '33333333-3333-3333-3333-333333333333',
    'EXP-002',
    'Cobro Judicial',
    'Proceso de recuperacion de deuda',
    'EN_PROCESO',
    '2026-02-15'
),
(
    'EXP33333-3333-3333-3333-333333333333',
    'AAAAAAA3-AAAA-AAAA-AAAA-AAAAAAAAAAAA',
    '22222222-2222-2222-2222-222222222222',
    'EXP-003',
    'Caso Civil',
    'Reclamacion por incumplimiento de contrato',
    'CERRADO',
    '2026-03-20'
);
INSERT INTO actuaciones (
    id_actuacion,
    id_expediente,
    id_usuario,
    titulo,
    descripcion,
    fecha_actuacion,
    archivo_adjunto
)
VALUES
(
    'ACT11111-1111-1111-1111-111111111111',
    'EXP11111-1111-1111-1111-111111111111',
    '22222222-2222-2222-2222-222222222222',
    'Apertura de Expediente',
    'Se registra el expediente en el sistema',
    '2026-01-10',
    'apertura.pdf'
),
(
    'ACT22222-2222-2222-2222-222222222222',
    'EXP11111-1111-1111-1111-111111111111',
    '22222222-2222-2222-2222-222222222222',
    'Primera Audiencia',
    'Se realizo la primera audiencia',
    '2026-01-25',
    'audiencia.pdf'
),
(
    'ACT33333-3333-3333-3333-333333333333',
    'EXP11111-1111-1111-1111-111111111111',
    '33333333-3333-3333-3333-333333333333',
    'Presentacion de Pruebas',
    'Se adjuntan pruebas documentales',
    '2026-02-05',
    'pruebas.pdf'
),
(
    'ACT44444-4444-4444-4444-444444444444',
    'EXP22222-2222-2222-2222-222222222222',
    '33333333-3333-3333-3333-333333333333',
    'Notificacion',
    'Se notifica a la parte demandada',
    '2026-02-20',
    'notificacion.pdf'
),
(
    'ACT55555-5555-5555-5555-555555555555',
    'EXP33333-3333-3333-3333-333333333333',
    '22222222-2222-2222-2222-222222222222',
    'Resolucion Final',
    'El caso ha sido concluido',
    '2026-04-10',
    'resolucion.pdf'
);
CALL sp_buscar_actuacion(
    'ACT11111-1111-1111-1111-111111111111'
);
CALL sp_ver_historial_cronologico(
    'EXP11111-1111-1111-1111-111111111111'
);
select * from usuario where nombre_usuario = "admin";