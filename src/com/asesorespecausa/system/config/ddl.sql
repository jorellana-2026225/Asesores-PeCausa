DROP DATABASE IF EXISTS AsesoresPeCausa_in4av;
CREATE DATABASE AsesoresPeCausa_in4av;
USE AsesoresPeCausa_in4av;

-- USUARIO
CREATE TABLE usuario (
    id_usuario CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre_usuario VARCHAR(50) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'ABOGADO') NOT NULL
);


 #CRUD USUARIO
-- =====================================================================

#Crear
DELIMITER $

CREATE PROCEDURE sp_crear_usuario(
    IN nombre_usuario_p VARCHAR(50),
    IN clave_p VARCHAR(255),
    IN rol_p ENUM('ADMINISTRADOR','ABOGADO')
)
BEGIN
    INSERT INTO usuario(
        nombre_usuario,
        clave,
        rol
    )
    VALUES(
        nombre_usuario_p,
        clave_p,
        rol_p
    );
END $

DELIMITER ;


#Editar
DELIMITER $

CREATE PROCEDURE sp_editar_usuario(
    IN id_usuario_p CHAR(36),
    IN nombre_usuario_p VARCHAR(50),
    IN clave_p VARCHAR(255),
    IN rol_p ENUM('ADMINISTRADOR','ABOGADO')
)
BEGIN
    UPDATE usuario
    SET
        nombre_usuario = nombre_usuario_p,
        clave = clave_p,
        rol = rol_p
    WHERE id_usuario = id_usuario_p;
END $

DELIMITER ;


-- =====================================================================
-- CLIENTE 

CREATE TABLE cliente (
    id_cliente CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    tipo_persona VARCHAR(20) NOT NULL,
    nombre_completo VARCHAR(150) NOT NULL,
    documento_identidad VARCHAR(20) NOT NULL,
    nit_empresa VARCHAR(20),
    telefono_principal VARCHAR(20) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL,
    direccion_fisica TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- CRUD CLIENTE
-- =====================================================================

#Crear
DELIMITER $$

CREATE PROCEDURE sp_crear_cliente (
    IN tipo_persona_p VARCHAR(20),
    IN nombre_completo_p VARCHAR(150),
    IN documento_identidad_p VARCHAR(20),
    IN nit_empresa_p VARCHAR(20),
    IN telefono_principal_p VARCHAR(20),
    IN correo_electronico_p VARCHAR(150),
    IN direccion_fisica_p TEXT
)
BEGIN
    INSERT INTO cliente (
        tipo_persona,
        nombre_completo,
        documento_identidad,
        nit_empresa,
        telefono_principal,
        correo_electronico,
        direccion_fisica
    )
    VALUES (
        tipo_persona_p,
        nombre_completo_p,
        documento_identidad_p,
        nit_empresa_p,
        telefono_principal_p,
        correo_electronico_p,
        direccion_fisica_p
    );

END $$

DELIMITER ;


#Eliminar
DELIMITER $$

CREATE PROCEDURE sp_eliminar_cliente(
    IN id_cliente_p CHAR(36)
)
BEGIN
    DELETE FROM cliente
    WHERE id_cliente = id_cliente_p;
END $$

DELIMITER ;


#Editar
DELIMITER $$

CREATE PROCEDURE sp_editar_cliente	(
    IN id_cliente_p CHAR(36),
    IN tipo_persona_p VARCHAR(20),
    IN nombre_completo_p VARCHAR(150),
    IN documento_identidad_p VARCHAR(50),
    IN nit_empresa_p VARCHAR(50),
    IN telefono_principal_p VARCHAR(20),
    IN correo_electronico_p VARCHAR(100),
    IN direccion_fisica_p TEXT
)
BEGIN
    UPDATE cliente 
    SET
        tipo_persona = tipo_persona_p,
        nombre_completo = nombre_completo_p,
        documento_identidad = documento_identidad_p,
        nit_empresa = nit_empresa_p, 
        telefono_principal = telefono_principal_p, 
        correo_electronico = correo_electronico_p,
        direccion_fisica = direccion_fisica_p
    WHERE id_cliente = id_cliente_p;

END$$

DELIMITER ;


#Buscar
DELIMITER $$

CREATE PROCEDURE sp_buscar_cliente(
    IN nombre_p VARCHAR(150)
)
BEGIN
    SELECT * FROM cliente
    WHERE nombre_completo LIKE CONCAT('%', nombre_p, '%'); 		
END $$

DELIMITER ;


-- =====================================================================
-- EXPEDIENTE
 
CREATE TABLE expediente (
    id_expediente CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_cliente CHAR(36) NOT NULL,
    id_abogado CHAR(36) NOT NULL,

    numero_expediente VARCHAR(50) NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,

    estado ENUM(
        'ABIERTO',
        'EN_PROCESO',
        'CERRADO'
    ) NOT NULL DEFAULT 'ABIERTO',
    
    fecha DATE NOT NULL,

    CONSTRAINT fk_expediente_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES cliente(id_cliente),

    CONSTRAINT fk_expediente_abogado
        FOREIGN KEY (id_abogado)
        REFERENCES usuario(id_usuario)
);


-- CRUD EXPEDIENTE
-- =====================================================================


#Crear
DELIMITER $$

CREATE PROCEDURE sp_crear_expediente(
    IN id_cliente_p CHAR(36),
    IN id_abogado_p CHAR(36),
    IN numero_expediente_p VARCHAR(50),
    IN titulo_p VARCHAR(150),
    IN descripcion_p TEXT,
    IN estado_p ENUM('ABIERTO', 'EN_PROCESO', 'CERRADO'),
    IN fecha_p DATE
)
BEGIN
    INSERT INTO expediente (
        id_cliente,
        id_abogado,
        numero_expediente,
        titulo,
        descripcion,
        estado,
        fecha
    )
    VALUES (
        id_cliente_p,
        id_abogado_p,
        numero_expediente_p,
        titulo_p,
        descripcion_p,
        estado_p,
        fecha_p
    );
END $$

DELIMITER ;


#Eliminar
DELIMITER $$

CREATE PROCEDURE eliminar_expediente(
    IN id_expediente_p CHAR(36)
)
BEGIN
    DELETE FROM expediente
    WHERE id_expediente = id_expediente_p;
END $$

DELIMITER ;


#Editar
DELIMITER $$

CREATE PROCEDURE sp_editar_expediente(
    IN id_expediente_p CHAR(36),
    IN id_cliente_p CHAR(36),
    IN id_abogado_p CHAR(36),
    IN numero_expediente_p VARCHAR(50),
    IN titulo_p VARCHAR(150),
    IN descripcion_p TEXT,
    IN estado_p ENUM('ABIERTO', 'EN_PROCESO', 'CERRADO'),
    IN fecha_p DATE
)
BEGIN

    IF NOT EXISTS (
        SELECT 1
        FROM expediente
        WHERE id_expediente = id_expediente_p
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El expediente no existe';

    ELSE

        UPDATE expediente
        SET
            id_cliente = id_cliente_p,
            id_abogado = id_abogado_p,
            numero_expediente = numero_expediente_p,
            titulo = titulo_p,
            descripcion = descripcion_p,
            estado = estado_p,
            fecha = fecha_p
        WHERE id_expediente = id_expediente_p;

    END IF;

END $$

DELIMITER ;


#Buscar
DELIMITER $$

CREATE PROCEDURE sp_buscar_expediente (
    IN id_expediente_p CHAR(36)
)
BEGIN

    IF NOT EXISTS (
        SELECT 1 
        FROM expediente 
        WHERE id_expediente = id_expediente_p
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El expediente no existe';

    ELSE

        SELECT
            id_expediente,
            id_cliente,
            id_abogado,
            numero_expediente,
            titulo,
            descripcion,
            estado,
            fecha
        FROM expediente
        WHERE id_expediente = id_expediente_p;

    END IF;

END$$

DELIMITER ;



#Vincular


#HistorialCronologico


-- =====================================================================
-- ACTUACIONES

CREATE TABLE actuaciones (
    id_actuacion CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_expediente CHAR(36) NOT NULL,
    id_usuario CHAR(36) NOT NULL,

    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha_actuacion DATE NOT NULL,
    archivo_adjunto VARCHAR(255),

    CONSTRAINT fk_actuacion_expediente
        FOREIGN KEY (id_expediente)
        REFERENCES expediente(id_expediente),

    CONSTRAINT fk_actuacion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);


-- CRUD ACTUACIONES
-- =====================================================================

#Crear

DELIMITER $$

CREATE PROCEDURE sp_crear_actuacion(
    IN id_expediente_p CHAR(36),
    IN id_usuario_p CHAR(36),
    IN titulo_p VARCHAR(150),
    IN descripcion_p TEXT,
    IN fecha_actuacion_p DATE,
    IN archivo_adjunto_p VARCHAR(255)
)
BEGIN

    INSERT INTO actuaciones (
        id_expediente,
        id_usuario,
        titulo,
        descripcion,
        fecha_actuacion,
        archivo_adjunto
    )
    VALUES (
        id_expediente_p,
        id_usuario_p,
        titulo_p,
        descripcion_p,
        fecha_actuacion_p,
        archivo_adjunto_p
    );

END $$

DELIMITER ;


#Eliminar

DELIMITER $$

CREATE PROCEDURE sp_eliminar_actuacion(
    IN id_actuacion_p CHAR(36)
)
BEGIN

    DELETE FROM actuaciones
    WHERE id_actuacion = id_actuacion_p;

END $$

DELIMITER ;


#Editar

DELIMITER $$

CREATE PROCEDURE sp_editar_actuacion(
    IN id_actuacion_p CHAR(36),
    IN id_expediente_p CHAR(36),
    IN id_usuario_p CHAR(36),
    IN titulo_p VARCHAR(150),
    IN descripcion_p TEXT,
    IN fecha_actuacion_p DATE,
    IN archivo_adjunto_p VARCHAR(255)
)
BEGIN

    UPDATE actuaciones
    SET
        id_expediente = id_expediente_p,
        id_usuario = id_usuario_p,
        titulo = titulo_p,
        descripcion = descripcion_p,
        fecha_actuacion = fecha_actuacion_p,
        archivo_adjunto = archivo_adjunto_p
    WHERE id_actuacion = id_actuacion_p;

END $$

DELIMITER ;


#Buscar

DELIMITER $$

CREATE PROCEDURE sp_buscar_actuacion(
    IN id_actuacion_p CHAR(36)
)
BEGIN

    SELECT
        id_actuacion,
        id_expediente,
        id_usuario,
        titulo,
        descripcion,
        fecha_actuacion,
        archivo_adjunto
    FROM actuaciones
    WHERE id_actuacion = id_actuacion_p;

END $$

DELIMITER ;
-- =====================================================================
delimiter $$
CREATE PROCEDURE sp_ver_historial_cronologico (IN id_expediente_p char(36))
        
BEGIN
			SELECT 
            e.numero_expediente,
            e.titulo AS expediente,
            e.estado,
			a.titulo AS actuaciones,
            a.descripcion,
            a.fecha_actuacion,
            u.nombre_usuario
            FROM expediente e
            
            INNER JOIN actuaciones a
				ON e.id_expediente = a.id_expediente 
                
			INNER JOIN usuario u
				ON u.id_usuario = a.id_usuario
                
			WHERE e.id_expediente = id_expediente_p
            ORDER BY a.fecha_actuacion ASC;
		
END$$
delimiter ;

delimiter $$
	create procedure sp_Login(in nombre_usuario_p varchar(50),
                              in clave_p varchar(255))
    begin
		select * from usuario
		where nombre_usuario = nombre_usuario_p 
			and clave = clave_p;
    end $$
delimiter ;


