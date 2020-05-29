//Classe principal que recebe a query de consulta ao banco
package extratorIntegraDoce;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

import org.eclipse.rdf4j.model.IRI; //Permite a criação de Internationalized Resource Identifiers (IRIs)

////imports do projeto
//import org.eclipse.rdf4j.model.vocabulary.OWL; //@prefix owl: <http://www.w3.org/2002/07/owl#>
//import org.eclipse.rdf4j.model.vocabulary.RDF;//@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
////@prefix xml: <http://www.w3.org/XML/1998/namespace>
//import org.eclipse.rdf4j.model.vocabulary.XMLSchema; //@prefix xsd: <http://www.w3.org/2001/XMLSchema#>
////@prefix doce: <http://purl.org/nemo/doce#>
////@prefix gufo: <http://purl.org/nemo/gufo#>
//import org.eclipse.rdf4j.model.vocabulary.RDFS;//@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
////@prefix unit: <http://qudt.org/vocab/unit/> .
////@base <http://purl.org/nemo/examples/doceexample#> .

//pode ser necessario eventualmente
//import org.eclipse.rdf4j.model.vocabulary.FOAF; //Permite a utilizacao do prefixo Friend Of A Friend (FOAF) a fim de utilizar predicados já definidos 


import org.eclipse.rdf4j.repository.RepositoryConnection; //Permite a utilizacao de funcoes para conectar-se ao banco

//INPUT: Uma lista de strings
	//OUTPUT: Caso a string esteja bem formada dentro dos padrões estabelicidos pela linguagem SPARQL printa no console o resultado da consulta
	//		  Caso contrário printa no console uma mensagem de erro alertando sobre a mal formacao da string de consulta

public class Extrator {
	
	public static void main(String[] args) throws Exception {
		//Start Connection with Stardog
		StardogConnection SC = new StardogConnection("http://200.137.66.31:5820", "testDB2");
		RepositoryConnection repoConn = SC.getConnection();
		WorkData WD = new WorkData(repoConn);
		long comeco = System.currentTimeMillis();
		//System.out.println("Criando conexão com o banco" + System.currentTimeMillis());
		
		//Read and organize data
		Reader arquivoCsv = new Reader("C:\\Users\\Tieza\\Desktop\\Rio_doce\\dados_test\\agua.csv");
		////System.out.println(arquivoCsv.getCabecalho());
		ArrayList<String[]> tabela = arquivoCsv.getTabela();
		String cabecalho[] = arquivoCsv.getCabecalho();
		
//		//System.out.println("lendo arquivo e armazenando dados" + System.currentTimeMillis());
		
		
		System.out.println("Begin");
		Sample s = new Sample();
		for(int i = 0; i < tabela.size(); i++) {
//			System.out.println(i);
			WD.beginStatment();
			s.postSample(cabecalho, tabela.get(i), WD);
			WD.commitStatment();
			//------------MEDINDO TEMPO-------------
//			long fim = System.currentTimeMillis() - comeco;
//			//System.out.println("Fim do codigo "+ fim);
			
		}
		System.out.println("End");
		
		
		//Post data
//		GeographicPoint geo = new GeographicPoint();
//		for(String[] geoGraficPoint : test) {
////			for(String str : geoGraficPoint) {
////				//System.out.println(str);
////			}
//			geo.postGeograficPoint(geoGraficPoint, WD);
//			
//		}
		
		
		
	}
	
	//INPUT: Uma string 
		//OUTPUT: Caso a string esteja bem formada dentro dos padrões estabelicidos pela linguagem SPARQL retorna o resultado da consulta
		//		  Caso contrário retorna uma mensagem de erro alertando sobre a mal formacao da string de consulta
		public String consulta(String query) throws Exception {
			StardogConnection SC = new StardogConnection("http://200.137.66.31:5820", "testDB2");
			RepositoryConnection repoConn = SC.getConnection();
			WorkData WD = new WorkData(repoConn);
			
			try {
				String x = WD.sparqlQueryretorna(query);
				SC.closeConnection();
				return x;
			} catch (Exception e) {
				// TODO: handle exception
				SC.closeConnection();
				return "Querry inapropriada";
			}
			
			
		}
}





