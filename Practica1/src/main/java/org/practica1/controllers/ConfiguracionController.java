package org.practica1.controllers;

import org.practica1.dao.ConfiguracionDAO;
import org.practica1.models.Configuracion;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
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
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        guardarConfiguracion();
    }

    private void guardarConfiguracion() {
        Number valor = (Number) vista.getJsCompletoEficiente();
        double eficiente = valor.doubleValue();
        Configuracion configuracion = new Configuracion(vista.getJsTiempoPreparacion(),
                vista.getJsDificultadNivel(),
                vista.getJsPunteoMinimo(),
                vista.getJsCompleto(),
                vista.getJsCompletoOptimo(),
                eficiente,
                vista.getJsNoEntregado(),
                vista.getJsCancelado());
        if (vista.getLblId() == 0) {
            try {
                dao.insertar(configuracion);
                vista.mostrarMensaje("Configuracion creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            configuracion.setConfiguracion_id(vista.getLblId());
            try {
                dao.actualizar(configuracion);
                vista.mostrarMensaje("Configuracion actualizada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
        refrescarDatos();;
    }

    private void refrescarDatos() {
        try {
            Optional<Configuracion> configuracion = dao.obtenerConfiguracion();
            this.vista.cargarConfiguracion(configuracion.get());
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}
