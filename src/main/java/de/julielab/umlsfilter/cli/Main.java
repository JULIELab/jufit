/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

package de.julielab.umlsfilter.cli;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import de.julielab.provider.ProvidedTerm;
import de.julielab.provider.SemanticType;
import de.julielab.provider.UMLSTermProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.OutputFormat;

public class Main {

	public static final String VERSION = "1.2";

	@SuppressWarnings({ "unchecked", "null" })
	public static void main(final String[] args) throws IOException 
	{
		String jsonFile = args[0];
		JuFiTJsonProperties jufitProperties = JuFiTJsonReader.getRunConfigurations(jsonFile);

		final String pathToMRCONSO			= jufitProperties.getPathToMRCONSO();
		final String pathToMRSTY			= jufitProperties.getPathToMRSTY();
		final String language				= jufitProperties.getLanguage();
		final String outFileName			= jufitProperties.getOutFileName();
		final List<String> semanticGroups	= jufitProperties.getSemanticGroups();
		final List<String> semanticTypes	= jufitProperties.getSemanticTypes();

		System.out.println("RUNNING JuFiT -- the Jena UMLS Filter Tool");
		System.out.println("READING json pararmeter input file: '"	+ jsonFile + "'");
		System.out.println("path to MRSTY: '"						+ pathToMRSTY + "'");
		System.out.println("path to MRCONSO: '"						+ pathToMRCONSO + "'");
		System.out.println("language: '"							+ language + "'");
		System.out.println("output file: '"							+ outFileName + "'");

		final Set<SemanticType> onlyTheseSemanticTypes = new HashSet<>();

		try
		{
			jufitProperties.getSemanticTypes().stream().map(SemanticType::valueOf).forEach(onlyTheseSemanticTypes::add);
			System.out.println("Semantic Types: "					+ semanticTypes);
		}
		catch (final IllegalArgumentException e)
		{
			if (onlyTheseSemanticTypes.isEmpty())
			{
				System.out.println("Semantic Types: empty");
			}
			else
			{
				System.err.println("Only UMLS Semantic Type names are supported (values between T001 and T204).\n"
						+ "Look into the files from https://metamap.nlm.nih.gov/SemanticTypesAndGroups.shtml\n");
					System.exit(1);
			}
		}

		if (!(semanticGroups.isEmpty()))
		{
			System.out.println("Semantic Groups: "					+ semanticGroups);

			for (int i = 0; i < semanticGroups.size(); i++)
			{
				Stream<SemanticType> value_semantic_types = Stream.of(SemanticType.values());//.map(SemanticType::name);
				String semanticGroup = semanticGroups.get(i);
				Stream<SemanticType> valuesSemanticGroups = value_semantic_types.filter(e -> e.semanticGroup.equals(semanticGroup));
				for (Iterator<SemanticType> j = valuesSemanticGroups.iterator(); j.hasNext();)
				{
					SemanticType element = j.next();
					onlyTheseSemanticTypes.add(element);
				}
			}
		}
		else
		{
			System.out.println("Your given Set of 'Semantic Groups' is empty.");
		}



		if ((semanticGroups.isEmpty()) && (semanticTypes.isEmpty()))
		{
			System.out.println("Processing all Semantic Groups / Semantic Types.");
			Stream.of(onlyTheseSemanticTypes).forEach(n -> System.out.println(n));
		}

		OutputFormat outputFormat = null;
		if (jufitProperties.getOutputFormat().equals("-mrconso"))
		{
			outputFormat = OutputFormat.MRCONSO;
		}
		else if (jufitProperties.getOutputFormat().equals("terms"))
		{
			outputFormat = OutputFormat.TERMS;
		}
		else if (jufitProperties.getOutputFormat().equals("grounded"))
		{
			outputFormat = OutputFormat.GROUNDED_TERMS;
		}
		else if (jufitProperties.getOutputFormat().equals("complex"))
		{
			outputFormat = OutputFormat.COMPLEX;
		}
		else if (outputFormat.equals(null))
		{
			throw new IllegalArgumentException("No valid output format selected!");
		}

		if (!(outFileName.equals(null)))
		{
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outFileName)), true));
		}
		final boolean applyFilters = false;
//		final boolean applyFilters = !(boolean) opts.get("--noFilter");
//		if (!applyFilters && (OutputFormat.MRCONSO == outputFormat))
//		{
//			throw new IllegalArgumentException("Applying no filtering while producing MRCONSO format is pointless");
//		}

//		final String outFileName = (String) opts.get("--outFile"); //may be null, respected later
//		if(null != outFileName){
//			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outFileName)), true));
//		}

		//Iterate over UMLS to generate list of existing terms
		//TODO Currently respects pre-existing terms of all semantic groups, even those later ignored. Trivial change, unsure what is expected behavior?
		final Set<String> existingTerms = Streams
			.stream(UMLSTermProvider.provideUMLSTerms(pathToMRCONSO, pathToMRSTY, true, null, language))
			.map(ProvidedTerm::getTerm).collect(Collectors.toSet());

		//Prepare to iterate over UMLS again, this time respecting group restrictions (if any)
		final Iterator<ProvidedTerm> iterator =
			UMLSTermProvider.provideUMLSTerms(pathToMRCONSO, pathToMRSTY, true, onlyTheseSemanticTypes, language);

		Delemmatizer.delemmatize(
				iterator,
				outputFormat,
				existingTerms,
				jsonFile,
				language,
				applyFilters
				);
	}
}