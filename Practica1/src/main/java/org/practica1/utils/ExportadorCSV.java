package org.practica1.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExportadorCSV {

    public static void exportarTabla(JTable tabla, Component ventanaPadre, String nombreSugerido) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Reporte a CSV");
        fileChooser.setSelectedFile(new File(nombreSugerido + ".csv"));

        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv"));

        int seleccion = fileChooser.showSaveDialog(ventanaPadre);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoDestino = fileChooser.getSelectedFile();

            if (!archivoDestino.getName().toLowerCase().endsWith(".csv")) {
                archivoDestino = new File(archivoDestino.getParentFile(), archivoDestino.getName() + ".csv");
            }

            try (FileWriter write = new FileWriter(archivoDestino, StandardCharsets.UTF_8)) {
                write.write('\ufeff');
                TableModel modelo = tabla.getModel();
                int columnas = modelo.getColumnCount();
                int filas = modelo.getRowCount();

                // 1. Escribir las cabeceras (Nombres de las columnas)
                for (int i = 0; i < columnas; i++) {
                    write.write(escaparDatoCSV(modelo.getColumnName(i)));
                    if (i < columnas - 1) write.write(",");
                }
                write.write("\n");

                // 2. Escribir los datos fila por fila
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        Object valor = modelo.getValueAt(i, j);
                        String texto = (valor == null) ? "" : valor.toString();
                        write.write(escaparDatoCSV(texto));

                        if (j < columnas - 1) write.write(",");
                    }
                    write.write("\n");
                }

                JOptionPane.showMessageDialog(ventanaPadre, "Reporte exportado exitosamente a:\n" + archivoDestino.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(ventanaPadre, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String escaparDatoCSV(String dato) {
        if (dato.contains(",") || dato.contains("\"") || dato.contains("\n")) {
            dato = dato.replace("\"", "\"\"");
            return "\"" + dato + "\"";
        }
        return dato;
    }

}
