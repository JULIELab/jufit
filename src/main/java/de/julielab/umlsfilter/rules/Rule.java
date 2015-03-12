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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;

/**
 * superclass for other rules.
 *
 * @author buechel
 *
 */

public abstract class Rule {
	/**
	 * Count words in input string by applying whitspace tokenizer
	 *
	 * @param s
	 * @return
	 */
	public static int countWords(final String s) {
		return s.trim().split("\\s+").length;
	}

	protected static String combineParts(final String... parts) {
		return "(" + Joiner.on(")|(").join(parts) + ")";
	}

	protected static Matcher prepareMatcher(final String... parts) {
		if (parts.length == 0)
			return null;
		return Pattern.compile(combineParts(parts)).matcher("");
	}

	protected final Matcher multiWhitespaces = Pattern.compile("\\s+").matcher(
			"");
	protected static final String IN_PARENTHESES_TEMPLATE = "(?<withPar>\\OPENPAR(?<inPar>.+?)\\CLOSEPAR)";
	protected static final String PARAMETER_PATTERNS = "pattern";
	protected static final String PARAMETER_STOPWORDS = "stopWords";
	protected static final String PARAMETER_SEMANTIC_TYPES = "semanticTypes";
	protected static final String PARAMETER_MISC = "misc";

	protected static final String PARAMETER_COMPOUND = "compound";

	protected final String ruleName;

	protected Rule(final String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * compatibility with old tests
	 */
	TermContainer apply(final TermContainer termContainer) throws IOException {
		return apply(termContainer, null);
	}

	public TermContainer apply(final TermContainer termContainer,
			final Set<String> existingTerms) throws IOException {
		final ArrayList<TermWithSource> filteredTerms = new ArrayList<>();
		for (final TermWithSource tws : termContainer.getRawTerms())
			if (!tws.getIsSupressed()) {
				final ArrayList<TermWithSource> newlyFilteredTerms = applyOnOneTerm(tws);
				if (newlyFilteredTerms != null)
					for (final TermWithSource filtered : newlyFilteredTerms)
						if ((existingTerms == null)
								|| !existingTerms.contains(Delemmatizer
										.regularizeTerm(filtered.getTerm())))
							filteredTerms.add(filtered);
			}
		termContainer.addTerms(filteredTerms);

		return termContainer;
	}

	abstract ArrayList<TermWithSource> applyOnOneTerm(TermWithSource tws)
			throws IOException;
}
