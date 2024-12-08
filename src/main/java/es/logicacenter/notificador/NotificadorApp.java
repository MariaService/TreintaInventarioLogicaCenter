package es.logicacenter.notificador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.logicacenter.notificador.cron.CronJobTask;
import es.logicacenter.notificador.service.PdfReader;
import es.logicacenter.notificador.service.VentaServiceImpl;

@SpringBootApplication
@ComponentScan(basePackages = "es.logicacenter.notificador")
@EnableScheduling
public class NotificadorApp {

	public static void main(String[] args) {
		SpringApplication.run(NotificadorApp.class, args);
		// cambio
	}

}
