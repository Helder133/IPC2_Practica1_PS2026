package org.practica1.controllers;

import org.practica1.dao.ProductoDAO;
import org.practica1.dao.SucursalProductoDAO;
import org.practica1.models.Producto;
import org.practica1.models.SucursalProducto;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SucursalProductoController implements ActionListener {

    private final int sucursalId;
    private final AdminTiendaFrame vista;
    private final SucursalProductoDAO dao;
    private final ProductoDAO productoDAO;
    public SucursalProductoController(int sucursalId, AdminTiendaFrame vista, SucursalProductoDAO dao, ProductoDAO productoDAO) {
        this.sucursalId = sucursalId;
        this.vista = vista;
        this.dao = dao;
        this.productoDAO = productoDAO;

        this.vista.getBtnAgregarProductoLocal().addActionListener(this);
        this.vista.getBtnCambiarEstadoProducto().addActionListener(this);
        this.vista.getBtnEliminarProductoLocal().addActionListener(this);

        cargarTabla();
    }

    private void cargarTabla() {
        try {
            List<SucursalProducto> list = dao.obtenerProductoSucursal(sucursalId);
            vista.actualizarTablaProductoLocal(list);
            List<Producto> list1 = productoDAO.obtenerTodo();
            vista.cargarCombosProductoLocal(list1);
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == vista.getBtnAgregarProductoLocal()) {
            guardarProductoLocal();
        } else if (actionEvent.getSource() == vista.getBtnCambiarEstadoProducto()) {
            actualizarEstadoProductoLocal();
        } else if (actionEvent.getSource() == vista.getBtnEliminarProductoLocal()) {
            eliminarProductoLocal();
        }
    }

    private void guardarProductoLocal() {
        Producto producto = vista.productoLocalSeleccionado();
        if (producto == null) {
            vista.mostrarMensaje("Debe de seleccionar un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            SucursalProducto sucursalProducto = new SucursalProducto(sucursalId, producto.getProductoID(), true);
            if (dao.existeProductoSucursal(sucursalId, producto.getProductoID())){
                vista.mostrarMensaje("Ya existe este producto en esta sucursal.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dao.insertar(sucursalProducto);
            vista.mostrarMensaje("Producto agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstadoProductoLocal() {
        int productoId = vista.getIdProductoLocalSeleccionado();
        if (productoId == 0) {
            vista.mostrarMensaje("Debe de seleccionar un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            SucursalProducto sucursalProducto = new SucursalProducto(sucursalId, productoId, false);
            dao.actualizar(sucursalProducto);
            vista.mostrarMensaje("Estado del producto actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProductoLocal() {
        int productoId = vista.getIdProductoLocalSeleccionado();
        if (productoId == 0) {
            vista.mostrarMensaje("Debe de seleccionar un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.eliminar(sucursalId, productoId);
            vista.mostrarMensaje("Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}