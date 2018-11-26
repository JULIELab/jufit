/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class TermWithSource {
	private static final Joiner DASH_JOINER = Joiner.on("-");

	private final String term;
	private final ArrayList<String> modifiedByRules = new ArrayList<>();
	private boolean supressed;
	private final String language;
	private final boolean isChem;

	TermWithSource(final String term, final String language,
			final boolean isChem) {
		this.term = term;
		this.language = language;
		this.isChem = isChem;
		supressed = false;
	}

	TermWithSource(final String term, final String language,
			final boolean isChem, final List<String> oldRuleNames,
			final String newRuleName) {
		this.term = term;
		this.language = language;
		this.isChem = isChem;
		supressed = false;
		modifiedByRules.addAll(oldRuleNames);
		modifiedByRules.add(newRuleName);
	}

	void addModifyingRule(final String ruleName) {
		modifiedByRules.add(ruleName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TermWithSource other = (TermWithSource) obj;
		if (isChem != other.isChem)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (modifiedByRules == null) {
			if (other.modifiedByRules != null)
				return false;
		} else if (!modifiedByRules.equals(other.modifiedByRules))
			return false;
		if (supressed != other.supressed)
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

	boolean getIsChem() {
		return isChem;
	}

	public boolean getIsSupressed() {
		return supressed;
	}

	String getLanguage() {
		return language;
	}

	List<String> getMdifiedByRulesList() {
		return modifiedByRules;
	}

	public String getModifiedByRulesString() {
		if (modifiedByRules.isEmpty())
			return "";
		else
			return DASH_JOINER.join(modifiedByRules);
	}

	int getNumberOfRules() {
		return modifiedByRules.size();
	}

	public String getTerm() {
		return term;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (isChem ? 1231 : 1237);
		result = (prime * result)
				+ ((language == null) ? 0 : language.hashCode());
		result = (prime * result)
				+ ((modifiedByRules == null) ? 0 : modifiedByRules.hashCode());
		result = (prime * result) + (supressed ? 1231 : 1237);
		result = (prime * result) + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	void supress() {
		supressed = true;
	}

	@Override
	public String toString() {
		return "TermWithSource [term=" + term + ", modifiedByRules="
				+ modifiedByRules + ", supressed=" + supressed + ", language="
				+ language + ", isChem=" + isChem + "]";
	}

}