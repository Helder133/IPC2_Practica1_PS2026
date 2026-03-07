package org.practica1.controllers;

import org.practica1.dao.IngredienteDAO;
import org.practica1.dao.ProductoDAO;
import org.practica1.dao.ProductoIngredienteDAO;
import org.practica1.models.Ingrediente;
import org.practica1.models.Producto;
import org.practica1.models.ProductoIngrediente;
import org.practica1.views.AdminTiendaFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoIngredienteController implements ActionListener {

    private final AdminTiendaFrame vista;
    private final ProductoIngredienteDAO dao;
    private final ProductoDAO productoDAO;
    private final IngredienteDAO ingredienteDAO;

    private boolean cargandoCombos = true;

    public ProductoIngredienteController(AdminTiendaFrame vista, ProductoIngredienteDAO dao, ProductoDAO productoDAO, IngredienteDAO ingredienteDAO) {
        this.vista = vista;
        this.dao = dao;
        this.productoDAO = productoDAO;
        this.ingredienteDAO = ingredienteDAO;

        this.vista.getBtnAgregarIngredienteReceta().addActionListener(this);
        this.vista.getBtnQuitarIngredienteReceta().addActionListener(this);

        this.vista.getCbxProductoReceta().addActionListener(e -> {
            if (!cargandoCombos) {
                actualizarTablaReceta();
            }
        });

        cargarCombos();
    }

    private void actualizarTablaReceta() {
        Producto producto = vista.productoSeleccionado();
        if (producto == null) {
            return;
        }
        try {
            List<ProductoIngrediente> relacion = dao.obtenerTodosLosIngredientes(producto.getProductoID());
            List<Ingrediente> list = new ArrayList<>();
            for (ProductoIngrediente p : relacion){
                Optional<Ingrediente> ingOpt = ingredienteDAO.obtenerPorId(p.getIngredienteId());
                ingOpt.ifPresent(list::add);
            }
            vista.actualizarTablaReceta(list);
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al actualizar tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCombos() {
        cargandoCombos = true;
        try {
            vista.cargarCombosProducto(productoDAO.obtenerTodo());
            vista.cargarCombosIngrediente(ingredienteDAO.obtenerTodo());
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al cargar listas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            cargandoCombos = false;
            actualizarTablaReceta();
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == vista.getBtnAgregarIngredienteReceta()) {
            agregarIngrediente();
        } else if (actionEvent.getSource() == vista.getBtnQuitarIngredienteReceta()) {
            quitarIngrediente();
        }
    }

    public void agregarIngrediente() {
        Producto producto = vista.productoSeleccionado();
        Ingrediente ingrediente = vista.ingredienteSeleccionado();

        if (producto == null || ingrediente == null) {
            vista.mostrarMensaje("Debe seleccionar una pizza y un ingrediente.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.existeIngredienteEnProducto(producto.getProductoID(), ingrediente.getIngredienteId())) {
                vista.mostrarMensaje("Esta pizza ya tiene ese ingrediente.", "Receta Duplicada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ProductoIngrediente productoIngrediente = new ProductoIngrediente(producto.getProductoID(), ingrediente.getIngredienteId());
            dao.insertar(productoIngrediente);
            actualizarTablaReceta();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al agregar a la receta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void quitarIngrediente() {
        int productoId = vista.productoSeleccionado().getProductoID();
        int ingredienteId = vista.getIdProductoIngredienteSeleccionado();
        if (ingredienteId == 0) {
            vista.mostrarMensaje("Debe seleccionar un ingrediente DE LA TABLA para quitarlo.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.eliminar(productoId, ingredienteId);
            actualizarTablaReceta();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al quitar el ingrediente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
