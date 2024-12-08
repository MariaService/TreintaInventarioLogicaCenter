package es.trapasoft.student.service;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeneratePdf {

	
	
	
	
	public void DescargarArchivo() {
		  String destino = "C:\\pdftest\\47741e3f-634c-5944-a651-becbfee55cf7-041224104143.pdf"; // Ruta donde se guardará el archivo

		  String urlString = "https://us-east-1-prod-treinta-assets-bucket.s3.amazonaws.com/47741e3f-634c-5944-a651-becbfee55cf7-041224104143.pdf";

		          try {
		              // Crear un objeto URL
		              URL url = new URL(urlString);
		              // Abrir la conexión
		              HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		              conexion.setRequestMethod("GET");
		              conexion.connect();

		              // Comprobar el código de respuesta
		              if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
		                  InputStream inputStream = conexion.getInputStream();
		                  FileOutputStream outputStream = new FileOutputStream(destino);

		                  byte[] buffer = new byte[4096];
		                  int bytesLeidos;

		                  // Leer el archivo y escribirlo en el destino
		                  while ((bytesLeidos = inputStream.read(buffer)) != -1) {
		                      outputStream.write(buffer, 0, bytesLeidos);
		                  }

		                  outputStream.close();
		                  inputStream.close();
		                  System.out.println("Descarga completada: " + destino);
		              } else {
		                  System.out.println("Error en la descarga: " + conexion.getResponseCode());
		              }

		              conexion.disconnect();
		          } catch (Exception e) {
		              e.printStackTrace();
		          }
		      }

	}
	
