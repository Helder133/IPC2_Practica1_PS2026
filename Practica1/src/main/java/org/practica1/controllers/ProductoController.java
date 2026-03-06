package org.practica1.controllers;

import org.practica1.dao.ProductoDAO;
import org.practica1.models.Producto;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ProductoController implements ActionListener {

    private AdminTiendaFrame vista;
    private ProductoDAO dao;

    public ProductoController(AdminTiendaFrame vista, ProductoDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.getBtnGuardarProducto().addActionListener(this);
        this.vista.getBtnEliminarProducto().addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            List<Producto> productos = dao.obtenerTodo();

        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.vista.getBtnGuardarProducto()) {
            guardarProducto();
        } else if (actionEvent.getSource() == this.vista.getBtnEliminarProducto()) {
            eliminarProducto();
        }
    }

    private void eliminarProducto() {
    }

    private void guardarProducto() {
    }
}
