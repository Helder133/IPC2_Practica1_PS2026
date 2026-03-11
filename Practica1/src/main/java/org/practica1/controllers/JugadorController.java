package org.practica1.controllers;

import org.practica1.config.Conexion;
import org.practica1.dao.*;
import org.practica1.models.*;
import org.practica1.views.HistorialDialog;
import org.practica1.views.JugadorFrame;
import org.practica1.views.PanelPedidoCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class JugadorController implements ActionListener {

    private final JugadorFrame vista;
    private final Usuario jugador;

    private final PartidaDAO partidaDAO;
    private final ConfiguracionDAO configDAO;
    private final PedidoDAO pedidoDAO;
    private final DetallePedidoDAO detalleDAO;
    private final HistorialPedidoDAO historialDAO;
    private final SucursalProductoDAO menuLocalDAO;
    private final ProductoIngredienteDAO recetaDAO;
    private final SucursalIngredienteDAO inventarioLocalDAO;


    private Configuracion reglasJuego;
    private Partida partidaActual;
    private Timer timerPartidaGlobal;
    private int tiempoRestanteTurno = 150;
    private Timer timerGeneradorPedidos;
    private boolean nivel2 = false;
    private boolean nivel3 = false;
    private int pedidosEntregados = 0;
    private int pedidosCancelados = 0;
    private int pedidosNoEntregados = 0;

    public JugadorController(JugadorFrame vista, Usuario jugador, PartidaDAO partidaDAO, ConfiguracionDAO configDAO, PedidoDAO pedidoDAO, DetallePedidoDAO detalleDAO, HistorialPedidoDAO historialDAO, SucursalProductoDAO menuLocalDAO, ProductoIngredienteDAO recetaDAO, SucursalIngredienteDAO inventarioLocalDAO) {
        this.vista = vista;
        this.jugador = jugador;
        this.partidaDAO = partidaDAO;
        this.configDAO = configDAO;
        this.pedidoDAO = pedidoDAO;
        this.detalleDAO = detalleDAO;
        this.historialDAO = historialDAO;
        this.menuLocalDAO = menuLocalDAO;
        this.recetaDAO = recetaDAO;
        this.inventarioLocalDAO = inventarioLocalDAO;

        this.vista.getBtnIniciarPartida().addActionListener(this);
        this.vista.getBtnTerminarTurno().addActionListener(this);
        this.vista.getItemCerrarSesion().addActionListener(this);
        this.vista.getItemVerHistorial().addActionListener(this);

        this.vista.getLblJugadorNombre().setText("Cocinero: " + jugador.getNombre());

        cargarConfiguracionGlobal();
    }

    private void cargarConfiguracionGlobal() {
        try {
            Optional<Configuracion> optConfig = configDAO.obtenerConfiguracion();
            if (optConfig.isPresent()) {
                reglasJuego = optConfig.get();
            } else {
                reglasJuego = new Configuracion(60, 10, 600, 100, 50, 0.5, 50, 30);
                vista.mostrarMensaje("Advertencia: El sistema no tiene configuración. Se usarán reglas por defecto.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            vista.mostrarMensaje("Error al cargar reglas del juego: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == vista.getBtnIniciarPartida()) {
            iniciarJuego();
        } else if (actionEvent.getSource() == vista.getBtnTerminarTurno()) {
            terminarJuego();
        } else if (actionEvent.getSource() == vista.getItemVerHistorial()) {
            abrirHistorial();
        }
    }

    private void abrirHistorial() {
        HistorialDialog dialog = new HistorialDialog(vista);
        new HistorialController(dialog, partidaDAO, jugador.getUsuario_id());
        dialog.setVisible(true); // Se pausa aquí hasta que el jugador cierre el cuadro
    }

    private void iniciarJuego() {
        iniciarRelojGeneral();
        try {
            Partida nuevaPartida = new Partida(jugador.getUsuario_id(), jugador.getSucursal_id());

            int idGenerado = partidaDAO.iniciarPartida(nuevaPartida);
            nuevaPartida.setPartidaId(idGenerado);

            this.partidaActual = nuevaPartida;

            vista.getLblNivel().setText("Nivel: " + partidaActual.getNivel());
            vista.getLblPuntaje().setText("Puntos: " + partidaActual.getPuntaje());
            vista.getBtnIniciarPartida().setEnabled(false);
            vista.getBtnTerminarTurno().setEnabled(true);

            arrancarGeneradorDePedidos();

        } catch (SQLException ex) {
            vista.mostrarMensaje("No se pudo iniciar la partida: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void terminarJuego() {
        if (partidaActual == null) return;

        if (timerGeneradorPedidos != null) {
            timerGeneradorPedidos.stop();
            timerPartidaGlobal.stop();
        }

        try {
            partidaActual.setFechaFin(LocalDateTime.now());
            partidaDAO.actualizar(partidaActual);

            vista.getPanelContenedorPedidos().removeAll();
            vista.getPanelContenedorPedidos().repaint();

            vista.getBtnTerminarTurno().setEnabled(false);
            vista.getBtnIniciarPartida().setEnabled(true);
            String mensaje = "Turno terminado. Puntos finales: " + partidaActual.getPuntaje() + ". Nivel alcanzado: " + partidaActual.getNivel() + ". " +
                    "Pedidos completados: "+pedidosEntregados+". Pedidos cancelados: "+pedidosCancelados+". Pedidos no entregados: "+pedidosNoEntregados+".";

            vista.mostrarMensaje(mensaje, "Fin del Juego. ", JOptionPane.INFORMATION_MESSAGE);

            vista.getLblPuntaje().setText("Puntos: 0");
            vista.getLblNivel().setText("Nivel: 1");
            vista.getLblTiempoGeneral().setText("2:30");
            nivel2 = false;
            nivel3 = false;
            this.partidaActual = null;

        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al guardar el progreso: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarRelojGeneral() {
        tiempoRestanteTurno = 150;
        timerPartidaGlobal = new Timer(1000, e -> {
            tiempoRestanteTurno--;

            int minutos = tiempoRestanteTurno / 60;
            int segundos = tiempoRestanteTurno % 60;
            vista.getLblTiempoGeneral().setText(String.format("Turno: %02d:%02d", minutos, segundos));

            if (tiempoRestanteTurno <= 60) {
                vista.getLblTiempoGeneral().setForeground(java.awt.Color.RED);
            }

            if (tiempoRestanteTurno <= 0) {
                finalizarTurnoPorTiempo();
            }
        });

        timerPartidaGlobal.start();
    }

    private void finalizarTurnoPorTiempo() {
        if (timerPartidaGlobal != null) timerPartidaGlobal.stop();
        if (timerGeneradorPedidos != null) timerGeneradorPedidos.stop();

        vista.mostrarMensajeFlotante("¡TIEMPO AGOTADO! Cerrando cocina...");

        Component[] tarjetasActivas = vista.getPanelContenedorPedidos().getComponents();

        for (Component comp : tarjetasActivas) {
            if (comp instanceof PanelPedidoCard) {
                PanelPedidoCard tarjeta = (PanelPedidoCard) comp;
                Pedido pedidoPendiente = tarjeta.getPedido();

                if (pedidoPendiente.getEstado() != EnumPedido.Lista_Y_Entregado &&
                        pedidoPendiente.getEstado() != EnumPedido.Cancelado &&
                        pedidoPendiente.getEstado() != EnumPedido.No_Entregado) {

                    pedidoPendiente.setEstado(EnumPedido.No_Entregado);
                    registrarHistorialEnBD(pedidoPendiente.getPedidoId(), EnumPedido.No_Entregado);
                    pedidosNoEntregados ++;
                    finalizarPedido(tarjeta, -reglasJuego.getNo_entregado());
                }
            }
        }

        vista.mostrarMensaje(
                "¡El turno de 2.30 minutos ha finalizado!\n" +
                        "Se penalizaron todos los pedidos que quedaron en preparación.\n\n" +
                        "Puntaje Final: " + partidaActual.getPuntaje() + "\n" +
                        "Nivel Alcanzado: " + partidaActual.getNivel(),
                "Fin del Turno",
                JOptionPane.INFORMATION_MESSAGE
        );
        terminarJuego();
    }

    private void arrancarGeneradorDePedidos() {
        timerGeneradorPedidos = new Timer(10000, e -> generarNuevoPedido());
        timerGeneradorPedidos.start();
        vista.mostrarMensaje("¡La pizzería está abierta! Prepárate para los pedidos.", "Inicio", JOptionPane.INFORMATION_MESSAGE);

        generarNuevoPedido();
    }

    private void generarNuevoPedido() {
        int pedidosActivos = vista.getPanelContenedorPedidos().getComponentCount();

        //Maximo de pedido`
        if (pedidosActivos >= 6) {
            return;
        }
        try {
            List<SucursalProducto> menu = menuLocalDAO.obtenerProductoSucursal(jugador.getSucursal_id());
            List<SucursalProducto> activas = new ArrayList<>();
            for (SucursalProducto sp : menu) {
                if (sp.isEstado() && esPizzaPreparable(sp.getProductoId(), jugador.getSucursal_id())) {
                    activas.add(sp);
                }
            }

            if (activas.isEmpty()) return;

            Random rand = new Random();
            SucursalProducto pizzaElegida = activas.get(rand.nextInt(activas.size()));

            Pedido nuevoPedido = new Pedido(partidaActual.getPartidaId());
            if (nuevoPedido.getTiempoLimite() == 0) nuevoPedido.setTiempoLimite(reglasJuego.getTiempo_preparacion());

            if (nivel2) nuevoPedido.setTiempoLimite(nuevoPedido.getTiempoLimite() - reglasJuego.getDificultad_nivel());
            if (nivel3) nuevoPedido.setTiempoLimite(nuevoPedido.getTiempoLimite() - (reglasJuego.getDificultad_nivel() * 2));

            int cantidad = rand.nextInt(5) + 1;

            nuevoPedido.setTiempoLimite(nuevoPedido.getTiempoLimite() * cantidad);

            DetallePedido detalle = new DetallePedido(0, pizzaElegida.getProductoId(), cantidad);

            ProductoDAO productoDAO = new ProductoDAO();
            Optional<Producto> productoOptional = productoDAO.obtenerPorId(pizzaElegida.getProductoId());

            int idGenerado = guardarNuevoPedidoEnBD(nuevoPedido, detalle);
            nuevoPedido.setPedidoId(idGenerado);

            String nombre = cantidad + " pizzas de " + productoOptional.get().getNombre();

            PanelPedidoCard tarjeta = new PanelPedidoCard(nuevoPedido, nombre);

            configurarEventosTarjeta(tarjeta, cantidad);
            vista.agregarTarjetaPedido(tarjeta);

        } catch (SQLException ex) {
            System.err.println("Error al generar pedido automático: " + ex.getMessage());
        }
    }

    private boolean esPizzaPreparable(int productoId, int sucursalId) {
        try {
            List<ProductoIngrediente> receta = recetaDAO.obtenerTodosLosIngredientes(productoId);

            List<SucursalIngrediente> inventario = inventarioLocalDAO.obtenerProductoSucursal(sucursalId);

            for (ProductoIngrediente ingredienteRequerido : receta) {
                boolean ingredienteDisponible = false;

                for (SucursalIngrediente itemInventario : inventario) {
                    if (itemInventario.getIngredienteId() == ingredienteRequerido.getIngredienteId() && itemInventario.isEstado()) {
                        ingredienteDisponible = true;
                        break;
                    }
                }
                if (!ingredienteDisponible) {
                    return false;
                }
            }

            return true;

        } catch (SQLException e) {
            vista.mostrarMensaje("Error al validar ingredientes: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void configurarEventosTarjeta(PanelPedidoCard tarjeta, int total) {
        Pedido pedido = tarjeta.getPedido();

        final int[] tiempo = {pedido.getTiempoLimite()};
        Timer timerPedido = new Timer(1000, null);

        timerPedido.addActionListener(e -> {
            tiempo[0]--;
            tarjeta.getLblTiempoRestante().setText(tiempo[0] + " seg");

            if (tiempo[0] <= 10) {
                tarjeta.getLblTiempoRestante().setForeground(java.awt.Color.RED);
            }

            if (tiempo[0] <= 0) {
                timerPedido.stop();
                pedido.setEstado(EnumPedido.No_Entregado);
                registrarHistorialEnBD(pedido.getPedidoId(), EnumPedido.No_Entregado);
                finalizarPedido(tarjeta, -reglasJuego.getNo_entregado());
            }
        });

        timerPedido.start();

        tarjeta.getBtnCancelar().addActionListener(e -> {
            timerPedido.stop();
            pedido.setEstado(EnumPedido.Cancelado);
            registrarHistorialEnBD(pedido.getPedidoId(), EnumPedido.Cancelado);
            finalizarPedido(tarjeta, -reglasJuego.getCancelado());
            pedidosCancelados ++;
        });

        tarjeta.getBtnSiguienteEtapa().addActionListener(e -> {
            Random rand = new Random();
            int probabilidadExito = Math.max(15, 85 - (total * 15));

            int suerte = rand.nextInt(100) + 1;

            if (suerte <= probabilidadExito) {
                avanzarEtapa(tarjeta, timerPedido, tiempo, pedido.getTiempoLimite(), total); // ¡Éxito!
            } else {
                castigarBoton(tarjeta.getBtnSiguienteEtapa(), total); // ¡Se quemó, a esperar!
            }
        });
    }

    private void avanzarEtapa(PanelPedidoCard tarjeta, Timer timerPedido, int[] tiempo, int tiempoLimite, int totalProducto) {
        int tiempoRestante = tiempo[0];
        Pedido pedido = tarjeta.getPedido();
        EnumPedido actual = pedido.getEstado();

        if (actual == EnumPedido.Recibido) {
            pedido.setEstado(EnumPedido.Preparando);
            registrarHistorialEnBD(pedido.getPedidoId(), EnumPedido.Preparando);
        } else if (actual == EnumPedido.Preparando) {
            pedido.setEstado(EnumPedido.En_Horno);
            registrarHistorialEnBD(pedido.getPedidoId(), EnumPedido.En_Horno);
        } else if (actual == EnumPedido.En_Horno) {
            pedido.setEstado(EnumPedido.Lista_Y_Entregado);
            registrarHistorialEnBD(pedido.getPedidoId(), EnumPedido.Lista_Y_Entregado);
        }
        tarjeta.actualizarBotonesPorEstado();

        if (pedido.getEstado() == EnumPedido.Lista_Y_Entregado) {
            timerPedido.stop();

            int puntosGanados = reglasJuego.getCompleto();

            if (tiempoRestante >= (tiempoLimite - (tiempoLimite - (10 * totalProducto)))) {
                puntosGanados += reglasJuego.getCompleto_optimo();
                vista.mostrarMensajeFlotante("El pedido se completo en un tiempo optimo, obtuviste un bonus de: "+reglasJuego.getCompleto_optimo()+" puntos");
            }
            if (tiempoRestante >= (tiempoLimite / 2)) {
                puntosGanados += (int) (reglasJuego.getCompleto() * reglasJuego.getCompleto_eficiente());
                vista.mostrarMensajeFlotante("El pedido se completo en un tiempo eficiente, obtuviste un bonus de: "+(int) (reglasJuego.getCompleto() * reglasJuego.getCompleto_eficiente())+" puntos");
            }
            pedidosEntregados ++;
            finalizarPedido(tarjeta, puntosGanados);
        }
    }

    private void castigarBoton(JButton btn, int total) {
        Random rand = new Random();
        int castigo = rand.nextInt(4) + (2 * total);

        btn.setEnabled(false);
        btn.setBackground(java.awt.Color.GRAY);

        final int[] tiempoCastigo = {castigo};
        Timer timerCastigo = new Timer(1000, null);

        timerCastigo.addActionListener(ev -> {
            tiempoCastigo[0]--;
            btn.setText("Bloqueado (" + tiempoCastigo[0] + "s)");

            if (tiempoCastigo[0] <= 0) {
                timerCastigo.stop();
                btn.setEnabled(true);
                btn.setBackground(new java.awt.Color(52, 152, 219));
                btn.setText("Siguiente Etapa");
            }
        });
        timerCastigo.start();
    }

    private void finalizarPedido(PanelPedidoCard tarjeta, int puntosAgregados) {

        if (partidaActual == null) {
            return;
        }

        Pedido pedido = tarjeta.getPedido();
        pedido.setPuntajeObtenido(puntosAgregados);
        pedido.setFechaEntrega(LocalDateTime.now());
        int nuevoPuntaje = partidaActual.getPuntaje() + puntosAgregados;
        if (nuevoPuntaje < 0) nuevoPuntaje = 0;
        partidaActual.setPuntaje(nuevoPuntaje);

        if (partidaActual.getPuntaje() >= reglasJuego.getPunteo_minimo() * partidaActual.getNivel()) {

            if (partidaActual.getNivel() < 3) {
                partidaActual.setNivel(partidaActual.getNivel() + 1);
                vista.mostrarMensajeFlotante("¡SUBISTE AL NIVEL " + partidaActual.getNivel() + "!");
                nivel2 = partidaActual.getNivel() == 2;
                if (nivel2) vista.mostrarMensajeFlotante("Ahora los productos van a tener "+reglasJuego.getDificultad_nivel()+" segundos menos de preparación.");
                nivel3 = partidaActual.getNivel() == 3;
                if (nivel3) vista.mostrarMensajeFlotante("Ahora los productos van a tener "+reglasJuego.getDificultad_nivel()*2+" segundos menos de preparación.");
            } else if (partidaActual.getNivel() == 3) {
                vista.mostrarMensaje("¡Felicidades! Has superado el Nivel 3 y te has convertido en un Maestro Pizzero.", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                terminarJuego();
                return;
            }
        }

        vista.getLblPuntaje().setText("Puntos: " + partidaActual.getPuntaje());
        vista.getLblNivel().setText("Nivel: " + partidaActual.getNivel());

        vista.removerTarjetaPedido(tarjeta);

        try {
            Connection connection = Conexion.getInstancia().getConnection();
            pedidoDAO.actualizar(pedido, connection);

            partidaDAO.actualizar(partidaActual);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar estado final en BD: " + ex.getMessage());
        }
    }

    private int guardarNuevoPedidoEnBD(Pedido nuevoPedido, DetallePedido detalle) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try {
            connection.setAutoCommit(false);

            int pedidoId = pedidoDAO.insertar(nuevoPedido, connection);

            detalle.setPedidoId(pedidoId);
            detalleDAO.insertar(detalle, connection);

            HistorialPedido historial = new HistorialPedido(pedidoId);
            historial.setEstado(EnumPedido.Recibido);
            historialDAO.insertar(historial, connection);

            connection.commit();
            return pedidoId;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void registrarHistorialEnBD(int pedidoId, EnumPedido nuevoEstado) {
        try {
            HistorialPedido hp = new HistorialPedido(pedidoId);
            hp.setEstado(nuevoEstado);
            historialDAO.insertar(hp);
        } catch (SQLException e) {
            System.err.println("Error al guardar el historial del paso: " + e.getMessage());
        }
    }

}
