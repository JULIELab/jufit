/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Contains information about currently processed term, the mode of processing
 * it and the result of delemmatization.
 *
 * @author buechel
 *
 */

public class TermContainer {
	private final HashMap<String, TermWithSource> terms = new HashMap<>();
	private final String language;
	private final boolean isChem;

	public TermContainer(final String s, final String language,
			final boolean isChemicalOrDrug) {
		terms.put(s, new TermWithSource(s, language, isChemicalOrDrug));
		this.language = language;
		isChem = isChemicalOrDrug;
	}

	void addTerms(final Iterable<TermWithSource> filteredTerms) {
		for (final TermWithSource newTerm : filteredTerms) {
			final String newTermString = newTerm.getTerm();
			if (!terms.containsKey(newTermString)
					|| (newTerm.getNumberOfRules() < terms.get(newTermString)
							.getNumberOfRules()))
				terms.put(newTermString, newTerm);
		}
	}

	public boolean getIsChem() {
		return isChem;
	}

	public String getLanguage() {
		return language;
	}

	public Collection<TermWithSource> getRawTerms() {
		return terms.values();
	}

	public String getTermsAsString() {
		String terms = "";
		for (final String z : getUnsuppressedTermStrings())
			terms = terms + z;
		return terms;
	}

	public ArrayList<String> getUnsuppressedTermStrings() {
		final ArrayList<String> list = new ArrayList<>(terms.size());
		for (final TermWithSource term : terms.values())
			if (!term.getIsSupressed())
				list.add(term.getTerm());
		return list;
	}
}
