package es.trapasoft.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.trapasoft.student.cron.CronJobTask;
import es.trapasoft.student.service.PdfReader;
import es.trapasoft.student.service.VentaServiceImpl;

@SpringBootApplication
@ComponentScan(basePackages = "es.trapasoft.student")
@EnableScheduling
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
		// cambio
	}

}
