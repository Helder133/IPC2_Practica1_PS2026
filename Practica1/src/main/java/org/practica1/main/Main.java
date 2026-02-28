package org.practica1.main;

import org.practica1.controllers.LoginController;
import org.practica1.dao.UsuarioDAO;
import org.practica1.views.LoginFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            LoginFrame loginFrame = new LoginFrame();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            LoginController controlador = new LoginController(loginFrame, usuarioDAO);
            loginFrame.setVisible(true);

        });
    }
}