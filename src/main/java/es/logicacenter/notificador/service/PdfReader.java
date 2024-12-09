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

	private final VentaService ventaServ;
	
	
	@Value("${param.max.dia}")
	private int diaMax;
	

	@Autowired
	public PdfReader(VentaService ventaServ) {
		this.ventaServ = ventaServ;
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
		int isExiteFolio = ventaServ.countVentaPorfolio(generateFolio(ventaPersi, consecutivo));

		if (isExiteFolio == 0) {
			// existe folio
			log.info(" Nuevo folio ****** " + ventaPersi.getFolio());
			ventaPersi.setIsNotificacion(1);
			messageEnviadoNotificacion(ventaPersi.getDescripcion());
			persitenciaVenta(ventaPersi);
			// consultar inventario
			consultaServicioInventario();
			
		} else {
			// crearemos una venta en base de datos
			log.info("ya existe folio en la base de datos" + generateFolio(ventaPersi, consecutivo));
		}

		return venta;
	}

	public String generateFolio(Venta venta, int consecutivo) {
		StringBuilder str = new StringBuilder();
		return str.append(venta.getFechaVenta()).append(venta.getMonto()).append(consecutivo).toString();
	}

	public Venta persitenciaVenta(Venta venta) {
		return ventaServ.saveVenta(venta);
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
				"https://api.telegram.org/bot7634748283:AAGNZxOhT_hSesXwpCX6awSuOsTB-_dARQ0/sendMessage?chat_id=@grupoventaTest&text="
						+ msj)
				.addHeader("Authorization", "Basic Og==").build();
		Response response = client.newCall(request).execute();
		log.info("se envia notificacion *******" + msj);
	}

	private void consultaServicioInventario() throws IOException {
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

		log.info("Consultado Inevatrio. ");
		List<InventarioResponse> productos = objectMapper.readValue(jsonString,
				new TypeReference<List<InventarioResponse>>() {
				});
		for (InventarioResponse producto : productos) {
			if (producto.getStock() == diaMax) {
				mesajeNotificacionInventario(producto.getName(), producto.getStock());
				log.info(" ***********" + producto.getName() + producto.getStock());
				
			}

		}
	}

	private void mesajeNotificacionInventario(String msj, int stock) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(
				"https://api.telegram.org/bot7634748283:AAGNZxOhT_hSesXwpCX6awSuOsTB-_dARQ0/sendMessage?chat_id=@grupoventaTest&text="
						+ msj + " " + "Cantidad en Almacen " + stock + " " + "disponibles")

				.addHeader("Authorization", "Basic Og==").build();
		Response response = client.newCall(request).execute();
	}

}
