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
	 * Combines multiple regular expression parts via disjunction
	 *
	 * @param parts
	 *            Partial regular expressions to be combined
	 * @return a combined regular expression
	 */
	protected static String combineParts(final String... parts) {
		return "(" + Joiner.on(")|(").join(parts) + ")";
	}

	/**
	 * Count words in input string by applying whitspace tokenizer
	 *
	 * @param s
	 *            String to analyze
	 * @return number of whitespace tokens
	 */
	static int countWords(final String s) {
		return s.trim().split("\\s+").length;
	}

	/**
	 * Combines multiple regular expression parts via disjunction and provides a
	 * matcher
	 *
	 * @param parts
	 *            Partial regular expressions
	 * @return a matcher for the combined regular expression
	 */
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

	protected static final String PARAMETER_COMPOUND = "compound";

	protected static final String PARAMETER_DESTRUCTIVE = "destructive";

	protected final String ruleName;

	/**
	 * Super constructor for specific rules
	 *
	 * @param ruleName
	 *            used for logging
	 */
	protected Rule(final String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * kept for compatibility with old tests
	 *
	 * @param termContainer
	 *            of term to be processed
	 * @return the newly generated terms as if there were no prior terms
	 * @throws IOException
	 */
	@Deprecated
	TermContainer apply(final TermContainer termContainer) throws IOException {
		return apply(termContainer, null);
	}

	/**
	 * API call used to process terms
	 *
	 * @param termContainer
	 *            of term to be processed
	 * @param existingTerms
	 *            terms which already exist and thus may not be generated as new
	 *            terms
	 * @return the newly generated terms
	 * @throws IOException
	 */
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

	/**
	 * used in specific rules to process a term
	 *
	 * @param tws
	 *            term with source to process, may be suppressed during
	 *            processing
	 * @return the newly generated terms or null if none exist
	 * @throws IOException
	 */
	abstract ArrayList<TermWithSource> applyOnOneTerm(TermWithSource tws)
			throws IOException;
}
