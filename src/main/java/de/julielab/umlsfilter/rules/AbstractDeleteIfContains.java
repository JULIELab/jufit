/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Takes a pattern using constructor. method 'apply'
 *
 *
 */
abstract class AbstractDeleteIfContains extends Rule {

	protected Matcher matcher;

	protected AbstractDeleteIfContains(final String ruleName) {
		super(ruleName);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		if (matcher.reset(tws.getTerm()).find()) {
			tws.supress();
			tws.addModifyingRule(ruleName);
		}
		return null;
	}
}
