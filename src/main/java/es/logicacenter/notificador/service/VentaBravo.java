package es.logicacenter.notificador.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.entity.Venta;
import es.logicacenter.notificador.vo.InventarioResponse;
import es.logicacenter.notificador.vo.Respuesta;
import es.logicacenter.notificador.vo.Transaction;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class VentaBravo {
	private static final Logger log = LoggerFactory.getLogger(VentaBravo.class);

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
	public VentaBravo(VentaService ventaService) {
		this.ventaService = ventaService;
	}

	public void bravoMain() throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\r\n    \"storeId\": \"47741e3f-634c-5944-a651-becbfee55cf7\",\r\n    \"userId\": \"cb02287f-7b46-5497-a2f6-114c3dcd60e4\",\r\n    \"countryId\": 2,\r\n    \"timezone\": \"America/Bogota\",\r\n    \"locale\": \"es-CO\",\r\n    \"zoom\": 25\r\n}");
		Request request = new Request.Builder()
				.url("https://api.prod.treinta.co/transaction/by-store/87d65767-13d2-58c7-a5d8-76c4ad6df3e0")

				.addHeader("X-Api-Key", "TRE1NT@WEB")
				.addHeader("X-Hash-Signature", "94230538bfea6b3dbb422444c02973e119ee82f20c30ab3673df0fa70ffe3465")
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization",
						"Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjNmZDA3MmRmYTM4MDU2NzlmMTZmZTQxNzM4YzJhM2FkM2Y5MGIyMTQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdHJlaW50YWNvIiwiYXVkIjoidHJlaW50YWNvIiwiYXV0aF90aW1lIjoxNzMzMTYxODgwLCJ1c2VyX2lkIjoicnpaR1JyUk0zc1djeEZZNEdXN3Q4YkpsSUt1MiIsInN1YiI6InJ6WkdSclJNM3NXY3hGWTRHVzd0OGJKbElLdTIiLCJpYXQiOjE3MzMxNjE4ODAsImV4cCI6MTczMzE2NTQ4MCwicGhvbmVfbnVtYmVyIjoiKzUyMjQ4MTM0OTYwMCIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsicGhvbmUiOlsiKzUyMjQ4MTM0OTYwMCJdfSwic2lnbl9pbl9wcm92aWRlciI6ImN1c3RvbSJ9fQ.LvtWFE7uQQUzcdngUy9SWcxevvMTghiQftT2KwiBG9MJ-33eOqiKdA0mXOAHX16KtyTdt80WX4MEaHax8SsixJTZlJBmjI7c6XPank8S4iLPWoFateK9ZK5Ipm1FldeuneMlbkdUlXzBgDtiWnHtJYQikwKNOaBtsC7nKSXOoSCkPNgAiupWomsSCiLsW5QQs86FY9iUwL1YckZ0ia6oL4W49DoSYEhj5Y2mgMtdLF2ZlDIejKXL6N1_xUZRt-ld4VrV3Lf7aRFkSho1no_XNh_EEEG4gHdsuQm6mPYRJniSzSA5eGeVHzKPgbq_E2ptsfKqM3wZJgdLfwQEC_XJFQ")
				.build();
		Response response_ = client.newCall(request).execute();
		String jsonString = response_.body().string();

		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules(); // Configuración adicional para manejar fechas
			Respuesta response = mapper.readValue(jsonString, Respuesta.class);

			// Imprime los valores deserializados

			//

			for (Transaction transaction : response.getTransactions()) {
				int isExiteFolio = ventaService.countVentaPorfolio(transaction.getId());
				// si existe folio persitimos en tabla
				if (isExiteFolio == 0) {
					log.info("Se Genera Venta en tienda Bravo " + transaction.getId());
					Venta venta = new Venta();
					venta.setConsecutivo(0);
					venta.setTipo("venta");
					venta.setFechaHora(fechaHoraVenta());
					venta.setFolio(transaction.getId());
					venta.setFechaVenta(transaction.getCreatedAt());
					
					//String soloFecha = fechaHoraVenta().substring(0, 10);
					String soloFecha = venta.getFechaVenta().substring(0, 10);
					venta.setMonto(transaction.getValue());
					double totalM = SumaMontoTotal( "63346365-3062-5566-b730-333266333235".trim(),"87d65767-13d2-58c7-a5d8-76c4ad6df3e0".trim() );
					double operacionMonto =totalM + venta.getMonto();
					// 30/12/2024 Venta LOGICA CENTER ROSAS centro de carga tipo c - Pagada Efectivo $200
					String msjeVentDescripcion = soloFecha + "💰 lógica Center bravo "+ transaction.getDescription() + "- Pagada Efectivo $" +transaction.getValue() +  " 💵 Monto Total de Venta $" + operacionMonto;
					
					
					venta.setDescripcion( msjeVentDescripcion );
					venta.setStoreId(transaction.getStoreId());
					venta.setUserId(transaction.getUserId());
					// se envia la notificacion
					venta.setIsNotificacion(messageEnviadoNotificacionVenta(msjeVentDescripcion));
					persitenciaVenta(venta);
					
					//se envia notifcacion inventario);
					TelegramBotDynamicTemplate telegramBotDynamicTemplate = new TelegramBotDynamicTemplate(token);
					telegramBotDynamicTemplate.SigleTelegramBravo(diaMax, token, chatidInventario);
					
				} else {
					//*******
					log.info("No hay ventasen tienda Bravo");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

	}
	
	public double SumaMontoTotal(String userId, String storeId) {
	    Double sumaMonto = ventaService.SumaMonto(userId, storeId);
	    return sumaMonto != null ? sumaMonto : 0.0;
	}
	
	
	private String fechaHoraVenta() {
		 LocalDateTime now = LocalDateTime.now();

	        // Definir el formato de la fecha y hora
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        // Convertir la fecha y hora a String
	        String formattedDate = now.format(formatter);
	        return formattedDate;
	}
	
	
	public int  messageEnviadoNotificacionVenta(String msj) {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(
				"https://api.telegram.org/bot7407891330:AAGdJGgQAapdSAAdBd5F3Uv761-ahMgrG0I/sendMessage?chat_id=@NotificacionVenta&text="
						+ msj)
				.addHeader("Authorization", "Basic Og==").build();
		
		try {
			Response response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		log.info("se envia notificacion *******" + msj);
		return 1;
	}
	
	
	

	
	public Venta persitenciaVenta(Venta venta) {
		return ventaService.saveVenta(venta);
	}
}
