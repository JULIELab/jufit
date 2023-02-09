/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.Map;

/**
 * delete terms, longer than 5 words and returns empty list.
 *
 * Was not used in experiments as Hettne et al. showed negative effects.
 *
 * @author buechel
 *
 */

public class DeleteLongTerm extends Rule {

	private static final String RULENAME = "LONG";

	DeleteLongTerm() {
		super(RULENAME);
	}

	public DeleteLongTerm(final Map<String, String[]> parameters) {
		this();
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		if (((Rule.countWords(tws.getTerm())) > 5) && (tws.getIsChem() == false))
		{
			tws.supress();
			tws.addModifyingRule(ruleName);
		}
		return null;
	}

}
