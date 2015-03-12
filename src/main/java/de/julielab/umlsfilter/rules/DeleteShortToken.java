/**
 * This is JUF, the Jena UMLS Filter
 * Copyright (C) 2015 Johannes HellrichJULIE LAB
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

package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Supresses Term, if after non standard (whitespace + punctuation) tokenization
 * not at least one token exists that consists of 2+ letters and is not a roman
 * numeral
 *
 * @author hellrich
 *
 */
public class DeleteShortToken extends Rule {
	private static final String RULENAME = "SHORT";
	private final Matcher twoOrMoreLetters = Pattern.compile(
			"^.*\\p{L}{2,}+.*$").matcher("");

	// http://stackoverflow.com/questions/267399/how-do-you-match-only-valid-roman-numerals-with-a-regular-expression
	//"^(M{1,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})|M{0,4}(CM|C?D|D?C{1,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})|M{0,4}(CM|CD|D?C{0,3})(XC|X?L|L?X{1,3})(IX|IV|V?I{0,3})|M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|I?V|V?I{1,3}))$")
	//to fancy, false positives
	final Matcher romanNumeral = Pattern
			.compile("^(I{1,3}|(IV)|(VI{0,3}))$")
					.matcher("");

	private final Set<String> stopWords;

	public DeleteShortToken(final Map<String, String[]> parameters) {
		super(RULENAME);
		if (!parameters.containsKey(PARAMETER_STOPWORDS))
			throw new IllegalArgumentException();
		stopWords = new HashSet<String>();
		for (final String stopWord : parameters.get(PARAMETER_STOPWORDS))
			stopWords.add(stopWord);
	}

	DeleteShortToken(final String[] stopWords) {
		super(RULENAME);
		this.stopWords = new HashSet<String>();
		for (final String stopWord : stopWords)
			this.stopWords.add(stopWord);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		final String[] tokens = tws.getTerm().split("\\P{L}");
		boolean keep = false;
		for (final String token : tokens)
			if (twoOrMoreLetters.reset(token).matches()
					&& !romanNumeral.reset(token).matches()
					&& !stopWords.contains(token.toLowerCase())) {
				keep = true;
				break;
			}
		if (!keep) {
			tws.supress();
			tws.addModifyingRule(ruleName);
		}
		return null;
	}
}
