/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015 JULIE LAB Authors:
 * Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package de.julielab.umlsfilter.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;

public class ResourceProvider {
	/**
	 * Fills Map languageDependentResources if empty
	 *
	 * @throws IOException
	 *             if file not found
	 */
	private static void fillLanguageDependentResources() throws IOException {
		if (MapLanguages.isEmpty()) {
			MapLanguages.put(Delemmatizer.LANGUAGE_GERMAN,
					readResourcesFromFile(Delemmatizer.LANGUAGE_GERMAN));
			MapLanguages.put(Delemmatizer.LANGUAGE_ENLGLISH,
					readResourcesFromFile(Delemmatizer.LANGUAGE_ENLGLISH));
			MapLanguages.put(Delemmatizer.LANGUAGE_SPANISH,
					readResourcesFromFile(Delemmatizer.LANGUAGE_SPANISH));
			MapLanguages.put(Delemmatizer.LANGUAGE_FRENCH,
					readResourcesFromFile(Delemmatizer.LANGUAGE_FRENCH));
			MapLanguages.put(Delemmatizer.LANGUAGE_DUTCH,
					readResourcesFromFile(Delemmatizer.LANGUAGE_DUTCH));
		}
	}

	/*****************************/

	public static String[] getLanguageIndependentParentheticals()
			throws IOException {
		prepareLanguageIndependentInformation();
		return languageIndependentResources.parentheticals;
	}

	public static Map<String, String[]> getRuleParameters(
			final String language, final String ruleName) throws IOException {
		fillLanguageDependentResources();
		if (!MapLanguages.containsKey(language))
			throw new IllegalArgumentException(language + " not supported");
		return MapLanguages.get(language).ruleParameters.get(ruleName);
	}

	public static String[] getRulesForLanguage(final String language)
			throws IOException {
		fillLanguageDependentResources();
		if (!MapLanguages.containsKey(language))
			throw new IllegalArgumentException(language + " not supported");
		return MapLanguages.get(language).rules;
	}

	private static void prepareLanguageIndependentInformation()
			throws IOException {
		if (languageIndependentResources == null)
			languageIndependentResources = readLanguageIndependentResourcesFile();
	}

	private static LanguageIndependentResources readLanguageIndependentResourcesFile()
			throws IOException {
		return readResourcesFile(LANGUAGE_INDEPENDENT_RESOURCES, RESOURCE_PATH,
				LanguageIndependentResources.class);
	}

	static <T> T readResourcesFile(final String resourceName,
			final String resourcePath, final Class<T> resourceClass)
					throws IOException {
		final Gson gson = new Gson();
		BufferedReader buffered = null;
		T resources = null;
		// works in Eclipse
		try {
			final FileReader fileReader = new FileReader(resourcePath
					+ resourceName);
			buffered = new BufferedReader(fileReader);
			resources = gson.fromJson(buffered, resourceClass);

		} catch (final Exception e) {
			// works in .jar file
			try {
				final InputStream IS = ResourceProvider.class.getClassLoader()
						.getResourceAsStream(resourceName);
				final Reader reader = new InputStreamReader(IS, "UTF-8");
				final BufferedReader bReader = new BufferedReader(reader);
				resources = gson.fromJson(bReader, resourceClass);
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
		}
		if (resources == null)
			throw new IOException("Problems accessing file, can open neither "
					+ resourcePath + resourceName + " nor load " + resourceName
					+ " from JAR");

		return resources;
	}

	private static LanguageDependentResources readResourcesFromFile(
			final String language) throws IOException {
		return readResourcesFile("resources_" + language + ".json",
				RESOURCE_PATH, LanguageDependentResources.class);
	}

	// TODO: to much state, maps should perhaps be kept in delematizer
	public static void setLanguageRule(final String language,
			final String jsonFile) throws IOException {
		if (!new File(jsonFile).canRead())
			throw new IOException("Can not read " + jsonFile);
		MapLanguages.put(
				language,
				readResourcesFile(jsonFile, "",
						LanguageDependentResources.class));

	}

	private static final String LANGUAGE_INDEPENDENT_RESOURCES = "languageIndependentResources.json";

	private static LanguageIndependentResources languageIndependentResources = null;

	private static final Map<String, LanguageDependentResources> MapLanguages = new HashMap<>();

	private final static String RESOURCE_PATH = "src/main/resources/";

}
