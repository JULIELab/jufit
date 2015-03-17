/**
 * This is JUFIT, the Jena UMLS Filter
 * Copyright (C) 2015 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.julielab.umlsfilter.cli;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import de.julielab.provider.ProvidedTerm;
import de.julielab.provider.UMLSTermProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.FilterMode;

public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("Needs at least 1 argument\n");
			printTasks();
			System.exit(0);
		}

		final String task = args[0];
		if (args.length > 1)
			args = Arrays.copyOfRange(args, 1, args.length);

		if (TASKS.containsKey(task)) {
			if (args.length < 3) {
				System.err
				.printf("Need at least 2 arguments, got %s\n"
						+ "1, MRCONSO file\n"
						+ "2, MRSTY file\n"
						+ "3, language to process, 3 letter code\n"
						+ "(4, optional json file used instead of default configuration)\n",
						args.length);
				System.exit(0);
			}
			final String pathToMRCONSO = args[0];
			final String pathToMRSTY = args[1];
			final String language = args[2];
			if (language.length() != 3) {
				System.err.println("Only 3 letter languages codes supported, e.g. ENG for English");
				System.exit(0);
			}
			Iterator<ProvidedTerm> iterator = UMLSTermProvider
					.provideUMLSTerms(pathToMRCONSO, pathToMRSTY, true,
							language);
			final Set<String> existingTerms = new HashSet<>();
			while (iterator.hasNext()) {
				final ProvidedTerm term = iterator.next();
				existingTerms.add(Delemmatizer.regularizeTerm(term.getTerm()));
			}

			iterator = UMLSTermProvider.provideUMLSTerms(pathToMRCONSO,
					pathToMRSTY, true, language);
			final String jsonFile = args.length > 3 ? args[3] : null;
			// if (task.equals("fe"))
			// Delemmatizer.delemmatize(iterator, FilterMode.SYNONYM,
			// existingTerms, jsonFile, language);
			// else if (task.equals("fm"))
			// Delemmatizer.delemmatize(iterator, FilterMode.MRCONSO,
			// existingTerms, jsonFile, language);
			// else if (task.equals("fc"))
			// Delemmatizer.delemmatize(iterator,
			// FilterMode.SYNONYM_IF_CHANGED, existingTerms, jsonFile,
			// language);
			// else if
			if (task.equals("ga"))
				Delemmatizer.delemmatize(iterator,
						FilterMode.DO_NOTHING_PRODUCE_GAZETTEER_FILE,
						existingTerms, jsonFile, language);
			else if (task.equals("gf"))
				Delemmatizer.delemmatize(iterator,
						FilterMode.PRODUCE_GAZETTEER_FILE, existingTerms,
						jsonFile, language);
		} else
			printTasks();
	}

	private static void printTasks() {
		System.err.println("Choose a mode");
		for (final String task : TASKS.keySet())
			System.err.println(task + "\t" + TASKS.get(task));
	}

	private static final TreeMap<String, String> TASKS = new TreeMap<String, String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			// put("fm", "filter umls, output mrconso");
			// put("fe", "filter umls, output rewritten entries");
			// put("fc",
			// "filter umls, output rewritten entries if changed, mark deleted");
			put("ga", "generate gazetteer file, without any filtering");
			put("gf", "generate gazetteer file, applying filter");
		}
	};
}
