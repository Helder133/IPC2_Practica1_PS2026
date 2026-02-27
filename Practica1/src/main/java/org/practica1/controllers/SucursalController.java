package org.practica1.controllers;

import org.practica1.dao.SucursalDAO;
import org.practica1.models.Sucursal;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SucursalController {

    private final SuperAdminFrame vista;
    private final SucursalDAO dao;

    public SucursalController(SuperAdminFrame vista, SucursalDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.addGuardarSucursalListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSucursal();
            }
        });

        this.vista.addEliminarSucursalListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSucursal();
            }
        });

        cargarTabla();
    }

    private void guardarSucursal() {
        // 1. Recopilar datos de la vista
        String nombre = vista.getNombreSucursal();
        String contacto = vista.getContactoSucursal();
        String ubicacion = vista.getUbicacionSucursal();

        int id = vista.getIdSucursalSeleccionada();

        // 2. Validación Básica
        if (nombre.isEmpty() || ubicacion.isEmpty()) {
            vista.mostrarMensaje("El nombre y la ubicación son obligatorios.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Sucursal sucursal = new Sucursal(nombre, contacto, ubicacion);

            if (id == 0) {

                if (dao.existeUbicacion(ubicacion)) {
                    vista.mostrarMensaje("Ya existe una sucursal en esta ubicación.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dao.insertar(sucursal);
                vista.mostrarMensaje("Sucursal creada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } else {

                sucursal.setSucursal_id(id);
                dao.actualizar(sucursal);
                vista.mostrarMensaje("Sucursal actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            vista.limpiarFormularioSucursal();
            cargarTabla();

        } catch (SQLException ex) {
            vista.mostrarMensaje("Error de base de datos: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarSucursal() {
        int id = vista.getIdSucursalSeleccionada();

        if (id == 0) {
            vista.mostrarMensaje("Debe seleccionar una sucursal de la tabla para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de que desea eliminar esta sucursal? Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                dao.eliminar(id);
                vista.mostrarMensaje("Sucursal eliminada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarFormularioSucursal();
                cargarTabla();
            } catch (SQLException ex) {
                if (ex.getMessage().contains("foreign key constraint")) {
                    vista.mostrarMensaje("No se puede eliminar la sucursal porque tiene usuarios o pedidos asignados.", "Restricción de Integridad", JOptionPane.ERROR_MESSAGE);
                } else {
                    vista.mostrarMensaje("Error al eliminar: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void cargarTabla() {
        try {
            List<Sucursal> lista = dao.obtenerTodo();

            vista.actualizarTablaSucursales(lista);
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al cargar las sucursales.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
