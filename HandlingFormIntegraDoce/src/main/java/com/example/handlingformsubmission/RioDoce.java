package com.example.handlingformsubmission;

import extratorIntegraDoce.Extrator;

public class RioDoce {
	private String resposta;

	public String getResposta() {
		return resposta;
	}
	
	//INPUT:  String contendo a requisicao 
	//OUTPUT: String contendo a reposta da requisicao
	public void setResposta(String resposta) throws Exception {
		Extrator ex = new Extrator(); 
		String s = ex.consulta(resposta);
		System.out.println(s);
		this.resposta = s;
	}
}
