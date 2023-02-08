/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

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

public class JuFiTJsonReader
{
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException
	{
		String jsonFile = "jufit_config.json";
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

			jufitProperties.setPathToMRCONSO(jsonObject.get("pathToMRCONSO").toString());
			jufitProperties.setPathToMRSTY(jsonObject.get("pathToMRSTY").toString());
			jufitProperties.setLanguage(jsonObject.get("language").toString());

			JSONArray arraySemanticTypes = (JSONArray) jsonObject.get("SemanticTypes");
			List<String> semanticTypes = IntStream.range(0, arraySemanticTypes.size())
					.mapToObj(arraySemanticTypes::get)
					.map(Object::toString)
					.collect(Collectors.toList());
			jufitProperties.setSemanticTypes(semanticTypes);

			JSONArray arraySemanticGroups = (JSONArray) jsonObject.get("SemanticGroups");
			List<String> semanticGroups = IntStream.range(0, arraySemanticGroups.size())
					.mapToObj(arraySemanticGroups::get)
					.map(Object::toString)
					.collect(Collectors.toList());
			jufitProperties.setSemanticGroups(semanticGroups);

			;
			
			jufitProperties.setApplyFilters(Boolean.parseBoolean(jsonObject.get("applyFilters").toString()));
			jufitProperties.setRulesFileName(jsonObject.get("rulesFileName").toString());

			jufitProperties.setOutputFormat(jsonObject.get("outputFormat").toString());
			jufitProperties.setOutFileName(jsonObject.get("outFileName").toString());
		}
		catch (Exception e)
		{
			System.out.println("Cannot read the json file '" + jsonFile + "'. Check your file and path.");
		}
		return jufitProperties;
	}
}