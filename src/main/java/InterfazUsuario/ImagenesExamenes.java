package InterfazUsuario;

import javax.imageio.ImageIO;

// Componente personalizado para mostrar imágenes de los exámenes. Renderizar imágenes, marcar áreas de interés (como respuestas seleccionadas).

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagenesExamenes extends JPanel {
	private static final long serialVersionUID = 1L;
    private BufferedImage image;
    private double zoomFactor = 1.0;

    public ImagenesExamenes() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE); // Fondo blanco para mejor contraste
    }
    
    public void cargarImagen(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            setImage(image);  // Asumiendo que hay un método setImage para establecer la imagen en el panel
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint(); // Redibuja el panel para mostrar la nueva imagen
        
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;

            // Obtiene el tamaño del panel
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Mantiene la proporción de la imagen
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            double scale = Math.min(panelWidth / imageWidth, panelHeight / imageHeight);

            // Calcula las nuevas dimensiones manteniendo la proporción
            int newImageWidth = (int) (imageWidth * scale);
            int newImageHeight = (int) (imageHeight * scale);

            // Calcula la posición x e y para centrar la imagen en el panel
            int x = (panelWidth - newImageWidth) / 2;
            int y = (panelHeight - newImageHeight) / 2;

            // Dibuja la imagen ajustada al tamaño del panel
            g2d.drawImage(image, x, y, newImageWidth, newImageHeight, this);
        }
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
        repaint(); // Redibuja el panel para aplicar el zoom
    }
    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            int x = (getWidth() - (int)(image.getWidth() * zoomFactor)) / 2;
            int y = (getHeight() - (int)(image.getHeight() * zoomFactor)) / 2;
            g2.drawImage(image, x, y, (int)(image.getWidth() * zoomFactor), (int)(image.getHeight() * zoomFactor), this);
        }
    }
    */
}

