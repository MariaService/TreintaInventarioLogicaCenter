package es.logicacenter.notificador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.vo.PdfResponse;

@SpringBootTest
class StudentApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testProgrammin() {

	}

	@Test
	void testExpresion() {
		String line = "02/12/2024 Venta LOGICA\tCENTER\tROSAS 1\tMOTOROLA\tE20 - Pagada Efectivo $550\r";
		System.out.println("Si estoy tomando cambios ");

		if (useRegex(line)) {
			System.out.println("expresion regular shida");
		} else {
			System.out.println("La línea no coincide con el patrón.");
		}
	}

	public static boolean useRegex(String input) {
		// Compile regular expression
		input = input.replaceAll("\t", " "); // Reemplaza tabulaciones por espacio (si lo deseas)
		input = input.replaceAll("\r", ""); // Elimina retorno de carro (\r)
		input = input.trim();
		String regex = "^(\\d{2}/\\d{2}/\\d{4})\\s+Venta$";
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		// Match regex against input
		final Matcher matcher = pattern.matcher(input);
		// Use results...
		return matcher.matches();
	}

	@Test
	void DescargaArchivo() {
		String destino = "C:\\pdftest\\47741e3f-634c-5944-a651-becbfee55cf7-041224104143.pdf"; // Ruta donde se guardará
																								// el archivo

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

	@Test
	void ClientePdf() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				@SuppressWarnings("deprecation")
				RequestBody body = RequestBody.create(mediaType, "{\r\n    \"storeId\": \"47741e3f-634c-5944-a651-becbfee55cf7\",\r\n    \"userId\": \"cb02287f-7b46-5497-a2f6-114c3dcd60e4\",\r\n    \"firstDate\": 1733270400000,\r\n    \"type\": \"day\",\r\n    \"timezone\": \"America/Mexico_City\",\r\n    \"locale\": \"es-CO\",\r\n    \"zoom\": \"25%\"\r\n}\r\n\r\n");
				Request request = new Request.Builder()
				  .url("https://api.prod.treinta.co/transaction/generate-pdf")
				  .method("POST", body)
				  .addHeader("Content-Type", "application/json")
				  .build();
				Response response = client.newCall(request).execute();
				
				String jsonString =response.body().string();
				 ObjectMapper objectMapper = new ObjectMapper();

			        try {
			            // Convertir JSON a objeto Java
			            PdfResponse response_ = objectMapper.readValue(jsonString, PdfResponse.class);

			            // Imprimir algunos datos para verificar
			            System.out.println("Ruta del PDF: " + response_.getFilePath());
			           
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				
	}
	
	@Test
	void MessageEnviado() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("https://api.telegram.org/bot7407891330:AAGdJGgQAapdSAAdBd5F3Uv761-ahMgrG0I/sendMessage?chat_id=@NotificacionVenta&text=03/12/2024 Venta carlos 2130 - Pagada Efectivo $1")
				  .get()
				  .build();
				Response response = client.newCall(request).execute();
	}

}
