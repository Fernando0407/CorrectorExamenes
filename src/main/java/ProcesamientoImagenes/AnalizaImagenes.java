package ProcesamientoImagenes;

import logica.ExamenTipo;
import logica.ResultadoExamen;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class AnalizaImagenes {
	
	 public static Mat marcarRespuestas(String pathToImage, ExamenTipo examenTipo, List<ResultadoExamen> resultados) {
	        Mat imagenExamen = Imgcodecs.imread(pathToImage);
	        if (imagenExamen.empty()) {
	            throw new IllegalArgumentException("No se pudo cargar la imagen del examen: " + pathToImage);
	        }

	        // Suponiendo que la lista de resultados incluye la posición de cada pregunta y si fue acertada o no
	        for (ResultadoExamen resultado : resultados) {
	            Point center = resultado.getCentro(); // Suponemos que ResultadoExamen tiene un método getCentro()
	            int radius = resultado.getRadio(); // Suponemos que ResultadoExamen tiene un método getRadio()
	            Scalar color = resultado.isCorrecta() ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255); // Verde si correcta, rojo si incorrecta
	            Imgproc.circle(imagenExamen, center, radius, color, 2); // Dibuja un círculo con un borde de grosor 2
	        }

	        return imagenExamen; // Devuelve la imagen marcada
	    }
	

    public static List<ResultadoExamen> analizarExamen(String pathToImage, ExamenTipo examenTipo) {
        Mat imagenExamen = Imgcodecs.imread(pathToImage, Imgcodecs.IMREAD_GRAYSCALE);
        Mat imagenRespuestasCorrectas = examenTipo.getImagenRespuestasCorrectas();

        // Preprocesar las imágenes
        Mat procImgExamen = new Mat();
        Mat procImgCorrectas = new Mat();
        preprocesarImagen(imagenExamen, procImgExamen);
        preprocesarImagen(imagenRespuestasCorrectas, procImgCorrectas);

        // Detectar respuestas
        return compararRespuestas(procImgExamen, procImgCorrectas);
    }

    private static void preprocesarImagen(Mat src, Mat dst) {
        Imgproc.GaussianBlur(src, dst, new Size(5, 5), 2);
        Imgproc.threshold(dst, dst, 120, 255, Imgproc.THRESH_BINARY_INV);
    }

    private static List<ResultadoExamen> compararRespuestas(Mat imgExamen, Mat imgCorrectas) {
        List<ResultadoExamen> resultados = new ArrayList<>();
        // Ajustar los parámetros según sea necesario
        double dp = 1.0; // Inversa de la resolución del acumulador
        double minDist = 30; // Distancia mínima entre los centros de los círculos detectados
        double param1 = 100; // Parámetro superior para el detector de bordes Canny
        double param2 = 30; // Umbral para la detección de centros
        int minRadius = 10; // Radio mínimo de los círculos a detectar
        int maxRadius = 20; // Radio máximo de los círculos a detectar

        Mat circles = new Mat();
        Imgproc.HoughCircles(imgExamen, circles, Imgproc.HOUGH_GRADIENT, dp, minDist, param1, param2, minRadius, maxRadius);

        for (int i = 0; i < circles.cols(); i++) {
            double[] c = circles.get(0, i);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            int radius = (int) Math.round(c[2]);

            // Verificar si el círculo está correctamente marcado comparando con la clave de respuestas
            boolean isFilledExam = checkIfFilled(imgExamen, center, radius);
            boolean isFilledCorrect = checkIfFilled(imgCorrectas, center, radius);

            String result = (isFilledExam == isFilledCorrect) ? "Correcta" : "Incorrecta";
            resultados.add(new ResultadoExamen("Pregunta en " + center.toString(), result, isFilledCorrect ? "Marcada" : "No Marcada"));
        }

        return resultados;
    }

    private static boolean checkIfFilled(Mat img, Point center, int radius) {
        double total = 0;
        double filled = 0;
        for (int y = (int)center.y - radius; y < (int)center.y + radius; y++) {
            for (int x = (int)center.x - radius; x < (int)center.x + radius; x++) {
                if (Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2) <= Math.pow(radius, 2)) {
                    total++;
                    if (img.get(y, x)[0] < 127) { // threshold for considering "filled"
                        filled++;
                    }
                }
            }
        }
        return (filled / total > 0.5); // Consider "filled" if more than 50% of the area is dark
    }
}
