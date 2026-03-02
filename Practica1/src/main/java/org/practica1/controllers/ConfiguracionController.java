package org.practica1.controllers;

import org.practica1.dao.ConfiguracionDAO;
import org.practica1.models.Configuracion;
import org.practica1.views.SuperAdminFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class ConfiguracionController implements ActionListener {

    private final SuperAdminFrame vista;
    private final ConfiguracionDAO dao;

    public ConfiguracionController(SuperAdminFrame vista, ConfiguracionDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.getBtnGuardarConfig().addActionListener(this);

        cargarDependencia();
    }

    private void cargarDependencia() {
        try {
            Optional<Configuracion> configuracion = dao.obtenerConfiguracion();
            if (configuracion.isEmpty()) {
                this.vista.cargarConfiguracion(new Configuracion(0,0,0,0,0,0,0,0));
            } else {
                this.vista.cargarConfiguracion(configuracion.get());
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
