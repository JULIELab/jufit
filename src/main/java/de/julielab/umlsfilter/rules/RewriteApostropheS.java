/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If term contains apostrophe-s "'s", delete the "'s" and return old and new
 * version of term.
 *
 * @author buechel
 *
 */

public class RewriteApostropheS extends Rule {

	private static final String RULENAME = "APO";
	private final Matcher apostropheS = Pattern.compile("'s\\b").matcher("");

	RewriteApostropheS() {
		super(RULENAME);
	}

	public RewriteApostropheS(final Map<String, String[]> parameters) {
		this();
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		ArrayList<TermWithSource> out = null;
		apostropheS.reset(tws.getTerm());
		if (apostropheS.find()) {
			final String s2 = apostropheS.replaceAll("");
			out = new ArrayList<>();
			out.add(new TermWithSource(s2, tws.getLanguage(), tws.getIsChem(),
					tws.getMdifiedByRulesList(), ruleName));
		}
		return out;
	}

}
