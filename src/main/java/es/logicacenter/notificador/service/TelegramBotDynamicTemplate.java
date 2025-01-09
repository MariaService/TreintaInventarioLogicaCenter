package es.logicacenter.notificador.service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.vo.InventarioResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelegramBotDynamicTemplate extends DefaultAbsSender {

	private static final Logger log = LoggerFactory.getLogger(TelegramBotDynamicTemplate.class);

	private final String botToken;

	
	private static final String fileNameInventario = "C:\\pdfDescarga\\inventario.pdf";

	
	public TelegramBotDynamicTemplate(String botToken) {
		super(new DefaultBotOptions());
		this.botToken = botToken;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	public void sendDynamicMessage(String chatId, List<String> news, double sumaTotal, String nameTienda) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		// message.setText("*Inventario:*\n" + formatNewsAsMarkdown(news)); // Usar
		// Markdown
		message.setText("*Inventario " + nameTienda + ":*\n" + formatNewsAsMarkdown(news) + "*Total Costo: $"
				+ formatDecimal(sumaTotal) + "*\n"); // Usar Markdown

		message.enableMarkdown(true);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
			//se envia pdf telegram
			log.warn("se envia un archivo ya que el mensaje es muy grande ");
			PdfWithTreinta pdfCreatro = new PdfWithTreinta(); 
			/*
			 * se cacha la excedpcion y se envia el pdf 
			 */
			String rutaPdfInventario =pdfCreatro.GenerarPdf(fileNameInventario, news, nameTienda);
			EnviarTelegram(rutaPdfInventario); 
			log.error(e.toString());
		}
	}

	public void sendPdf(String chatId, File pdfFile) {
		SendDocument sendDocument = new SendDocument();
		sendDocument.setChatId(chatId);
		sendDocument.setDocument(new InputFile(pdfFile));

		try {
			execute(sendDocument);
			System.out.println("PDF enviado exitosamente!");
		} catch (TelegramApiException e) {
			System.err.println("Error al enviar el PDF: " + e.getMessage());
		}
	}

	public void EnviarTelegram(String fileName) {
		// Reemplaza con el token de tu bot y el ID del chat
		String botToken = "7841587623:AAHKEjAlwqeVEtfKmct6tAvdTqW8J4AuH7M";
		String chatId = "@inventariocenter";

		
		/// crear el pdf
		
		log.info("se crfeo pdf inventario");
		// Ruta al archivo PDF
		File pdfFile = new File(fileName);

		TelegramBotDynamicTemplate botSender = new TelegramBotDynamicTemplate(botToken);
		botSender.sendPdf(chatId, pdfFile);
	}

	private String formatDecimal(double num) {
		DecimalFormat formato = new DecimalFormat("#,###.00");
		// Formatear el número
		return formato.format(num);
	}

	private String formatNewsAsMarkdown(List<String> news) {
		StringBuilder formattedNews = new StringBuilder();
		for (String headline : news) {
			formattedNews.append("📦 ").append(headline).append("\n");
		}
		return formattedNews.toString();
	}

	// inventarioLoficaCenter
	public void SigleTelegram(int parametro, String token, String chatId) throws IOException {

		// Simular datos dinámicos (pueden venir de una base de datos o una API)
		List<String> titulares = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n    \"storeId\": \"47741e3f-634c-5944-a651-becbfee55cf7\",\r\n    \"userId\": \"cb02287f-7b46-5497-a2f6-114c3dcd60e4\",\r\n    \"countryId\": 2,\r\n    \"timezone\": \"America/Bogota\",\r\n    \"locale\": \"es-CO\",\r\n    \"zoom\": 25\r\n}");
		Request request = new Request.Builder().url(
				"https://api.prod.treinta.co/product/find?storeId=47741e3f-634c-5944-a651-becbfee55cf7&limit=100000")
				.get().addHeader("X-Api-Key", "TRE1NT@WEB")
				.addHeader("X-Hash-Signature", "94230538bfea6b3dbb422444c02973e119ee82f20c30ab3673df0fa70ffe3465")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjNmZDA3MmRmYTM4MDU2NzlmMTZmZTQxNzM4YzJhM2FkM2Y5MGIyMTQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdHJlaW50YWNvIiwiYXVkIjoidHJlaW50YWNvIiwiYXV0aF90aW1lIjoxNzMzMTYxODgwLCJ1c2VyX2lkIjoicnpaR1JyUk0zc1djeEZZNEdXN3Q4YkpsSUt1MiIsInN1YiI6InJ6WkdSclJNM3NXY3hGWTRHVzd0OGJKbElLdTIiLCJpYXQiOjE3MzMxNjE4ODAsImV4cCI6MTczMzE2NTQ4MCwicGhvbmVfbnVtYmVyIjoiKzUyMjQ4MTM0OTYwMCIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsicGhvbmUiOlsiKzUyMjQ4MTM0OTYwMCJdfSwic2lnbl9pbl9wcm92aWRlciI6ImN1c3RvbSJ9fQ.LvtWFE7uQQUzcdngUy9SWcxevvMTghiQftT2KwiBG9MJ-33eOqiKdA0mXOAHX16KtyTdt80WX4MEaHax8SsixJTZlJBmjI7c6XPank8S4iLPWoFateK9ZK5Ipm1FldeuneMlbkdUlXzBgDtiWnHtJYQikwKNOaBtsC7nKSXOoSCkPNgAiupWomsSCiLsW5QQs86FY9iUwL1YckZ0ia6oL4W49DoSYEhj5Y2mgMtdLF2ZlDIejKXL6N1_xUZRt-ld4VrV3Lf7aRFkSho1no_XNh_EEEG4gHdsuQm6mPYRJniSzSA5eGeVHzKPgbq_E2ptsfKqM3wZJgdLfwQEC_XJFQ")
				.build();
		Response response = client.newCall(request).execute();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = response.body().string();

		List<InventarioResponse> productos = objectMapper.readValue(jsonString,
				new TypeReference<List<InventarioResponse>>() {
				});

		double suma = 0;
		for (InventarioResponse producto : productos) {
			if (producto.getStock() == parametro) {

				System.out.println("tienda logica center" + producto.getId());
				titulares.add(producto.getName() + " " + "Cantidad en Almacen 0 disponibles" + " " + "$"
						+ producto.getCost());
				suma += producto.getCost();

			}

		}

		TelegramBotDynamicTemplate bot = new TelegramBotDynamicTemplate(token);
		bot.sendDynamicMessage(chatId, titulares, suma, "Lógica Center Morelos");
		log.info("Se envia notificacion de inventario ...");
	}

	public void SigleTelegramBravo(int parametro, String token, String chatId) throws IOException {

		// Simular datos dinámicos (pueden venir de una base de datos o una API)
		List<String> titulares = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n    \"storeId\": \"47741e3f-634c-5944-a651-becbfee55cf7\",\r\n    \"userId\": \"cb02287f-7b46-5497-a2f6-114c3dcd60e4\",\r\n    \"countryId\": 2,\r\n    \"timezone\": \"America/Bogota\",\r\n    \"locale\": \"es-CO\",\r\n    \"zoom\": 25\r\n}");
		Request request = new Request.Builder().url(
				"https://api.prod.treinta.co/product/find?storeId=87d65767-13d2-58c7-a5d8-76c4ad6df3e0&limit=100000")
				.get().addHeader("X-Api-Key", "TRE1NT@WEB")
				.addHeader("X-Hash-Signature", "94230538bfea6b3dbb422444c02973e119ee82f20c30ab3673df0fa70ffe3465")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjNmZDA3MmRmYTM4MDU2NzlmMTZmZTQxNzM4YzJhM2FkM2Y5MGIyMTQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdHJlaW50YWNvIiwiYXVkIjoidHJlaW50YWNvIiwiYXV0aF90aW1lIjoxNzMzMTYxODgwLCJ1c2VyX2lkIjoicnpaR1JyUk0zc1djeEZZNEdXN3Q4YkpsSUt1MiIsInN1YiI6InJ6WkdSclJNM3NXY3hGWTRHVzd0OGJKbElLdTIiLCJpYXQiOjE3MzMxNjE4ODAsImV4cCI6MTczMzE2NTQ4MCwicGhvbmVfbnVtYmVyIjoiKzUyMjQ4MTM0OTYwMCIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsicGhvbmUiOlsiKzUyMjQ4MTM0OTYwMCJdfSwic2lnbl9pbl9wcm92aWRlciI6ImN1c3RvbSJ9fQ.LvtWFE7uQQUzcdngUy9SWcxevvMTghiQftT2KwiBG9MJ-33eOqiKdA0mXOAHX16KtyTdt80WX4MEaHax8SsixJTZlJBmjI7c6XPank8S4iLPWoFateK9ZK5Ipm1FldeuneMlbkdUlXzBgDtiWnHtJYQikwKNOaBtsC7nKSXOoSCkPNgAiupWomsSCiLsW5QQs86FY9iUwL1YckZ0ia6oL4W49DoSYEhj5Y2mgMtdLF2ZlDIejKXL6N1_xUZRt-ld4VrV3Lf7aRFkSho1no_XNh_EEEG4gHdsuQm6mPYRJniSzSA5eGeVHzKPgbq_E2ptsfKqM3wZJgdLfwQEC_XJFQ")
				.build();
		Response response = client.newCall(request).execute();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = response.body().string();

		List<InventarioResponse> productos = objectMapper.readValue(jsonString,
				new TypeReference<List<InventarioResponse>>() {
				});
		double suma = 0;
		for (InventarioResponse producto : productos) {
			if (producto.getStock() == 0) {
				titulares.add(producto.getName() + " $ " + producto.getCost());
				suma += producto.getCost();
			}

		}

		TelegramBotDynamicTemplate bot = new TelegramBotDynamicTemplate(token);
		bot.sendDynamicMessage(chatId, titulares, suma, "lógica Center bravo");
		log.info("Se envia notificacion de inventario ...");
	}

}
