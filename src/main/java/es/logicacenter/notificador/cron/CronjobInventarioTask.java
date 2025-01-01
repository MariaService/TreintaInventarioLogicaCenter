package es.logicacenter.notificador.cron;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.logicacenter.notificador.service.InventarioBussine;

@Component
public class CronjobInventarioTask {

	private static final Logger log = LoggerFactory.getLogger(CronjobInventarioTask.class);

	@Autowired
	InventarioBussine inventarioBussine;

	@Scheduled(cron = "0 0 12,15,18 * * ?")
	public void test() throws IOException {
		log.info("Tarea ejecutada a las: " + obtenerfechaActual());
		inventarioBussine.consultaServicioInventario();
	
	
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
}
