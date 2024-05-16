package InterfazUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import logica.ResultadoExamen;  // Importación correcta de la clase ResultadoExamen del paquete logica

public class Resultados extends JDialog {
    private static final long serialVersionUID = 1L;

    public Resultados(JFrame owner, boolean modal, List<ResultadoExamen> resultados) {
        super(owner, "Resultados del Examen", modal);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setupUI(resultados);
    }

    private void setupUI(List<ResultadoExamen> resultados) {
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (ResultadoExamen resultado : resultados) {
            contentPanel.add(new JLabel("Pregunta: " + resultado.getPregunta()));
            contentPanel.add(new JLabel("Respuesta seleccionada: " + resultado.getRespuestaSeleccionada()));
            contentPanel.add(new JLabel("Respuesta correcta: " + resultado.getRespuestaCorrecta()));
            contentPanel.add(new JSeparator());
        }
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    
   

    
    
}
