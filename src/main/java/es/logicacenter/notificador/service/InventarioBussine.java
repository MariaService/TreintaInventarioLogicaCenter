package es.logicacenter.notificador.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.logicacenter.notificador.entity.Inventario;

@Component
public class InventarioBussine {

	private static final Logger log = LoggerFactory.getLogger(InventarioBussine.class);

	private final InventarioService inventarioService;
	
	
	

	@Value("${param.dia}")
	private int diaMax;

	@Value("${param.token}")
	private String token;
	
	@Value("${param.chatId}")
	private String chatId;
	
	
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
		TelegramBotDynamicTemplate telegramBotDynamicTemplate = new TelegramBotDynamicTemplate(token);
	telegramBotDynamicTemplate.SigleTelegram( diaMax,token,chatId);
	log.info("notificacion Tienda Bravo...");
	telegramBotDynamicTemplate.SigleTelegramBravo(diaMax, token, chatId);
	}
}
