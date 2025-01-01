package es.logicacenter.notificador.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.entity.Inventario;
import es.logicacenter.notificador.entity.Venta;
import es.logicacenter.notificador.vo.InventarioResponse;
import es.logicacenter.notificador.vo.PdfResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class PdfReader {

	private static final Logger log = LoggerFactory.getLogger(PdfReader.class);

	private final VentaService ventaService;

	@Value("${param.dia}")
	private int diaMax;

	@Value("${param.token}")
	private String token;

	@Value("${param.chatId}")
	private String chatId;

	@Value("${param.chatidInventario}")
	private String chatidInventario;

	@Autowired
	public PdfReader(VentaService ventaService) {
		this.ventaService = ventaService;
	}

	public void pdfRead(PdfResponse pdf, String filename) {
		try {
			// Ruta del archivo PDF
			File file = new File(filename);

			// Leer el PDF
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);
			document.close();

			// Procesar el texto para generar JSON
			JSONObject jsonReport = parsePDFContentToJSON(text, pdf);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONObject parsePDFContentToJSON(String content, PdfResponse pdf) throws IOException {
		// Crear un objeto JSON principal
		JSONObject reporte = new JSONObject();

		// Extraer datos principales del reporte
		reporte.put("empresa", "morelos logica center");
		reporte.put("contacto", new JSONObject().put("nombre", "carlos").put("telefono", "2296468929"));
		reporte.put("fecha_reporte", "02/12/2024");
		reporte.put("numero_transacciones", pdf.getTotalTransactions());

		// Resumen
		JSONObject resumen = new JSONObject();
		resumen.put("ingresos", 12163);
		resumen.put("abonos_ventas", 0);
		resumen.put("abonos_gastos", 0);
		resumen.put("gastos", 0);
		resumen.put("utilidad_total", 12163);
		reporte.put("resumen", resumen);

		// Ventas
		JSONArray ventas = new JSONArray();
		String[] lines = content.split("\n");
		int contador = 0;

		for (String line : lines) {
			if (useRegex(line)) {
				// imprime la leida del pdf
				// System.out.println(line);
				/// revisar si hay registro ne la base de datos
				contador++;
				ventas.put(parseVenta(line, pdf, contador));
			}
		}
		reporte.put("ventas", ventas);

		// Gastos
		reporte.put("gastos", new JSONArray());

		return reporte;
	}

	public boolean useRegex(String input) {
		// Compile regular expression
		input = input.replaceAll("\t", " "); // Reemplaza tabulaciones por espacio (si lo deseas)
		input = input.replaceAll("\r", ""); // Elimina retorno de carro (\r)
		input = input.replaceAll(" ", "");
		// final Pattern pattern = Pattern.compile("03/12/2024Venta.*",

		final Pattern pattern = Pattern.compile(cadenaBusqueda(), Pattern.CASE_INSENSITIVE);
		// Match regex against input
		final Matcher matcher = pattern.matcher(input.trim());
		// Use results...
		return matcher.matches();
	}

	// Cadena de busquewda
	public String cadenaBusqueda() {

		// Obtener la fecha y hora actual
		LocalDateTime fechaHoraActual = LocalDateTime.now();
		// Formatear la fecha y hora
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String fechaHoraFormateada = fechaHoraActual.format(formato);
		// Imprimir la fecha y hora actual

		return fechaHoraFormateada + "Venta.*";
	}

	public JSONObject parseVenta(String line, PdfResponse pdf, int consecutivo) throws IOException {
		// guardar en base de datos

		Venta ventaPersi = new Venta();

		String[] parts = line.split(" - ");
		String[] details = parts[0].split(" ", 6);
		String fecha = details[0];
		String vendedor = details[2];

		JSONObject venta = new JSONObject();
		venta.put("fecha", fecha);
		ventaPersi.setFechaVenta(fecha);

		ventaPersi.setFechaHora(fechaHoraVenta());
		venta.put("tipo", "Venta");
		ventaPersi.setTipo("venta");
		venta.put("vendedor", vendedor);
		line = line.replaceAll("\t", " "); // Reemplaza tabulaciones por espacio (si lo deseas)
		line = line.replaceAll("\r", ""); // Elimina retorno de carro (\r)
		venta.put("descripcion", line);
		ventaPersi.setDescripcion(line);
		// se envia notificacion
		// messageEnviadoNotificacion(line);
		venta.put("estado", "Pagada Efectivo");

		ventaPersi.setMonto(ExtraerMonto(ventaPersi.getDescripcion()));
		ventaPersi.setConsecutivo(consecutivo);
		ventaPersi.setFolio(generateFolio(ventaPersi, consecutivo));
		int isExiteFolio = ventaService.countVentaPorfolio(generateFolio(ventaPersi, consecutivo));

		if (isExiteFolio == 0) {
			// existe folio
			log.info(" Nuevo folio ****** " + ventaPersi.getFolio());
			ventaPersi.setIsNotificacion(1);

			log.info("Se envia notificacion a Venta");
			messageEnviadoNotificacion(ventaPersi.getDescripcion());

			persitenciaVenta(ventaPersi);
			// consultar inventario

			TelegramBotDynamicTemplate telegramBotDynamicTemplate = new TelegramBotDynamicTemplate(token);
			telegramBotDynamicTemplate.SigleTelegram(diaMax, token, chatidInventario);

		} else {
			// crearemos una venta en base de datos
			log.info("ya existe folio en la base de datos" + generateFolio(ventaPersi, consecutivo));
		}

		return venta;
	}

	private String fechaHoraVenta() {
		LocalDateTime now = LocalDateTime.now();

		// Definir el formato de la fecha y hora
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Convertir la fecha y hora a String
		String formattedDate = now.format(formatter);
		return formattedDate;
	}

	public String generateFolio(Venta venta, int consecutivo) {
		StringBuilder str = new StringBuilder();
		return str.append(venta.getFechaVenta()).append(venta.getMonto()).append(consecutivo).toString();
	}

	public Venta persitenciaVenta(Venta venta) {
		return ventaService.saveVenta(venta);
	}

	private double ExtraerMonto(String cadena) {
		Pattern patron = Pattern.compile("\\$\\d+(?:\\.\\d{1,2})?");
		Matcher matcher = patron.matcher(cadena);
		if (matcher.find()) {
			String monto = matcher.group();
			String monton_ = monto.replace("$", "");
			Double mont = Double.parseDouble(monton_);
			return mont;

		} else {
			log.warn("No se encontró un monto en la cadena.");
			return 0;
		}

	}

	/// y la buenas parcticas programador
	public void messageEnviadoNotificacion(String msj) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(
				"https://api.telegram.org/bot7407891330:AAGdJGgQAapdSAAdBd5F3Uv761-ahMgrG0I/sendMessage?chat_id=@NotificacionVenta&text="
						+ msj)
				.addHeader("Authorization", "Basic Og==").build();
		Response response = client.newCall(request).execute();
		log.info("se envia notificacion *******" + msj);
	}

}
