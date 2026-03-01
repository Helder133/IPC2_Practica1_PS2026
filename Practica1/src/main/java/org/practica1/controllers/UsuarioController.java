package org.practica1.controllers;

import org.apache.commons.lang3.StringUtils;
import org.practica1.dao.SucursalDAO;
import org.practica1.dao.UsuarioDAO;
import org.practica1.models.EnumUsuario;
import org.practica1.models.Sucursal;
import org.practica1.models.Usuario;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController implements ActionListener {

    private final SuperAdminFrame vista;
    private final UsuarioDAO usuarioDAO;
    private final SucursalDAO sucursalDAO;

    public UsuarioController(SuperAdminFrame vista, UsuarioDAO usuarioDAO, SucursalDAO sucursalDAO) {
        this.vista = vista;
        this.usuarioDAO = usuarioDAO;
        this.sucursalDAO = sucursalDAO;

        this.vista.getBtnGuardarUsuario().addActionListener(this);
        this.vista.getBtnEliminarUsuario().addActionListener(this);

        cargarDependencia();
    }

    private void cargarDependencia() {
        try {
            List<Sucursal> sucursals = sucursalDAO.obtenerTodo();
            vista.cargarComboSucursales(sucursals);

            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == vista.getBtnGuardarUsuario()) {
            guardarUsuario();
        } else if (actionEvent.getSource() == vista.getBtnEliminarUsuario()) {
            eliminarUsuario();
        }
    }

    private void guardarUsuario() {
        String nombre = vista.getNombreUsuario();
        String email = vista.getEmailUsuario();
        String contrasena = vista.getContrasena();
        String rol = vista.getRolUsuario();
        Sucursal sucursalSeleccionando = vista.getSucursalUsuarioSeleccionada();
        int id = vista.getIdUsuarioTablaSeleccionado();

        boolean flag = StringUtils.isBlank(contrasena);
        boolean flag2 = false;

        if (flag) {
            contrasena = "123";
            flag2 = true;
        }

        Usuario usuario = new Usuario(sucursalSeleccionando.getSucursal_id(), nombre, email, contrasena, EnumUsuario.valueOf(rol));

        if (!usuario.isValid()) {
            vista.mostrarMensaje("Nombre, Email y Rol son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (flag2) {
            contrasena = "";
        }

        if (usuario.getRol().equals(EnumUsuario.ADMIN_TIENDA) && usuario.getSucursal_id() == 0) {
            vista.mostrarMensaje("Un Administrador de Tienda debe estar asignado a una sucursal.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (id == 0) {
                if (flag) {
                    vista.mostrarMensaje("La contraseña es obligatoria para nuevos usuarios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }else if (usuarioDAO.existeEmail(usuario.getEmail())) {
                    vista.mostrarMensaje("Este correo ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.insertar(usuario);
                vista.mostrarMensaje("Usuario creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                usuario.setUsuario_id(id);
                usuarioDAO.actualizar(usuario);
                vista.mostrarMensaje("Usuario actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            vista.limpiarFormularioUsuario();
            cargarTabla();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error de BD: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        int id = vista.getIdUsuarioTablaSeleccionado();
        if (id == 0) {
            vista.mostrarMensaje("Seleccione un usuario para desactivar o activar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            usuarioDAO.eliminar(id); // Tu DAO ya hace un UPDATE estado = 0 (Soft Delete)
            vista.mostrarMensaje("Usuario desactivado/activado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarFormularioUsuario();
            cargarTabla();
        } catch (SQLException e) {

        }
    }

    private void cargarTabla() throws SQLException {
        List<Usuario> usuarios = usuarioDAO.obtenerTodo();
        vista.actualizarTablaUsuarios(usuarios);
    }
}
