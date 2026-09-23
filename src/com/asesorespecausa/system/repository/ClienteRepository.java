 package com.asesorespecausa.system.repository;

import com.asesorespecausa.system.config.ConexionDB;
import com.asesorespecausa.system.model.Cliente;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private ConexionDB conexionDB = ConexionDB.getInstanciaConexionDB();


    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "select * from cliente order by fecha_registro desc";

        Connection conn = conexionDB.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getString("id_cliente"),
                            rs.getString("tipo_persona"),
                            rs.getString("nombre_completo"),
                            rs.getString("documento_identidad"),
                            rs.getString("nit_empresa"),
                            rs.getString("telefono_principal"),
                            rs.getString("correo_electronico"),
                            rs.getString("direccion_fisica"),
                            rs.getTimestamp("fecha_registro")
                    );
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes");
            e.printStackTrace();
        }

        return clientes;
    }

    public List<Cliente> buscarClientes(String texto) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "select * from cliente "
                + "where nombre_completo like ? "
                + "or documento_identidad like ? "
                + "or nit_empresa like ? "
                + "or telefono_principal like ? "
                + "or correo_electronico like ? "
                + "order by fecha_registro desc";

        Connection conn = conexionDB.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String patron = "%" + texto + "%";
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, patron);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                            rs.getString("id_cliente"),
                            rs.getString("tipo_persona"),
                            rs.getString("nombre_completo"),
                            rs.getString("documento_identidad"),
                            rs.getString("nit_empresa"),
                            rs.getString("telefono_principal"),
                            rs.getString("correo_electronico"),
                            rs.getString("direccion_fisica"),
                            rs.getTimestamp("fecha_registro")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar clientes");
            e.printStackTrace();
        }

        return clientes;
    }

    public boolean registrarCliente(Cliente cliente) {
        String sql = "{call sp_crear_cliente(?, ?, ?, ?, ?, ?, ?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, cliente.getTipo_persona());
            cstmt.setString(2, cliente.getNombre_completo());
            cstmt.setString(3, cliente.getDocumento_identidad());
            cstmt.setString(4, cliente.getNit_empresa());
            cstmt.setString(5, cliente.getTelefono_principal());
            cstmt.setString(6, cliente.getCorreo_electronico());
            cstmt.setString(7, cliente.getDireccion_fisica());
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente");
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "{call sp_editar_cliente(?, ?, ?, ?, ?, ?, ?, ?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, cliente.getId_cliente());
            cstmt.setString(2, cliente.getTipo_persona());
            cstmt.setString(3, cliente.getNombre_completo());
            cstmt.setString(4, cliente.getDocumento_identidad());
            cstmt.setString(5, cliente.getNit_empresa());
            cstmt.setString(6, cliente.getTelefono_principal());
            cstmt.setString(7, cliente.getCorreo_electronico());
            cstmt.setString(8, cliente.getDireccion_fisica());
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(String idCliente) {
        String sql = "{call sp_eliminar_cliente(?)}";

        Connection conn = conexionDB.getConnection();

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, idCliente);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente");
            e.printStackTrace();
            return false;
        }
    }
}

