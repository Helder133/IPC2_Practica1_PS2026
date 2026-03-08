package org.practica1.controllers;

import org.practica1.dao.IngredienteDAO;
import org.practica1.dao.SucursalIngredienteDAO;
import org.practica1.models.Ingrediente;
import org.practica1.models.SucursalIngrediente;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SucursalIngredienteController implements ActionListener {

    private final int sucursalId;
    private final AdminTiendaFrame vista;
    private final SucursalIngredienteDAO dao;
    private final IngredienteDAO ingredienteDAO;

    public SucursalIngredienteController(int sucursalId, AdminTiendaFrame vista, SucursalIngredienteDAO dao, IngredienteDAO ingredienteDAO) {
        this.sucursalId = sucursalId;
        this.vista = vista;
        this.dao = dao;
        this.ingredienteDAO = ingredienteDAO;

        this.vista.getBtnAgregarIngredienteLocal().addActionListener(this);
        this.vista.getBtnCambiarEstadoIngrediente().addActionListener(this);
        this.vista.getBtnEliminarIngredienteLocal().addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            List<SucursalIngrediente> list = dao.obtenerProductoSucursal(sucursalId);
            vista.actualizarTablaIngredienteLocal(list);
            List<Ingrediente> list1 = ingredienteDAO.obtenerTodo();
            vista.cargarCombosIngredientesLocales(list1);
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == vista.getBtnAgregarIngredienteLocal()) {
            guardarIngredienteLocal();
        } else if (actionEvent.getSource() == vista.getBtnCambiarEstadoIngrediente()) {
            actualizarEstadoIngredienteLocal();
        } else if (actionEvent.getSource() == vista.getBtnEliminarIngredienteLocal()) {
            eliminarIngredienteLocal();
        }
    }

    private void guardarIngredienteLocal() {
        Ingrediente ingrediente = vista.ingredienteLocalSeleccionado();
        if (ingrediente == null) {
            vista.mostrarMensaje("Debe de seleccionar un ingrediente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            SucursalIngrediente sucursalIngrediente = new SucursalIngrediente(sucursalId, ingrediente.getIngredienteId(), true);
            if (dao.existeIngredienteSucursal(sucursalId, ingrediente.getIngredienteId())) {
                vista.mostrarMensaje("Ya existe este ingrediente en esta sucursal.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dao.insertar(sucursalIngrediente);
            vista.mostrarMensaje("Ingrediente agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstadoIngredienteLocal() {
        int ingredienteId = vista.getIdIngredienteLocalSeleccionado();
        if (ingredienteId == 0) {
            vista.mostrarMensaje("Debe de seleccionar un ingrediente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            SucursalIngrediente sucursalIngrediente = new SucursalIngrediente(sucursalId, ingredienteId, false);
            dao.actualizar(sucursalIngrediente);
            vista.mostrarMensaje("Estado del ingrediente actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarIngredienteLocal() {
        int ingredienteId = vista.getIdIngredienteLocalSeleccionado();
        if (ingredienteId == 0) {
            vista.mostrarMensaje("Debe de seleccionar un ingrediente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.eliminar(sucursalId, ingredienteId);
            vista.mostrarMensaje("Ingrediente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}