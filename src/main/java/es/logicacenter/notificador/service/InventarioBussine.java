package es.logicacenter.notificador.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.logicacenter.notificador.entity.Inventario;
import es.logicacenter.notificador.vo.InventarioResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class InventarioBussine {

	private static final Logger log = LoggerFactory.getLogger(InventarioBussine.class);

	private final InventarioService inventarioService;

	
	@Value("${param.dia}")
	private int diaMax;
	
	
	@Autowired
	public InventarioBussine(InventarioService inventarioService) {
		this.inventarioService = inventarioService;
	}

	public void test() {
		log.info("test de persistencia");
		Inventario inv = new Inventario();
		inv.setName("Test");
		inventarioService.saveVenta(inv);
	}


	public void consultaServicioInventario() throws IOException {
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
			// actualizar
			if (producto.getStock() == diaMax) {
				mesajeNotificacionInventario(producto.getName(), producto.getStock(), producto.getPrice());
				log.info(" ***********" + producto.getName() + producto.getStock());

			}

		}
	}

	/// mensaje de notificacion 
	private void mesajeNotificacionInventario(String msj, int stock, double precio) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(
				"https://api.telegram.org/bot7841587623:AAHKEjAlwqeVEtfKmct6tAvdTqW8J4AuH7M/sendMessage?chat_id=@inventariocenter&text="
						+ msj + " " + "Cantidad en Almacen " + stock + " " + "disponibles " + "$" +precio )
				

				.addHeader("Authorization", "Basic Og==").build();
		Response response = client.newCall(request).execute();
	}

}
