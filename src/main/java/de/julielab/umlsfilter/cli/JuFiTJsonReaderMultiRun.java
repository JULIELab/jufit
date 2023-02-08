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

		catch (Exception e)
		{
			System.out.println("Cannot read the json file '" + jsonFile + "'. Check your file and path.");
		}
		return jufitProperties;
	}
}