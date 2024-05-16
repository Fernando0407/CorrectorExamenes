package InterfazUsuario;



// CLASE PRINCIPAL DE LA INTERFAZ, MANEJA LA VENTANA PRINCIPAL DE LA APLICACIÓN. Iniciar la aplicación, mostrar menús, y manejar eventos de usuario.
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.JFrame;
import ProcesamientoImagenes.AnalizaImagenes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;  // Asegúrate de que esta importación es correcta
import java.util.ArrayList;
import javax.swing.JOptionPane;

import logica.ExamenTipo;
import logica.ResultadoExamen;
import ProcesamientoImagenes.AnalizaImagenes;


public class Principal extends JFrame {
	private static final long serialVersionUID = 1L;
    private ImagenesExamenes panelImagenes;

    public Principal() {
        initUI();
    }

    private void initUI() {
        setTitle("Corrector de Exámenes");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new BackgroundPanel());  // Añade solo el fondo inicialmente

        setupMenuBar();
        setupZoomSlider();
    }


    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 123, 255));  // Azul brillante
        menuBar.setForeground(Color.WHITE);

        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 16));

        JMenuItem openItem = new JMenuItem("Abrir");
        styleMenuItem(openItem);
        openItem.addActionListener(this::abrirImagen);
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Salir");
        styleMenuItem(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private void styleMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(new Color(0, 123, 255));  // Azul brillante
        menuItem.setForeground(Color.WHITE);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItem.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupZoomSlider() {
        JSlider zoomSlider = new JSlider(1, 5, 1);
        zoomSlider.addChangeListener(e -> {
            if (!zoomSlider.getValueIsAdjusting()) {
                double zoomLevel = zoomSlider.getValue();
                panelImagenes.setZoomFactor(zoomLevel);
            }
        });
        add(zoomSlider, BorderLayout.SOUTH);
    }
    
    class BackgroundPanel extends JPanel {
    	private static final long serialVersionUID = 1L;
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon(getClass().getResource("/imagenes/fondo_examen.jpg")).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.BLACK);
            g.drawString("Corrector de Examen tipo test por Imagen", 60, 60);
        }
    }
    
    

    private void abrirImagen(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "bmp"));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ExamenTipo examenTipo = new ExamenTipo("src/main/java/imagenes/ExamenTipo.png");
            List<ResultadoExamen> resultados = AnalizaImagenes.analizarExamen(selectedFile.getAbsolutePath(), examenTipo);
            mostrarResultados(resultados);
        }
    }

    /*
    private void procesarExamen(File examFile) {
        try {
            String rutaRespuestasCorrectas = "/imagenes/ExamenTipo.png";  // Asegura que esta ruta es accesible y correcta
            ExamenTipo examenTipo = new ExamenTipo(rutaRespuestasCorrectas);
            if (examenTipo.getImagenRespuestasCorrectas() == null) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de respuestas correctas.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<ResultadoExamen> resultados = AnalizaImagenes.analizarExamen(examFile.getAbsolutePath(), examenTipo);
            mostrarResultados(resultados);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar el examen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     */

    
    private void mostrarResultados(List<ResultadoExamen> resultados) {

        Resultados dialog = new Resultados(this, true, resultados);
        dialog.setVisible(true);
    }
    
    private void abrirImagenYProcesar(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "bmp"));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String pathToStudentImage = selectedFile.getAbsolutePath();
            ExamenTipo examenTipo = new ExamenTipo("ruta/a/ExamenTipo.png"); // Asegúrate de que esta ruta es correcta

            List<ResultadoExamen> resultados = AnalizaImagenes.analizarExamen(pathToStudentImage, examenTipo);
            Mat imagenMarcada = AnalizaImagenes.marcarRespuestas(pathToStudentImage, examenTipo, resultados);
            String outputPath = "ruta/a/imagen/resultante.png";
            Imgcodecs.imwrite(outputPath, imagenMarcada);

            // Opcional: Mostrar la imagen marcada en una nueva ventana o panel
            mostrarImagenMarcada(outputPath);
        }
    }
    
    private void mostrarImagenMarcada(String imagePath) {
        // Crear un ImageIcon desde la ruta de la imagen
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // Crear un JLabel y establecer el icono de la imagen
        JLabel imageLabel = new JLabel(imageIcon);

      

        // Opción 2: Mostrar en un panel dentro de la ventana principal
        // Configura el panel si es necesario (este código asume que tienes un panel designado para mostrar la imagen)
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Agrega el panel a la ventana principal o a un marco existente
        this.getContentPane().add(imagePanel, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }

    // Otros métodos necesarios para la clase

    
     
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
        	new Principal().setVisible(true);
        });
    }
}
