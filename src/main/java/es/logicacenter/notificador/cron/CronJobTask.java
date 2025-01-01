package es.logicacenter.notificador.cron;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.service.PdfReader;
import es.logicacenter.notificador.service.VentaBravo;
import es.logicacenter.notificador.vo.PdfResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
// patron de sisenia singleton

// 

@Component
public class CronJobTask {
	private static final Logger log = LoggerFactory.getLogger(CronJobTask.class);

	@Value("${ruta.url}")
	private String ruta;

	@Value("${file.name}")
	private String fileName;

	@Autowired
	private PdfReader pdd;
	
	@Autowired
	private VentaBravo ventaBravo;

	@Scheduled(cron = "0 */1 * * * ?") // Formato CRON
	public void ejecutarCadaDosMinuto() throws IOException {
		log.info("Tarea ejecutada a las: " + obtenerfechaActual());
		// inicio
		CronJobTask task = new CronJobTask();
		// consumo del servico
		PdfResponse datosPedf = task.pdfResponse( obtenerTimeReporte());

		if (datosPedf != null) {
			// si hay archivo
			// descargar el reporte
			String rutaArchivo = ruta + fileName;
			task.descargaFile(rutaArchivo, datosPedf.getFilePath());
			// lectura del reporte
			pdd.pdfRead(datosPedf, rutaArchivo);
			// enviar notificacion
		} else {
			log.error("no se pudo descargar el archivo");
			// ocurrio un error
		
		}
		
		// tienda bravo
		 log.info("Se consulta tienda Bravo ...");

		 ventaBravo.bravoMain();

	}

	// consulta de servicio
	public PdfResponse pdfResponse(long fechaReporte) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		@SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n    \"storeId\": \"47741e3f-634c-5944-a651-becbfee55cf7\",\r\n    \"userId\": \"cb02287f-7b46-5497-a2f6-114c3dcd60e4\",\r\n    \"firstDate\": "+ fechaReporte+  ",\r\n    \"type\": \"day\",\r\n    \"timezone\": \"America/Mexico_City\",\r\n    \"locale\": \"es-CO\",\r\n    \"zoom\": \"25%\"\r\n}\r\n\r\n");
		Request request = new Request.Builder().url("https://api.prod.treinta.co/transaction/generate-pdf")
				.method("POST", body).addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();

		String jsonString = response.body().string();
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Convertir JSON a objeto Java
			PdfResponse response_ = objectMapper.readValue(jsonString, PdfResponse.class);

			// Imprimir algunos datos para verificar
			log.info(response_.getFilePath());

			return response_;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return null;
	}

	// paso 2 Descargar el Archivo

	public void descargaFile(String destino, String urlString) {

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
				log.info("Descarga completada: " + destino);
			} else {
				// System.out.println("Error en la descarga: " + conexion.getResponseCode());
				log.error("Error en la descarga: " + conexion.getResponseCode());
			}

			conexion.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String obtenerfechaActual() {
		// Obtener la fecha y hora actual
		LocalDateTime fechaHoraActual = LocalDateTime.now();
		// Formatear la fecha y hora
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String fechaHoraFormateada = fechaHoraActual.format(formato);
		// Imprimir la fecha y hora actual
		return fechaHoraFormateada;

	}

	public Long obtenerTimeReporte() {

		LocalDate fecha = LocalDate.now();

		// Extrae el día, mes y año
		int dia = fecha.getDayOfMonth();
		int mes = fecha.getMonthValue();
		int año = fecha.getYear();

		//
//		
//		LocalDateTime fecha_ = LocalDateTime.of(2024, 12, 04, 0, 0, 0);
		int diaReporte = dia ;
		LocalDateTime fecha_ = LocalDateTime.of(año, mes, diaReporte  , 0, 0, 0);
		// Convertir a timestamp en milisegundos (UTC)
		long timestamp = fecha_.toInstant(ZoneOffset.UTC).toEpochMilli();

		 long fechaInicial = timestamp;

	        // Diferencia en milisegundos (6 horas)
	        long diferenciaMillis = 6 * 60 * 60 * 1000; // 6 horas a milisegundos

	        // Calcular la nueva fecha
	        long fechaFinal = fechaInicial + diferenciaMillis;
		
		// Mostrar el resultado
		return fechaFinal;
	}
	
	
	
	
	

}
