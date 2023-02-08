/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.Map;

public class DeleteIfContainsDosage extends AbstractDeleteIfContains {

	private static final String RULENAME = "DOSAGE";

	private static final String END = "( |$|/|\\*)";
	private static final String PREFIX = "(nano|micro|mili|kilo|[ckmnµdnphu])";
	private static final String UNIT = "(gram|liter|meter|m|g|l|mol|h)";
	private static final String PREFIXED_UNIT = "(" + PREFIX + "?" + UNIT
			+ "|(I?U))";
	private static final String START = "(^| |/|((^| |\\.|,|/)\\d+ ?))";

	public DeleteIfContainsDosage(final Map<String, String[]> parameters) {
		this(parameters.get("day"));
	}

	DeleteIfContainsDosage(final String... strings) {
		super(RULENAME);
		String day;
		if ((strings != null) && (strings.length > 0)) {
			if (strings.length == 1)
				day = "(" + START + "?(" + strings[0] + ")" + END + ")";
			else
				day = "(" + START + "?(" + combineParts(strings) + ")" + END
						+ ")";
			matcher = prepareMatcher("%|" + day + "|(" + START + "("
					+ PREFIXED_UNIT + "|(i?u))" + END + ")");
		} else
			matcher = prepareMatcher("%|(" + START + "(" + PREFIXED_UNIT
					+ "|(i?u))" + END + ")");
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		if (matcher.reset(tws.getTerm().toLowerCase()).find()) {
			tws.supress();
			tws.addModifyingRule(ruleName);
		}
		return null;
	}
}
