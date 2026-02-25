package org.practica1.dao;

import org.apache.commons.lang3.StringUtils;
import org.practica1.config.Conexion;
import org.practica1.models.EnumUsuario;
import org.practica1.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements CRUD<Usuario> {

    private static final String INSERTAR = "INSERT INTO (nombre, email, contrasena, rol) VALUES (?,?,?,?)";
    private static final String ACTUALIZAR_SIN_CONTRASEÑA = "UPDATE usuario SET nombre = ?, email = ?, rol = ? WHERE usuario_id = ?";
    private static final String ACTUALIZAR_CON_CONTRASEÑA = "UPDATE usuario SET nombre = ?, email = ?, contraseña = ?, rol = ? WHERE usuario_id = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM usuario WHERE usuario_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM usuario";
    private static final String ELIMINAR = "DELETE FROM usuario WHERE usuario_id = ?"; //Prueba, ya que no se va a permitir eliminar usuario, solo se desactiva la cuenta

    @Override
    public void insertar(Usuario entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setString(1, entity.getNombre());
            insert.setString(2, entity.getEmail());
            insert.setString(3, entity.getContrasena());
            insert.setString(4, entity.getRol().toString());

            insert.executeUpdate();
        }
    }

    @Override
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = extraer(resultSet);
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> obtenerTodo() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO)) {
            ResultSet resultSet = select.executeQuery();
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
        if (flag) {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CON_CONTRASEÑA)) {
                update.setString(1, entity.getNombre());
                update.setString(2, entity.getEmail());
                update.setString(3, entity.getContrasena());
                update.setString(4, entity.getRol().toString());
                update.executeUpdate();
            }
        } else {
            try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SIN_CONTRASEÑA)) {
                update.setString(1, entity.getNombre());
                update.setString(2, entity.getEmail());
                update.setString(3, entity.getRol().toString());
                update.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR)) {
            delete.setInt(1,id);
            delete.executeUpdate();
        }
    }

    private Usuario extraer(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario(
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                "",
                EnumUsuario.valueOf(resultSet.getString("rol")));
        usuario.setUsuario_id(resultSet.getInt("usuario_id"));
        usuario.setSucursal_id(resultSet.getInt("sucursal_id"));
        usuario.setEstado(resultSet.getBoolean("estado"));
        return usuario;
    }

}
