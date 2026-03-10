package org.practica1.controllers;

import org.practica1.dao.UsuarioDAO;
import org.practica1.views.AdminTiendaFrame;
import org.practica1.views.JugadorFrame;
import org.practica1.views.LoginFrame;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CerrarSesionController implements ActionListener {
    JFrame jFrame;
    public CerrarSesionController(AdminTiendaFrame adminTiendaFrame) {
        this.jFrame = adminTiendaFrame;
        adminTiendaFrame.getItemCerrarSesion().addActionListener(this);
    }

    public CerrarSesionController(SuperAdminFrame superAdminFrame) {
        this.jFrame = superAdminFrame;
        superAdminFrame.getItemCerrarSesion().addActionListener(this);
    }

    public CerrarSesionController(JugadorFrame jugadorFrame) {
        this.jFrame = jugadorFrame;
        jugadorFrame.getItemCerrarSesion().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        LoginFrame loginFrame = new LoginFrame();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LoginController controlador = new LoginController(loginFrame, usuarioDAO);
        loginFrame.setVisible(true);
        jFrame.dispose();
    }
}
