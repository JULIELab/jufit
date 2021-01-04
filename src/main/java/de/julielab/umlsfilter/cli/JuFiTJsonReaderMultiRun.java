package de.julielab.umlsfilter.cli;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import de.julielab.germanclinicaltext.readbooks.BookProperties;
import de.julielab.umlsfilter.cli.JuFiTJsonProperties;

public class JuFiTJsonReaderMultiRun
{
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException
	{
		String jsonFile = "jufit_config_multi.json";
		System.out.println(getRunConfigurations(jsonFile));
	}

	public static JuFiTJsonProperties getRunConfigurations(String jsonFile)
	{
		JSONParser jsonConfigparser = new JSONParser();
		JuFiTJsonProperties jufitProperties = new JuFiTJsonProperties();
	
		try
		{
			Object obj = jsonConfigparser.parse(new FileReader(jsonFile));
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.get("run_jufit"));
			
			
			JSONArray runs = (JSONArray) jsonObject.get("run_jufit");
			System.out.println(runs.size());
			
			for (int i = 0; i < runs.size(); i++)
			{
				System.out.println(runs.get(i));
			}
			
			
		}
		
//		try
//		{
//			Object obj = jsonConfigparser.parse(new FileReader(jsonFile));
//			JSONObject jsonObject = (JSONObject) obj;
//
//			jufitProperties.setPathToMRCONSO(jsonObject.get("pathToMRCONSO").toString());
//			jufitProperties.setPathToMRSTY(jsonObject.get("pathToMRSTY").toString());
//			jufitProperties.setLanguage(jsonObject.get("language").toString());
//			jufitProperties.setOutFileName(jsonObject.get("outFileName").toString());
//			jufitProperties.setOutputFormat(jsonObject.get("outputFormat").toString());
//
//			JSONArray arraySemanticGroups = (JSONArray) jsonObject.get("SemanticGroups");
//			List<String> semanticGroups = IntStream.range(0, arraySemanticGroups.size())
//					.mapToObj(arraySemanticGroups::get)
//					.map(Object::toString)
//					.collect(Collectors.toList());
//			jufitProperties.setSemanticGroups(semanticGroups);
//
//			JSONArray arraySemanticTypes = (JSONArray) jsonObject.get("SemanticTypes");
//			List<String> semanticTypes = IntStream.range(0, arraySemanticTypes.size())
//					.mapToObj(arraySemanticTypes::get)
//					.map(Object::toString)
//					.collect(Collectors.toList());
//			jufitProperties.setSemanticTypes(semanticTypes);
//		}
		catch (Exception e)
		{
			System.out.println("Cannot read the json file '" + jsonFile + "'. Check your file and path.");
		}
		return jufitProperties;
	}
}