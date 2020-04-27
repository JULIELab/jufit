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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.docopt.Docopt;

import com.google.common.collect.Streams;

import de.julielab.provider.ProvidedTerm;
import de.julielab.provider.SemanticGroup;
import de.julielab.provider.UMLSTermProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.OutputFormat;

public class Main {

	public static final String VERSION = "1.2";

	private static final String doc = "Usage:\n"
			+ " java -jar <JuFiT-file.jar> "
			+ " followed by (on the same line)"
			+ " <mrconso> <mrsty> <language> (--mrconso | --terms | --grounded | --complex) [--outFile=FILE] [--semanticGroup=GROUP]... [--rules=JSON] [--noFilter]\n"
			+ " --help\n" + " jufit --version\n" + "\nOptions:\n"
			+ "--help  Show this screen\n"
			+ "--version  Show the version number\n"
			+ "--mrconso  MRCONSO output format (one format must be chosen)\n"
			+ "--terms  terms only output (one format must be chosen)\n"
			+ "--grounded  terms and CUIs output, separated with \""
			+ Delemmatizer.SEPARATOR + "\" (one format must be chosen)\n"
			+ "--complex  complex output format providing applied rules, also writes removed terms to stderr (one format must be chosen)\n"
			+ "--outFile=FILE  write output to this file instead of stdout\n"
			+ "--semanticGroup=GROUP  Process only terms belonging to a semantic group (repeat for multiple)\n"
			+ "--rules=JSON  file with rules to use instead of defaults (probably not a good idea)\n"
			+ "--noFilter  Do not filter output (incompatible with --mrconso as nothing would need to be done)";
	
	@SuppressWarnings("unchecked")
	public static void main(final String[] args) throws IOException {
		final Map<String, Object> opts = new Docopt(doc).withVersion(VERSION).parse(args);
		final String pathToMRCONSO = (String) opts.get("<mrconso>");
		final String pathToMRSTY = (String) opts.get("<mrsty>");
		final String language = (String) opts.get("<language>");

		if (language.length() != 3) {
			System.err.println("Only 3 letter languages codes supported, e.g., ENG for English");
			System.exit(1);
		}

		final String jsonFile = (String) opts.get("--rules"); //may be null, respected later
		final Set<SemanticGroup> onlyTheseSemanticGroups = new HashSet<>();

		try {
			((List<String>) opts.get("--semanticGroup")).stream()
					.map(SemanticGroup::valueOf)
					.forEach(onlyTheseSemanticGroups::add);
		} catch (final IllegalArgumentException e) {
			System.err.println(
					"Only the following semantic group names are supported:\n"
					+ SemanticGroup.getNames().collect(Collectors.joining(", ")));
			System.exit(1);
		}

		OutputFormat outputFormat = null;
		if ((boolean) opts.get("--mrconso")){
			outputFormat = OutputFormat.MRCONSO;
		}
		else if ((boolean) opts.get("--terms")){
			outputFormat = OutputFormat.TERMS;
		}
		else if ((boolean) opts.get("--grounded")){
			outputFormat = OutputFormat.GROUNDED_TERMS;
		}
			else if ((boolean) opts.get("--complex")){
			outputFormat = OutputFormat.COMPLEX;
		}
		if (outputFormat == null){
			throw new IllegalArgumentException("No valid output format selected!");
		}

		final boolean applyFilters = !(boolean) opts.get("--noFilter");
		if (!applyFilters && (OutputFormat.MRCONSO == outputFormat)){
			throw new IllegalArgumentException("Applying no filtering while producing MRCONSO format is pointless");
		}

		final String outFileName = (String) opts.get("--outFile"); //may be null, respected later
		if(null != outFileName){
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outFileName)), true));
		}

		//Iterate over UMLS to generate list of existing terms
		//TODO Currently respects pre-existing terms of all semantic groups, even those later ignored. Trivial change, unsure what is expected behavior?
		final Set<String> existingTerms = Streams
			.stream(UMLSTermProvider.provideUMLSTerms(pathToMRCONSO, pathToMRSTY, true, null, language))
			.map(ProvidedTerm::getTerm).collect(Collectors.toSet());

		//Prepare to iterate over UMLS again, this time respecting group restrictions (if any)
		final Iterator<ProvidedTerm> iterator =
			UMLSTermProvider.provideUMLSTerms(pathToMRCONSO, pathToMRSTY, true, onlyTheseSemanticGroups, language);

		Delemmatizer.delemmatize(iterator, outputFormat, existingTerms, jsonFile, language, applyFilters);
	}
}
