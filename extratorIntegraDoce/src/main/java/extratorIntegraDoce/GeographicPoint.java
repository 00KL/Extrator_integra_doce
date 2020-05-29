package extratorIntegraDoce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.rdf4j.model.IRI; //Permite a criação de Internationalized Resource Identifiers (IRIs)

import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

//(0)Site	(1)Sample Point Long Name	(2)Sample Point Short Name	(3)Lat	(4)Long	
//(5)X	(6)Y	(7)Z	(8)Projection	(9)Datum	(10)Sample Point Category	(11)Comment
public class GeographicPoint {

	String geograficPoint; //Short Name
	String label; //Long Name
	String comment;
	String latitude;
	String longitude; 
	String query;
	
	public void postGeograficPoint(String[] strings, WorkData WD){
		
		this.geograficPoint = strings[2].replaceAll("\\s", "-");
		this.label = strings[1];
		this.comment = strings[11];
		this.latitude = strings[3].replaceAll("\\.", "");
		this.longitude = strings[4].replaceAll("\\.", "");
		sendStatement(WD);
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
		
		//objetos
		IRI newGeographicPoint = wd.createIRI(prefixos.get("dataBase"), this.geograficPoint);
		IRI geographicPointType = wd.createIRI(prefixos.get("doce"), "GeographicPoint");
		//relaçoes
		IRI latitude = wd.createIRI(prefixos.get("doce"), "hasLatitude");
		IRI longitude = wd.createIRI(prefixos.get("doce"), "hasLongitude");
		
		wd.addStatment2(newGeographicPoint, RDF.TYPE, OWL.NAMEDINDIVIDUAL);
		wd.addStatment2(newGeographicPoint, RDF.TYPE, geographicPointType);
		wd.addStatment2(newGeographicPoint, latitude, wd.createLiteral(Float.parseFloat(this.latitude)));
		wd.addStatment2(newGeographicPoint, longitude, wd.createLiteral(Float.parseFloat(this.longitude)));
		wd.addStatment2(newGeographicPoint, RDFS.COMMENT, this.comment);
		wd.addStatment2(newGeographicPoint, RDFS.LABEL, this.label);
		
		
	}
	
	
	
	
}
