package org.practica1.controllers;

import org.practica1.dao.ConfiguracionDAO;
import org.practica1.dao.ProductoDAO;
import org.practica1.models.Configuracion;
import org.practica1.models.Producto;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoController implements ActionListener {

    private final AdminTiendaFrame vista;
    private final ProductoDAO dao;
    private final ConfiguracionDAO configuracionDAO;

    public ProductoController(AdminTiendaFrame vista, ProductoDAO dao, ConfiguracionDAO configuracionDAO) {
        this.vista = vista;
        this.dao = dao;
        this.configuracionDAO = configuracionDAO;

        this.vista.getBtnGuardarProducto().addActionListener(this);
        this.vista.getBtnEliminarProducto().addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            List<Producto> productos = dao.obtenerTodo();
            Optional<Configuracion> optionalConfiguracion = configuracionDAO.obtenerConfiguracion();
            optionalConfiguracion.ifPresent(configuracion -> vista.setJsTiempoPreparacion(configuracion.getTiempo_preparacion()));
            vista.actualizarTablaProducto(productos);
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

    private void guardarProducto() {
        int id = vista.getIdProductoSeleccionado();
        Producto producto = new Producto(vista.getTiempoPreparacionProducto(), vista.getNombreProducto());
        if (!producto.isValid()) {
            vista.mostrarMensaje("Por favor, llene todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (id == 0) {
                if (dao.existeNombre(producto.getNombre())) {
                    vista.mostrarMensaje("Ya existe un producto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dao.insertar(producto);
                vista.mostrarMensaje("Producto creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                producto.setProductoID(id);
                if (dao.validarNombreActualizar(producto.getNombre(), id)) {
                    vista.mostrarMensaje("Ya existe un producto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dao.actualizar(producto);
                vista.mostrarMensaje("Producto actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
    }

    private void eliminarProducto() {
        int id = vista.getIdProductoSeleccionado();
        try {
            if (id == 0) {
                vista.mostrarMensaje("No hay producto seleccionado para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.eliminar(id);
            vista.mostrarMensaje("Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

}
