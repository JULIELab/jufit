/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.Map;

public class DeleteIfContainsResidual extends AbstractDeleteIfContains {

	private static final String RULENAME = "RES";

	public DeleteIfContainsResidual(final Map<String, String[]> parameters) {
		super(RULENAME);
		if (!parameters.containsKey(PARAMETER_PATTERNS))
			throw new IllegalArgumentException();
		matcher = prepareMatcher(parameters.get(PARAMETER_PATTERNS));
	}

	DeleteIfContainsResidual(final String[] patterns) {
		super(RULENAME);
		matcher = prepareMatcher(patterns);
	}
}
