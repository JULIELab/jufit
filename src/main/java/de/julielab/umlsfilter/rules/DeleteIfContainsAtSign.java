/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.Map;

public class DeleteIfContainsAtSign extends AbstractDeleteIfContains {

	private static final String RULENAME = "ATSIGN";

	DeleteIfContainsAtSign() {
		super(RULENAME);
		matcher = prepareMatcher("@");
	}

	public DeleteIfContainsAtSign(final Map<String, String[]> parameters) {
		this();
	}
}
