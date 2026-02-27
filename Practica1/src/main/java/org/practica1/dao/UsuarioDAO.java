package org.practica1.dao;

import org.apache.commons.lang3.StringUtils;
import org.practica1.config.Conexion;
import org.practica1.models.EnumUsuario;
import org.practica1.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements CRUD<Usuario> {

    private static final String INSERTAR = "INSERT INTO usuario (sucursal_id, nombre, email, contrasena, rol) VALUES (?,?,?,?,?)";
    private static final String ACTUALIZAR_SIN_CONTRASENA = "UPDATE usuario SET sucursal_id = ?, nombre = ?, email = ?, rol = ? WHERE usuario_id = ?";
    private static final String ACTUALIZAR_CON_CONTRASENA = "UPDATE usuario SET sucursal_id = ?, nombre = ?, email = ?, contrasena = ?, rol = ? WHERE usuario_id = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM usuario";
    private static final String DESACTIVAR_USUARIO = "UPDATE usuario SET estado = 0 WHERE usuario_id = ?";
    private static final String ACTIVAR_USUARIO = "UPDATE usuario SET estado = 1 WHERE usuario_id = ?";
    private static final String LOGIN = "SELECT * FROM usuario WHERE email = ? AND contrasena = ? AND estado = 1";
    private static final String VALIDAR_EMAIL = "SELECT usuario_id FROM usuario WHERE email = ?";

    public Optional<Usuario> login(Usuario usuario) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement login = connection.prepareStatement(LOGIN)) {
            login.setString(1,usuario.getEmail());
            login.setString(2,usuario.getContrasena());
            try (ResultSet resultSet = login.executeQuery();) {
                if (resultSet.next()) {
                    return Optional.of(extraer(resultSet));
                }
            }
            return Optional.empty();
        }
    }

    public boolean existeEmail(String email) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement unique = connection.prepareStatement(VALIDAR_EMAIL)) {
            unique.setString(1,email);
            try (ResultSet resultSet = unique.executeQuery();) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(Usuario entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            if (entity.getSucursal_id() > 0) {
                insert.setInt(1, entity.getSucursal_id());
            } else {
                insert.setNull(1, Types.INTEGER);
            }
            insert.setString(2, entity.getNombre());
            insert.setString(3, entity.getEmail());
            insert.setString(4, entity.getContrasena());
            insert.setString(5, entity.getRol().toString());

            insert.executeUpdate();
        }
    }

    @Override
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = extraer(resultSet);
                    return Optional.of(usuario);
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> obtenerTodo() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO);
             ResultSet resultSet = select.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuario = extraer(resultSet);
                usuarios.add(usuario);
            }
            return usuarios;
        }
    }

    @Override
    public void actualizar(Usuario entity) throws SQLException {
        boolean flag = StringUtils.isBlank(entity.getContrasena());
        Connection connection = Conexion.getInstancia().getConnection();
        if (!flag) {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CON_CONTRASENA)) {
                if (entity.getSucursal_id() > 0) {
                    update.setInt(1, entity.getSucursal_id());
                } else {
                    update.setNull(1, Types.INTEGER);
                }
                update.setString(2, entity.getNombre());
                update.setString(3, entity.getEmail());
                update.setString(4, entity.getContrasena());
                update.setString(5, entity.getRol().toString());
                update.setInt(6, entity.getUsuario_id());
                update.executeUpdate();
            }
        } else {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SIN_CONTRASENA)) {
                if (entity.getSucursal_id() > 0) {
                    update.setInt(1, entity.getSucursal_id());
                } else {
                    update.setNull(1, Types.INTEGER);
                }
                update.setString(2, entity.getNombre());
                update.setString(3, entity.getEmail());
                update.setString(4, entity.getRol().toString());
                update.setInt(5, entity.getUsuario_id());
                update.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(DESACTIVAR_USUARIO)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    public void activar(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ACTIVAR_USUARIO)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    private Usuario     extraer(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario(
                resultSet.getInt("sucursal_id"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                "",
                EnumUsuario.valueOf(resultSet.getString("rol")));
        usuario.setUsuario_id(resultSet.getInt("usuario_id"));
        usuario.setEstado(resultSet.getBoolean("estado"));
        return usuario;
    }

}
