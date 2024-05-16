package logica;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ExamenTipo {
    private Mat imagenRespuestasCorrectas;

    static {
        // Carga la biblioteca nativa de OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public ExamenTipo(String rutaImagen) {
        this.imagenRespuestasCorrectas = Imgcodecs.imread(rutaImagen, Imgcodecs.IMREAD_GRAYSCALE);
        if (this.imagenRespuestasCorrectas.empty()) {
            System.out.println("Error al cargar la imagen de respuestas correctas desde la ruta: " + rutaImagen);
        }
    }

    public Mat getImagenRespuestasCorrectas() {
        return imagenRespuestasCorrectas;
    }
}


