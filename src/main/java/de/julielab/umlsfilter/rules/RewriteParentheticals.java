/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;

import de.julielab.umlsfilter.config.ResourceProvider;

/**
 * Deletes parenthetical expressions and returns old and new version of term.
 *
 * Was not used in experiments as Hettne et al. showed negative effects.
 *
 * @author buechel
 *
 */
public class RewriteParentheticals extends Rule {
	private static final String RULENAME = "PAR";
	private final Matcher matcher;

	RewriteParentheticals() throws IOException {
		super(RULENAME);
		matcher = prepareMatcher(
				ResourceProvider.getLanguageIndependentParentheticals());
	}

	public RewriteParentheticals(final Map<String, String[]> parameters)
			throws IOException {
		this();
	}

	protected RewriteParentheticals(final String ruleName,
			final String parentheticals) throws IOException {
		super(ruleName);
		matcher = prepareMatcher(parentheticals);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		ArrayList<TermWithSource> out = null;
		if (!tws.getIsChem()) {
			String term = matcher.reset(tws.getTerm()).replaceAll("");
			term = multiWhitespaces.reset(term).replaceAll(" ");
			term = term.trim();
			if (!term.equals(tws.getTerm())) {
				out = new ArrayList<>();
				out.add(new TermWithSource(term, tws.getLanguage(),
						tws.getIsChem(), tws.getMdifiedByRulesList(),
						ruleName));
			}
		}
		return out;
	}
}
