/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;

public class RewriteSemanticType extends Rule {

	private static final String RULENAME = "SEM";
	private final Matcher inRoundParenthesesMatcher = Pattern
			.compile("(?<withPar>\\((?<inPar>[^\\)]*)\\)$)").matcher("");
	private final Matcher inSquareParenthesesMatcher = Pattern
			.compile("(?<withPar>\\[(?<inPar>[^\\]]*)\\]$)").matcher("");
	private final Matcher inAngularParenthesesMatcher = Pattern
			.compile("(?<withPar><(?<inPar>[^>]*)>$)").matcher("");
	private final Set<String> toRemoveRound;
	private final Set<String> toRemoveSquare;
	private final Set<String> toRemoveAngular;

	public RewriteSemanticType(final Map<String, String[]> parameters) {
		this(parameters.get("round"), parameters.get("square"),
				parameters.get("angular"));
	}

	public RewriteSemanticType(final String[] semanticTypes1,
			final String[] semanticTypes2, final String[] semanticTypes3) {
		super(RULENAME);
		toRemoveRound = (semanticTypes1 == null) ? new HashSet<>()
				: Sets.newHashSet(semanticTypes1);
		toRemoveSquare = (semanticTypes2 == null) ? new HashSet<>()
				: Sets.newHashSet(semanticTypes2);
		toRemoveAngular = (semanticTypes3 == null) ? new HashSet<>()
				: Sets.newHashSet(semanticTypes3);
	}

	TermWithSource apply(final TermWithSource tws,
			final Matcher parenthesesMatcher, final Set<String> toRemove) {
		String term = tws.getTerm();
		parenthesesMatcher.reset(term);

		if (parenthesesMatcher.find()) {
			final String withParentheses = parenthesesMatcher.group("withPar");
			final String inParentheses = parenthesesMatcher.group("inPar");
			if (toRemove.contains(inParentheses)) {
				term = term.replace(withParentheses, "");
				term = multiWhitespaces.reset(term).replaceAll(" ").trim();
				return new TermWithSource(term, tws.getLanguage(),
						tws.getIsChem(), tws.getMdifiedByRulesList(), ruleName);
			}
		}
		return null;
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		final ArrayList<TermWithSource> out = new ArrayList<>();
		if (tws.getTerm().contains(")")) {
			final TermWithSource term = apply(tws, inRoundParenthesesMatcher,
					toRemoveRound);
			if (term != null)
				out.add(term);
		}
		if (tws.getTerm().contains("]")) {
			final TermWithSource term = apply(tws, inSquareParenthesesMatcher,
					toRemoveSquare);
			if (term != null)
				out.add(term);
		}
		if (tws.getTerm().contains(">")) {
			final TermWithSource term = apply(tws, inAngularParenthesesMatcher,
					toRemoveAngular);
			if (term != null)
				out.add(term);
		}
		if (!out.isEmpty()) {
			tws.addModifyingRule(ruleName);
			tws.supress();
		}
		return out;
	}
}
