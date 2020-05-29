package extratorIntegraDoce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;

public class Measurement {
//	:WaterMeasurementAlkalinity001 rdf:type owl:NamedIndividual ,
//		    doce:Measurement ;
//			doce:expressedIn unit:MilliGM-PER-L ;
//			doce:measuredQualityKind doce:TotalAlkalinityAsCaCO3 ;
//			gufo:hasQualityValue "22.0"^^xsd:double .
	String waterMeasurement;
	String qualityKind;
	String value;
	String unit;
	java.util.Date data;
	
	public IRI postMeasurement(String quemicalElement, String sample, String value, String data, WorkData WD, String[] elementAndUnit  ){
		
		this.waterMeasurement = "WaterMeasurement" + "_" + quemicalElement + "_" + sample;
		this.qualityKind = elementAndUnit[0];
		this.value = value;
		this.unit = elementAndUnit[1];
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			this.data = formatter1.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sendStatement(WD);
		
		
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
	
	
	public IRI sendStatement(WorkData wd) {
		Map<String, String> prefixos = this.Prefixos();
//		//tripla -> sujeito relação objeto 
//		
//		//Sujeito
		IRI waterMeasurement = wd.createIRI(prefixos.get("dataBase"), this.waterMeasurement);
//		
//		//Objeto
		IRI unit = wd.createIRI(this.unit);
		IRI measurement = wd.createIRI(prefixos.get("doce"), "Measurement");
		IRI qualityKind = wd.createIRI(this.qualityKind);
		
//		//data é um objeto literal, por isso é criado na declaração do Statment
//		
		//relações
		IRI expressedIn = wd.createIRI(prefixos.get("doce"), "expressedIn");
		IRI measuredQualityKind = wd.createIRI(prefixos.get("doce"), "measuredQualityKind");
		IRI hasQualityValue = wd.createIRI(prefixos.get("gufo"), "hasQualityValue");
		IRI hasBeginPointInXSDDateTimeStamp = wd.createIRI(prefixos.get("doce"), "hasBeginPointInXSDDateTimeStamp");
		IRI hasEndPointInXSDDateTimeStamp = wd.createIRI(prefixos.get("doce"), "hasEndPointInXSDDateTimeStamp");
		
		
		//test
//		//System.out.println(waterMeasurement + "   " + RDF.TYPE.toString() + "   " + OWL.NAMEDINDIVIDUAL);
//		//System.out.println(waterMeasurement + "   " + RDF.TYPE.toString() + "   " + measurement);
//		//System.out.println(waterMeasurement + "   " + expressedIn.toString() + "   " + unit);
//		//System.out.println(waterMeasurement + "   " + measuredQualityKind.toString() + "   " + qualityKind);
//		//System.out.println(waterMeasurement + hasBeginPointInXSDDateTimeStamp.toString() + wd.createLiteralDate(this.data));
//		//System.out.println(waterMeasurement + hasEndPointInXSDDateTimeStamp.toString() + wd.createLiteralDate(this.data));
//		if(!this.value.contains("<")) {
//			//System.out.println(waterMeasurement + "   " + hasQualityValue.toString() + "   " + wd.createLiteral(Double.parseDouble(this.value)));
//		} else {
//			//System.out.println(waterMeasurement + "   " + hasQualityValue.toString() + "   " +  wd.createLiteral(this.value));
//		}
		
//		//System.out.println();
		
		
		wd.addStatment2(waterMeasurement, RDF.TYPE, OWL.NAMEDINDIVIDUAL);
		wd.addStatment2(waterMeasurement, RDF.TYPE, measurement);
		wd.addStatment2(waterMeasurement, expressedIn, unit);
		wd.addStatment2(waterMeasurement, measuredQualityKind, qualityKind);
		wd.addStatment2(waterMeasurement, hasBeginPointInXSDDateTimeStamp, wd.createLiteralDate(this.data));
		wd.addStatment2(waterMeasurement, hasEndPointInXSDDateTimeStamp, wd.createLiteralDate(this.data));
		if(!this.value.contains("<")) {
			wd.addStatment2(waterMeasurement, hasQualityValue, wd.createLiteral(Double.parseDouble(this.value)));
		} else {
			wd.addStatment2(waterMeasurement, hasQualityValue,  wd.createLiteral(this.value));
		}
		
		return waterMeasurement;
		
	}
}
