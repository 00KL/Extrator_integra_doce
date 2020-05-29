package extratorIntegraDoce;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rdf4j.common.net.ParsedIRI;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.query.resultio.UnsupportedQueryResultFormatException;
import org.eclipse.rdf4j.common.net.*;

import com.complexible.stardog.plan.filter.functions.datetime.Date;

//(0)Site                    (1)Sample Point	(2)Data Source	(3)Date	        (4)Sample Ref	(5)Lab Ref	    (6)Sample Type
// Aguas  Interiores	    Acaiaca - Carmo 01	ALS	             8/9/17 14:24	314020-2017-1	314020-2017-1	Superficial
public class Sampling {
//	###  http://purl.org/nemo/examples/doceexample#WaterSampling314020-2017-1
//		:WaterSampling314020-2017-1 rdf:type owl:NamedIndividual ,
//		                                     doce:Sampling ;
//		                            doce:locatedIn :RCA-01 ;
//		                            gufo:hasBeginPointInXSDDateTimeStamp "2009-08-17T14:24:00-03:00"^^xsd:dateTimeStamp ;
//		                            gufo:hasEndPointInXSDDateTimeStamp "2009-08-17T14:24:00-03:00"^^xsd:dateTimeStamp .
	
	String Sampling;
	String pointLongName;
	java.util.Date date;
	SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
	
	public void postSampling(String[] strings, WorkData wd){
		
		this.Sampling = "WaterSampling" + strings[4];
		this.pointLongName = strings[1];
		try {
			this.date = formatter1.parse(strings[3]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//System.out.println("Erro com formato da data.");
			e.printStackTrace();
		}
		sendStatement(wd);
	}
	
	Map<String,String> Prefixos(){
		
		Map<String,String> prefixo =  new HashMap<String,String>();
		prefixo.put( "owl", new String( "http://www.w3.org/2002/07/owl#" )); //@prefix owl: <http://www.w3.org/2002/07/owl#>
		prefixo.put( "rdf", new String( "http://www.w3.org/1999/02/22-rdf-syntax-ns#" )); //@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
		prefixo.put( "xml", new String( "http://www.w3.org/XML/1998/namespace#" )); //@prefix xml: <http://www.w3.org/XML/1998/namespace>
		prefixo.put( "xsd", new String( "http://www.w3.org/2001/XMLSchema#" )); //@prefix xsd: <http://www.w3.org/2001/XMLSchema#>
		prefixo.put( "doce", new String( "http://purl.org/nemo/doce#" )); //@prefix doce: <http://purl.org/nemo/doce#>
		prefixo.put( "gufo", new String( "http://purl.org/nemo/gufo#" )); //@prefix gufo: <http://purl.org/nemo/gufo#>
		prefixo.put( "rdfs", new String( "http://www.w3.org/2000/01/rdf-schema#" )); //@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
		prefixo.put( "unit", new String( "http://qudt.org/vocab/unit#" )); //@prefix unit: <http://qudt.org/vocab/unit/>
		prefixo.put( "dataBase", new String( "http://purl.org/nemo/prefixos/doceprefixo#" )); //@base <http://purl.org/nemo/prefixos/doceprefixo#>
		
		return prefixo;
		
	}
	
	public void sendStatement(WorkData wd) {
		Map<String, String> prefixos = this.Prefixos();
		//tripla -> sujeito relação objeto 
		
		//Sujeito
		IRI newSampling = wd.createIRI(prefixos.get("dataBase"), this.Sampling);
		
		//Objeto
		IRI samplingType = wd.createIRI(prefixos.get("doce"), "Sampling");
		//data é um objeto literal, por isso é criado na declaração do Statment
		
		IRI pointShortName = LongToShortName(this.pointLongName, wd);
		
		
		//relações
		IRI locatedIn = wd.createIRI(prefixos.get("dataBase"), "locatedIn");
		IRI hasBeginPointInXSDDateTimeStamp = wd.createIRI(prefixos.get("doce"), "hasBeginPointInXSDDateTimeStamp");
		IRI hasEndPointInXSDDateTimeStamp = wd.createIRI(prefixos.get("doce"), "hasEndPointInXSDDateTimeStamp");
		
//		//System.out.println(newSampling + "   " + RDF.TYPE.toString() + "   " + OWL.NAMEDINDIVIDUAL.toString());
//		//System.out.println(newSampling + "   " + RDF.TYPE.toString() + "   " + samplingType);
//		//System.out.println(newSampling + "   " + locatedIn.toString() + "   " + pointShortName);
//		//System.out.println(newSampling + "   " + hasBeginPointInXSDDateTimeStamp.toString() + "   " + wd.createLiteralDate(this.date));
//		//System.out.println(newSampling + "   " + hasEndPointInXSDDateTimeStamp.toString() + "   " + wd.createLiteralDate(this.date));
//		//System.out.println();
		
		wd.addStatment2(newSampling, RDF.TYPE, OWL.NAMEDINDIVIDUAL);
		wd.addStatment2(newSampling, RDF.TYPE, samplingType);
		wd.addStatment2(newSampling, locatedIn, pointShortName);
		wd.addStatment2(newSampling, hasBeginPointInXSDDateTimeStamp, wd.createLiteralDate(this.date));
		wd.addStatment2(newSampling, hasEndPointInXSDDateTimeStamp, wd.createLiteralDate(this.date));
//			
	}
	
	IRI LongToShortName(String longName, WorkData wd) {
		try {
			String queryTest = "SELECT ?s WHERE{ ?s ?a ?o FILTER (?a = rdfs:label && ?o = \"" + longName + "\")}";
			String x = wd.sparqlQueryretorna(queryTest);
			//retira ?s padrão do retorno(com o split da quebra de linha e seleçãodo segundo componente) e remove "< >" da IRI 
			String shortName = x.split("\n")[1].replace("<", "").replace(">", "");
			String nameSpaceAndPoint[] = shortName.split("#");
			IRI pointShortName = wd.createIRI(nameSpaceAndPoint[0]+"#", nameSpaceAndPoint[1]);
			return pointShortName;
		} catch (TupleQueryResultHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedQueryResultFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
