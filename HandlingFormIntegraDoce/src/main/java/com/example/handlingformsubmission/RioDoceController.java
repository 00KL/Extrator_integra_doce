package com.example.handlingformsubmission;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//Essa classe define quais funcoes serao chamadas para quais requisicoes
@Controller
public class RioDoceController {
	
	@GetMapping("/") // mapeia a requisicao para a funcao abaixo definida
	public String defaultForm(Model model) {
		model.addAttribute("riodoce", new RioDoce()); // cria um objeto da classe RioDoce e atribui seus campos a palavra riodoce
		return "riodoce"; // O método greetingForm () usa um objeto Model para representar a classe RioDoce na vizualizacao. O objeto 
						  // RioDoce contem os mesmos campos do arquivo riodoce.html(a string dada como retorno diz qual será o nome do arquivo)
						  //o que permite a interface entre ambos.

//						  A implementação do corpo do método depende de uma tecnologia de exibição para executar a renderização do HTML 
//						  no lado do servidor, convertendo o nome da exibição (riodoce) em um modelo para renderização. Nesse caso, 
//						  usamos o Thymeleaf, que analisa o modelo riodoce.html e avalia as várias expressões de modelo para renderizar 
//						  o formulário. 
//						  O arquivo html referente ao formulario esta em src/main/resources/templates/riodoce.html
	}
	
	
	
	@GetMapping("/riodoce")// A mesma função foi definida tanto para a auxência de requisicao como para a requisicao /riodoce.
						   // O que diferencia as requisicoes de mesmo nome e a forma de mapeamento.
						   // A funcao abaixo tem por objetivo coletar informacao, por isso e definida com GetMapping.
						   // Já a próxima, em por objetivo retornar um valor, por isso a definimos com PostMapping.
	public String riodoceForm(Model model) {
		model.addAttribute("riodoce", new RioDoce());
		return "riodoce";// O arquivo html referente ao formulario esta em src/main/resources/templates/riodoce.html
	}

	@PostMapping("/riodoce")
	public String riodoceSubmit(@ModelAttribute RioDoce riodoce, Model model) {
		model.addAttribute("riodoce", riodoce);
		return "resultRiodoce"; //O arquivo html referente ao formulario esta em src/main/resources/templates/resultRiodoce.html
	}

}
