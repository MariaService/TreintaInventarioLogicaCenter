package es.logicacenter.notificador.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.vo.InventarioResponse;
import es.logicacenter.notificador.vo.MensajeNotificadorVentaVo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelegramBotDynamicTemplateNotificacionVenta extends DefaultAbsSender {

	private static final Logger log = LoggerFactory.getLogger(TelegramBotDynamicTemplateNotificacionVenta.class);

	private final String botToken;

	public TelegramBotDynamicTemplateNotificacionVenta(String botToken) {
		super(new DefaultBotOptions());
		this.botToken = botToken;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	public void sendDynamicMessage(MensajeNotificadorVentaVo mensajeObjetc, String chatId) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		log.info(chatId);

		String tituloScursalOtra = mensajeObjetc.getCatTienda() == 1 ? "Lógica Center Morelos" : "lógica Center bravo";
		
		String mensajeCentrado = centrarTexto( mensajeObjetc.getNombreTienda() + "") + "\n\n"
				+ mensajeObjetc.getConcepto() + "\n\n"
				+ centrarTexto("Total de venta del dia: $ " + mensajeObjetc.getMontoTotalTienda()) + "\n\n"
				+ formatNewsAsMarkdown(mensajeObjetc.getListEgresos()) + "\n\n"
				+ centrarTexto("Total de Egreso del dia: $" + formatDecimal(mensajeObjetc.getSumaEgresoDia())) + "\n\n"
				+ centrarTexto("Total de Venta sucursal ("+tituloScursalOtra + " ): $" + formatDecimal(mensajeObjetc.getTotalVentaOtraTienda())) + "\n\n"
				+ centrarTexto("Total de Egreso sucursal ("+tituloScursalOtra + " ): $" + formatDecimal(mensajeObjetc.getTotalGastoOtraTienda())) + "\n\n";
				
				

//Establecer el texto del mensaje y el modo de análisis (HTML)
		message.setText(mensajeCentrado);

		message.setParseMode("HTML");

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private String formatNewsAsMarkdown(List<String> news) {
		StringBuilder formattedNews = new StringBuilder();
		if (news.isEmpty()) {
			return "No hay gasto en la lista";
		} else {

			for (String headline : news) {
				formattedNews.append(" 📉 ").append(headline).append("\n");
			}
			return formattedNews.toString();
		}
	}

	private String formatDecimal(double num) {
		DecimalFormat formato = new DecimalFormat("#,###.00");
		// Formatear el número
		return formato.format(num);
	}

	private String generarListaDinamica(List<String> egresos) {
		if (egresos.isEmpty()) {
			return "No hay egresos registrados hoy.";
		}

		StringBuilder lista = new StringBuilder();
		for (String egreso : egresos) {
			lista.append("📌").append(egreso).append("\n"); // Usar emojis y saltos de línea
		}
		return lista.toString();
	}

	private String centrarTexto(String texto) {
		int longitudMaxima = 30; // Longitud máxima estimada de una línea en Telegram
		int espacios = (longitudMaxima - texto.length()) / 2;

		// Crear una cadena con espacios a la izquierda (alternativa para Java 8)
		StringBuilder espaciosBuilder = new StringBuilder();
		for (int i = 0; i < Math.max(0, espacios); i++) {
			espaciosBuilder.append(" ");
		}

		return espaciosBuilder.toString() + texto;
	}

	// inventarioLoficaCenter
	public void enviarNotiMain(String token, String chatId, MensajeNotificadorVentaVo mensajeObjetc)
			throws IOException {

		TelegramBotDynamicTemplateNotificacionVenta bot = new TelegramBotDynamicTemplateNotificacionVenta(token);
		bot.sendDynamicMessage(mensajeObjetc, chatId);
	}

}
