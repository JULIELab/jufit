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

package de.julielab.umlsfilter.delemmatizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.julielab.provider.ProvidedTerm;
import de.julielab.umlsfilter.config.ResourceProvider;
import de.julielab.umlsfilter.rules.Rule;
import de.julielab.umlsfilter.rules.TermContainer;
import de.julielab.umlsfilter.rules.TermWithSource;

public class Delemmatizer {

	private final static Matcher punctuation = Pattern.compile("\\p{Punct}")
			.matcher("");
	private final static Matcher space = Pattern.compile("\\s").matcher("");
	public static final String LANGUAGE_ENLGLISH = "ENG";
	public static final String LANGUAGE_GERMAN = "GER";
	public static final String LANGUAGE_FRENCH = "FRE";
	public static final String LANGUAGE_SPANISH = "SPA";
	public static final String LANGUAGE_DUTCH = "DUT";

	/**
	 * Entry point from Main class, processes provided umls terms and writer
	 * results to stdout and logging information to stderr
	 * 
	 * @param iterator
	 *            Iterator over provided terms from UMLS
	 * @param mode
	 *            Used to select output format
	 * @param existingTerms
	 *            Pre-existing terms for this language may not be produced as
	 *            new terms
	 * @param jsonRuleFile
	 *            Configuration file to use during processing
	 * @param language
	 *            Language to process, needed to select proper rules (if no
	 *            config is provided)
	 * @throws IOException
	 */
	public static void delemmatize(final Iterator<ProvidedTerm> iterator,
			final FilterMode mode, final Set<String> existingTerms,
			final String jsonRuleFile, final String language)
			throws IOException {
		final Delemmatizer d = new Delemmatizer();

		if (jsonRuleFile != null)
			ResourceProvider.setLanguageRule(language, jsonRuleFile);
		final Set<String> alreadyPrinted = new HashSet<>();

		while (iterator.hasNext()) {
			final ProvidedTerm providedTerm = iterator.next();
			final TermContainer cleanedTerms = FilterMode.DO_NOTHING_PRODUCE_GAZETTEER_FILE == mode ? null
					: d.delemmatizeTerm(providedTerm.getTerm(),
							providedTerm.getLanguageLong(),
							providedTerm.isChemicalOrDrug(), existingTerms);
			if (FilterMode.MRCONSO == mode) {
				for (final TermWithSource term : cleanedTerms.getRawTerms())
					if (!term.getIsSupressed())
						System.out.println(providedTerm.getUpdatedMRCONSO(term
								.getTerm()));
			} else if (FilterMode.SYNONYM == mode) {
				for (final TermWithSource term : cleanedTerms.getRawTerms())
					if (!term.getIsSupressed())
						System.out.println(providedTerm.getCui() + "|"
								+ term.getTerm());
			} else if (FilterMode.SYNONYM_IF_CHANGED == mode) {
				for (final TermWithSource term : cleanedTerms.getRawTerms())
					if (term.getIsSupressed())
						System.out.println(providedTerm.getTerm()
								+ "\t|\tDELETED" + "\t|\t"
								+ providedTerm.isChemicalOrDrug() + "\t|\t"
								+ term.getModifiedByRulesString());
					else if (!providedTerm.getTerm().equals(term.getTerm()))
						System.out.println(providedTerm.getTerm() + "\t|\t"
								+ term + "\t|\t"
								+ providedTerm.isChemicalOrDrug() + "\t|\t"
								+ term.getModifiedByRulesString());
			} else if (FilterMode.DO_NOTHING_PRODUCE_GAZETTEER_FILE == mode) {
				final String term = providedTerm.getTerm();
				final String cui = providedTerm.getCui();
				printGazetteerString(term, cui, "", alreadyPrinted);
			} else if (FilterMode.PRODUCE_GAZETTEER_FILE == mode) {
				final String cui = providedTerm.getCui();
				for (final TermWithSource term : cleanedTerms.getRawTerms())
					if (term.getIsSupressed())
						System.err.printf("Deleted:\t%s\t%s\t%s\n",
								term.getTerm(), cui,
								term.getModifiedByRulesString());
					else if (!term.getModifiedByRulesString().equals(""))
						printGazetteerString(
								term.getTerm(),
								cui,
								providedTerm.getTerm() + "---"
										+ term.getModifiedByRulesString(),
								alreadyPrinted);
					else
						printGazetteerString(term.getTerm(), cui, "",
								alreadyPrinted);
			} else
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Executes all methods for delemmatization one after another. Order:short
	 * form / long form, suppresion rules (not words > 5), rewrite rules
	 * deleting expression, syntactic inversion, possesive s, words > 5.
	 *
	 * @param s
	 *            Processed term
	 * @param language
	 *            Abbreviation or (English) name of the language the term is in.
	 *            Insert "" if unkown.
	 * @param isChemicalOrDrug
	 *            Information about the term being form sematic group Chemical &
	 *            Drugs.
	 * @param rules
	 *            Rules to apply, precompiled for performance
	 * @param lang2existingTerms
	 * @return Returns ArrayList of terms containing delemmatized terms.
	 */
	private static TermContainer delemmatizeTermForRules(final String s,
			final String language, final boolean isChemicalOrDrug,
			final List<Rule> rules, final Set<String> existingTerms)
			throws IOException {
		final TermContainer termContainer = new TermContainer(s, language,
				isChemicalOrDrug);

		for (final Rule rule : rules)
			rule.apply(termContainer, existingTerms);

		return termContainer;
	}

	private static void printGazetteerString(final String term,
			final String cui, final String rule,
			final Set<String> allreadyPrinted) {
		final String cuiAndTerm = cui + regularizeTerm(term);
		if (!allreadyPrinted.contains(cuiAndTerm)) {
			allreadyPrinted.add(cuiAndTerm);
			System.out.println(String.format("%s\tUMLS@@%s@@%s@@ANY", term,
					cui, rule));
		}
	}

	public static String regularizeTerm(final String s) {
		return space.reset(punctuation.reset(s).replaceAll("").toLowerCase())
				.replaceAll("");
	}

	/**
	 * Recycles constructed rules. Will be filled if necessary
	 */
	private final Map<String, List<Rule>> ruleMap = new HashMap<String, List<Rule>>();

	/**
	 * Executes all methods for delemmatization one after another. Order:short
	 * form / long form, suppresion rules (not words > 5), rewrite rules
	 * deleting expression, syntactic inversion, possesive s, words > 5.
	 *
	 * @param s
	 *            Processed term
	 * @param language
	 *            Abbreviation or (English) name of the language the term is in.
	 *            Insert "" if unkown.
	 * @param isChemicalOrDrug
	 *            Information about the term being form sematic group Chemical &
	 *            Drugs.
	 * @return Returns a TermContainer with both supresssed and unsuppressed
	 *         terms
	 */
	private TermContainer delemmatizeTerm(final String s,
			final String language, final boolean isChemicalOrDrug,
			final Set<String> existingTerms) throws IOException {
		if (!ruleMap.containsKey(language))
			prepareRules(language);
		final List<Rule> rules = ruleMap.get(language);
		return delemmatizeTermForRules(s, language, isChemicalOrDrug, rules,
				existingTerms);
	}

	/**
	 * Testing only
	 *
	 * Executes all methods for delemmatization one after another. Order:short
	 * form / long form, suppresion rules (not words > 5), rewrite rules
	 * deleting expression, syntactic inversion, possesive s, words > 5.
	 *
	 * @param s
	 *            Processed term
	 * @param language
	 *            Abbreviation or (English) name of the language the term is in.
	 *            Insert "" if unkown.
	 * @param isChemicalOrDrug
	 *            Information about the term being form sematic group Chemical &
	 *            Drugs.
	 * @return Returns ArrayList of terms containing delemmatized terms.
	 */
	@Deprecated
	ArrayList<String> delemmatizeTermProducingUnsuppressedStrings(
			final String s, final String language,
			final boolean isChemicalOrDrug) throws IOException {
		return delemmatizeTerm(s, language, isChemicalOrDrug, null)
				.getUnsuppressedTermStrings();
	}

	/**
	 * Loads rules specified in json configuration file
	 * 
	 * @param language
	 *            language to load rules for
	 * @throws IOException
	 */
	private void prepareRules(final String language) throws IOException {
		final ArrayList<Rule> rules = new ArrayList<>();
		for (final String rule : ResourceProvider.getRulesForLanguage(language))
			try {
				Class<?> ruleClass;
				ruleClass = rule.contains(".") ? Class.forName(rule) : Class
						.forName("de.julielab.umlsfilter.rules." + rule);
				final Map<String, String[]> parameters = ResourceProvider
						.getRuleParameters(language, rule);
				rules.add((Rule) ruleClass.getDeclaredConstructor(Map.class)
						.newInstance(parameters));
			} catch (final Exception e) {
				e.printStackTrace();
			}
		ruleMap.put(language, rules);
	}

}
