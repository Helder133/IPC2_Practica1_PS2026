package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.Configuracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ConfiguracionDAO implements CRUD<Configuracion> {

    private static final String INSERTAR = "INSERT INTO configuracion_sistema ( tiempo_preparacion, dificultad_nivel, punteo_minimo, completo, completo_optimo, completo_eficiente, cancelado, no_entregado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ACTUALIZAR = "UPDATE configuracion_sistema SET tiempo_preparacion = ?, dificultad_nivel = ?, punteo_minimo = ?, completo = ?, completo_optimo = ?, completo_eficiente = ?, cancelado = ?, no_entregado = ? WHERE configuracion_id = 1";
    private static final String OBTENER = "SELECT * FROM configuracion_sistema LIMIT 1";

    @Override
    public void insertar(Configuracion entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insertar = connection.prepareStatement(INSERTAR)) {
            insertar.setInt(1, entity.getTiempo_preparacion());
            insertar.setInt(2, entity.getDificultad_nivel());
            insertar.setInt(3, entity.getPunteo_minimo());
            insertar.setInt(4, entity.getCompleto());
            insertar.setInt(5, entity.getCompleto_optimo());
            insertar.setDouble(6, entity.getCompleto_eficiente());
            insertar.setInt(7, entity.getCancelado());
            insertar.setInt(8, entity.getNo_entregado());

            insertar.executeUpdate();
        }
    }

    @Override
    public Optional<Configuracion> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    public Optional<Configuracion> obtenerConfiguracion() throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER);
             ResultSet resultSet = select.executeQuery()) {
            if (resultSet.next()) {
                Configuracion c = new Configuracion(resultSet.getInt("tiempo_preparacion"),
                        resultSet.getInt("dificultad_nivel"),
                        resultSet.getInt("punteo_minimo"),
                        resultSet.getInt("completo"),
                        resultSet.getInt("completo_optimo"),
                        resultSet.getDouble("completo_eficiente"),
                        resultSet.getInt("no_entregado"),
                        resultSet.getInt("cancelado"));
                c.setConfiguracion_id(resultSet.getInt("configuracion_id"));
                return Optional.of(c);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Configuracion> obtenerTodo() throws SQLException {
        return List.of();
    }

    @Override
    public void actualizar(Configuracion entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR)) {
            update.setInt(1, entity.getTiempo_preparacion());
            update.setInt(2, entity.getDificultad_nivel());
            update.setInt(3, entity.getPunteo_minimo());
            update.setInt(4, entity.getCompleto());
            update.setInt(5, entity.getCompleto_optimo());
            update.setDouble(6, entity.getCompleto_eficiente());
            update.setInt(7, entity.getCancelado());
            update.setInt(8, entity.getNo_entregado());

            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
