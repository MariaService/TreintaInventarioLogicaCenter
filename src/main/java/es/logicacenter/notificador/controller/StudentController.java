package es.logicacenter.notificador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

	@GetMapping("/")
	public String showPage(Model model) {
		model.addAttribute("imageName", "http-cat-200.png");
		return "index";
	}

}
