package extratorIntegraDoce;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.rdf4j.model.IRI; //Permite a criação de Internationalized Resource Identifiers (IRIs)

import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.TupleQueryResultHandlerException;
import org.eclipse.rdf4j.query.resultio.UnsupportedQueryResultFormatException;

//(0)Site                    (1)Sample Point	(2)Data Source	(3)Date	        (4)Sample Ref	(5)Lab Ref	    (6)Sample Type
//Aguas  Interiores	         Acaiaca-Carmo01	ALS	             8/9/17 14:24	314020-2017-1	314020-2017-1	Superficial
public class Sample {
//	:WaterSample314020-2017-1 rdf:type owl:NamedIndividual ,
//    doce:SurfaceWaterSample ;
//	  doce:represents doce:RioDoce ;
//	  doce:wasMeasuredIn :WaterMeasurementAlkalinity001 ; //usar "001" após o nome da medida significa que eu tenho q por um 
														  //nome único em cada identificador de medida de cada elemento?
														  //Nesse caso vale mais a pena so usar o identificador da medida. 
														  //a menos que aquela medição fosse ser usada em outras amostragens, 
														  // o que n faz sentido pq a mediçao é inerente a amostra de onde 
														  // foi medida.
//	  gufo:wasCreatedIn :WaterSampling314020-2017-1 .
	
	String waterSample;
	String waterSampling;
	
	//precisa receber o cabeçalho para identificar o elemento da coluna
	public void postSample(String cabecalho[], String[] linha, WorkData WD){
		
		this.waterSample = "WaterSample" + linha[4].replaceAll("\\.", "");  
		//System.out.println(this.waterSample);
//		return;
		this.waterSampling = "WaterSampling" + linha[4].replaceAll("\\.", "");
		                                                   
		sendStatement(cabecalho, linha, WD);                                 
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
	
	//Foi escolhida hashmap para que fosse possível usar a string de entrada para acessar facilmente 
	//as palavras correspondentes na Ontologia.
	//Foi escolhido Map<String,String> ao inves de Map<String,String[]> para facilitar a construçao,
	//o acesso será feito pela função split
	Map<String,String> qualidadeDaAgua(){
		Map<String,String> qualidadeDaAgua =  new HashMap<String,String>();
		
		//Água - elementos quimicos
		qualidadeDaAgua.put("Alcalinidade-total",new String( "http://purl.org/nemo/doce#TotalAlkalinityAsCaCO3,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Aluminio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedAluminiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Aluminio-total",new String( "http://purl.org/nemo/doce#TotalAluminiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Antimonio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedAntimonyConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Antimonio-total",new String( "http://purl.org/nemo/doce#TotalAntimonyConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Arsenio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedArsenicConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Arsenio-total",new String( "http://purl.org/nemo/doce#TotalArsenicConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Bario-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedBariumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Bario-total",new String( "http://purl.org/nemo/doce#TotalBariumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Berilio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedBerylliumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Berilio-total",new String( "http://purl.org/nemo/doce#TotalBerylliumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Boro-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedBoronConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Boro-total",new String( "http://purl.org/nemo/doce#TotalBoronConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cadmio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedCadmiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cadmio-total",new String( "http://purl.org/nemo/doce#TotalCadmiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Calcio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedCalciumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Calcio-total",new String( "http://purl.org/nemo/doce#TotalCalciumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Carbono-organico-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedOrganicCarbonConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Carbono-organico-total",new String( "http://purl.org/nemo/doce#TotalOrganicCarbonConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Chumbo-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedLeadConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Chumbo-total",new String( "http://purl.org/nemo/doce#TotalLeadConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cianeto",new String( "http://purl.org/nemo/doce#CyanideConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cloreto-total",new String( "http://purl.org/nemo/doce#TotalChlorideConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cobalto-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedCobaltConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cobalto-total",new String( "http://purl.org/nemo/doce#TotalCobaltConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cobre-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedCopperConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cobre-total",new String( "http://purl.org/nemo/doce#TotalCopperConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cor-verdadeira",new String( "http://purl.org/nemo/doce#TrueColor,http://purl.org/nemo/doce#HazenUnit"));
		qualidadeDaAgua.put("Cromo-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedChromiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Cromo-total",new String( "http://purl.org/nemo/doce#TotalChromiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Feoftina",new String( "http://purl.org/nemo/doce#PheophytinConcentration,http://qudt.org/vocab/unit#MicroGM-PER-L"));
		qualidadeDaAgua.put("Ferro-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedIronConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Ferro-total",new String( "http://purl.org/nemo/doce#TotalIronConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Fosfato",new String( "http://purl.org/nemo/doce#PhosphateConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Fosforo-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedPhosphorusConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Fosforo-total",new String( "http://purl.org/nemo/doce#TotalPhosphorousConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Magnesio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedMagnesiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Magnesio-total",new String( "http://purl.org/nemo/doce#TotalMagnesiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Manganes-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedManganeseConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Manganes-total",new String( "http://purl.org/nemo/doce#TotalManganeseConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Mercurio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedMercuryConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Mercurio-total",new String( "http://purl.org/nemo/doce#TotalMercuryConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Metilmercurio",new String( "http://purl.org/nemo/doce#MethylmercuryConcentration,http://qudt.org/vocab/unit#MicroGM-PER-L"));
		qualidadeDaAgua.put("Molibdenio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedMolybdenumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Molibdenio-total",new String( "http://purl.org/nemo/doce#TotalMolybdenumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Niquel-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedNickelConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Niquel-total",new String( "http://purl.org/nemo/doce#TotalNickelConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Nitrato",new String( "http://purl.org/nemo/doce#NitrateConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Nitrito",new String( "http://purl.org/nemo/doce#NitriteConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Nitrogenio-amoniacal",new String( "http://purl.org/nemo/doce#AmmoniacalNitrogenConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Nitrogenio-kjeldahl total",new String( "http://purl.org/nemo/doce#KjeldahlMethodNitrogenConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Nitrogenio-organico",new String( "http://purl.org/nemo/doce#OrganicNitrogenConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Oxigenio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedOxygenConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Oxigenio-dissolvido-saturado",new String( "http://purl.org/nemo/doce#OxygenSaturation,http://qudt.org/vocab/unit#PERCENT"));
		qualidadeDaAgua.put("pH",new String( "http://purl.org/nemo/doce#PH,http://purl.org/nemo/doce#PH"));
		qualidadeDaAgua.put("Polifosfato",new String( "http://purl.org/nemo/doce#PolyphosphateConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Potassio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedPotassiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Potencial-redox",new String( "http://purl.org/nemo/doce#RedoxPotential,http://qudt.org/vocab/unit#MilliV"));
		qualidadeDaAgua.put("Prata-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedSilverConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Prata-total",new String( "http://purl.org/nemo/doce#TotalSilverConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Profundidade-de-coleta",new String( "http://purl.org/nemo/doce#SamplingDepth,http://qudt.org/vocab/unit/M"));
		qualidadeDaAgua.put("Salinidade",new String( "http://purl.org/nemo/doce#Salinity,http://purl.org/nemo/doce#PSU"));
		qualidadeDaAgua.put("Selenio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedSeleniumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Selenio-total",new String( "http://purl.org/nemo/doce#TotalSeleniumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Silica-dissolvida",new String( "http://purl.org/nemo/doce#DissolvedSilicaConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Sodio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedSodiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Sodio-total",new String( "http://purl.org/nemo/doce#TotalSodiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Solidos-dissolvidos totais",new String( "http://purl.org/nemo/doce#TotalDissolvedSolids,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Solidos-sedimentaveis",new String( "http://purl.org/nemo/doce#SettleableSolids,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Solidos-suspensos totais",new String( "http://purl.org/nemo/doce#TotalSuspendedSolids,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Solidos-totais",new String( "http://purl.org/nemo/doce#TotalSolids,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Sulfato",new String( "http://purl.org/nemo/doce#SulphateConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Sulfetos-totais",new String( "http://purl.org/nemo/doce#TotalSulfideConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Temperatura-ambiente",new String( "http://purl.org/nemo/doce#AmbientTemperature,http://qudt.org/vocab/unit/DEG_C"));
		qualidadeDaAgua.put("Temperatura-da-amostra",new String( "http://purl.org/nemo/doce#Temperature,http://qudt.org/vocab/unit/DEG_C"));
		qualidadeDaAgua.put("Transparencia-da-agua",new String( "http://purl.org/nemo/doce#SecchiDiskTransparency,http://qudt.org/vocab/unit/M"));
		qualidadeDaAgua.put("Turbidez",new String( "http://purl.org/nemo/doce#Turbidity,http://qudt.org/vocab/unit/NTU"));
		qualidadeDaAgua.put("Vanadio-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedVanadiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Vanadio-total",new String( "http://purl.org/nemo/doce#TotalVanadiumConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Zinco-dissolvido",new String( "http://purl.org/nemo/doce#DissolvedZincConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		qualidadeDaAgua.put("Zinco-total",new String( "http://purl.org/nemo/doce#TotalZincConcentration,http://qudt.org/vocab/unit#MilliGM-PER-L"));
		
		//Descarga liquida
		
		
		return qualidadeDaAgua;
	}
	
	public void sendStatement(String cabecalho[], String[] linha, WorkData wd) {
		Map<String, String> prefixos = this.Prefixos();
		
		//tripla -> sujeito relação objeto
			
//		Sujeito
		IRI newSample = wd.createIRI(prefixos.get("dataBase"), this.waterSample);
		
//		Objeto
		IRI samplingType  = wd.createIRI(prefixos.get("doce"), this.waterSampling);
		IRI surfaceWaterSample = wd.createIRI(prefixos.get("doce"), "SurfaceWaterSample");
		IRI RioDoce = wd.createIRI(prefixos.get("doce"), "RioDoce");
	
//		Relações
		IRI wasCreatedIn = wd.createIRI(prefixos.get("gufo"), "wasCreatedIn");
		IRI represents = wd.createIRI(prefixos.get("doce"), "represents");
		
//		//System.out.println(newSample + "   " + RDF.TYPE.toString() + "   " + OWL.NAMEDINDIVIDUAL);
//		//System.out.println(newSample + "   " + RDF.TYPE.toString() + "   " + surfaceWaterSample);
//		//System.out.println(newSample + "   " + wasCreatedIn  + "   " + samplingType);
//		//System.out.println(newSample + "   " + represents + "   " + RioDoce);
//		
//		//System.out.println();
		
		wd.addStatment2(newSample, RDF.TYPE, OWL.NAMEDINDIVIDUAL);
		wd.addStatment2(newSample, RDF.TYPE, surfaceWaterSample);
		wd.addStatment2(newSample, wasCreatedIn, samplingType);
		wd.addStatment2(newSample, represents, RioDoce);
		
		//------------MEDINDO TEMPO-------------
		//System.out.println("Criação da amostra "+System.currentTimeMillis());
//		  gufo:wasCreatedIn :WaterSampling314020-2017-1 
		
		Sampling s = new Sampling();
		s.postSampling(linha, wd);
		
		//------------MEDINDO TEMPO-------------
		//System.out.println("Criação da amostragem "+System.currentTimeMillis());
		
		postAllMeasurement(cabecalho, linha, wd, prefixos, newSample);
		
	}
	
	
	void postAllMeasurement(String cabecalho[], String[] linha, WorkData wd, Map<String, String> prefixos, IRI sample) {
		
		String elementoQuimicoStr = null;
		Measurement elementoQuimico = new Measurement();
		Map<String, String> qualidadeDaAgua = this.qualidadeDaAgua();
//		  doce:wasMeasuredIn :WaterMeasurementAlkalinity001
		IRI wasMeasuredIn = wd.createIRI(prefixos.get("doce"), "wasMeasuredIn");
		
		for(int i = 0; i < linha.length; i++) {
			if(!linha[i].isEmpty()) {
				elementoQuimicoStr = qualidadeDaAgua.get(cabecalho[i]);
				if(elementoQuimicoStr != null) {
					IRI measurement = elementoQuimico.postMeasurement(cabecalho[i], linha[4].replaceAll("\\.", ""), linha[i], linha[3], wd, elementoQuimicoStr.split(","));
					
//					//System.out.println(sample + "   "  + wasMeasuredIn.toString() + "   "  + measurement);
//					//System.out.println();
					wd.addStatment2(sample, wasMeasuredIn, measurement);
					//------------MEDINDO TEMPO-------------
					//System.out.println("Criação elemento quimico "+System.currentTimeMillis());
				}
				
			}
		}
		
		//------------MEDINDO TEMPO-------------
		//System.out.println("Criação de todos os elementos quimicos "+System.currentTimeMillis());
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
