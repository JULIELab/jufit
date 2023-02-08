/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.Map;

public class DeleteIfContainsECNumber extends AbstractDeleteIfContains {

	private static final String RULENAME = "ECNUMBER";

	DeleteIfContainsECNumber() {
		super(RULENAME);
		matcher = prepareMatcher("EC\\p{Z}*\\d(\\.\\d+){0,3}( |$)");
	}

	public DeleteIfContainsECNumber(final Map<String, String[]> parameters) {
		this();
	}
}
