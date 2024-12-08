package es.logicacenter.notificador.service;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


public class PlacaDetector {

	public static void main(String[] args) {
        // Cargar la imagen
        Mat imagen = Imgcodecs.imread("imagen.jpg");

        // Inicializar el clasificador Haar para la detección de placas
        CascadeClassifier clasificador = new CascadeClassifier("haarcascade_plate.xml");

        // Convertir la imagen a escala de grises
        Mat gris = new Mat();
        Imgproc.cvtColor(imagen, gris, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(gris, gris);

        // Detectar placas en la imagen
        MatOfRect placas = new MatOfRect();
        clasificador.detectMultiScale(gris, placas, 1.1, 2, 0, new Size(30, 30), new Size());

        // Dibujar rectángulos alrededor de las placas detectadas
        for (Rect rect : placas.toArray()) {
            Imgproc.rectangle(imagen, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);
        }

        // Guardar la imagen con los rectángulos dibujados
        Imgcodecs.imwrite("placas_detectadas.jpg", imagen);
    }

}
