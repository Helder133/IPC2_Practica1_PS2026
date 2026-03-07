package org.practica1.controllers;

import org.practica1.dao.IngredienteDAO;
import org.practica1.models.Ingrediente;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class IngredienteController implements ActionListener {

    private final AdminTiendaFrame vista;
    private final IngredienteDAO dao;

    public IngredienteController(AdminTiendaFrame vista, IngredienteDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.getBtnGuardarIngrediente().addActionListener(this);
        this.vista.getBtnEliminarIngrediente().addActionListener(this);

        cargarTablaIngrediente();
    }

    private void cargarTablaIngrediente() {
        try {
            List<Ingrediente> list = dao.obtenerTodo();
            vista.actualizarTablaIngrediente(list);
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.vista.getBtnGuardarIngrediente()) {
            guardarIngrediente();
        } else if (actionEvent.getSource() == this.vista.getBtnEliminarIngrediente()) {
            eliminarIngrediente();
        }
    }

    private void guardarIngrediente() {
        Ingrediente ingrediente = new Ingrediente(vista.getNombreIngrediente());

        if (!ingrediente.isValid()) {
            vista.mostrarMensaje("Nombre de un ingrediente es obligatorio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = vista.getIdIngredienteSeleccionado();

        if (id == 0) {
            try {
                if (dao.existeNombre(ingrediente.getNombre())) {
                    vista.mostrarMensaje("Este nombre ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dao.insertar(ingrediente);
                vista.mostrarMensaje("Ingrediente creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            ingrediente.setIngredienteId(id);
            try {
                if (dao.validarNombreActualizar(ingrediente.getNombre(), id)) {
                    vista.mostrarMensaje("Este nombre ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dao.actualizar(ingrediente);
                vista.mostrarMensaje("Ingrediente actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
        vista.limpiarFormIngrediente();
        cargarTablaIngrediente();
    }

    private void eliminarIngrediente() {
        int id = vista.getIdIngredienteSeleccionado();
        if (id == 0) {
            vista.mostrarMensaje("Debe de seleccionar un ingrediente, para poder ser eliminado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.eliminar(id);
            vista.mostrarMensaje("Ingrediente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarFormIngrediente();
            cargarTablaIngrediente();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}
