package org.practica1.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public LoginFrame() {
        setTitle("Pizza Express Tycoon - Autenticación");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

        Font titulo = new Font("Arial", Font.BOLD, 22);
        Font principal = new Font("Helvetica", Font.BOLD, 14);
        Font secundario = new Font("Helvetica", Font.PLAIN, 13);

        JLabel lblTitulo = new JLabel("PIZZA EXPRESS TYCOON", SwingConstants.CENTER);
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(0, 25, 500, 30);
        panel.add(lblTitulo);

        JLabel lblEmail = new JLabel("Correo Electrónico:");
        lblEmail.setFont(principal);
        lblEmail.setForeground(Color.LIGHT_GRAY);
        lblEmail.setBounds(100, 80, 300, 20);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(secundario);
        txtEmail.setBackground(new Color(50, 50, 50));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setCaretColor(Color.WHITE);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        txtEmail.setBounds(100, 100, 300, 35);
        panel.add(txtEmail);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(principal);
        lblPassword.setForeground(Color.LIGHT_GRAY);
        lblPassword.setBounds(100, 150, 300, 20);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(secundario);
        txtPassword.setBackground(new Color(50, 50, 50));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        txtPassword.setBounds(100, 170, 300, 35);
        panel.add(txtPassword);

        btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setBounds(150, 240, 200, 40);
        btnIngresar.setFont(principal);
        btnIngresar.setBackground(new Color(231, 76, 60));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder());
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnIngresar);

        this.add(panel);
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnIngresar.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }
}