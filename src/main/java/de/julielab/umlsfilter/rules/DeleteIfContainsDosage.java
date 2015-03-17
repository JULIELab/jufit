/**
 * This is JUFIT, the Jena UMLS Filter
 * Copyright (C) 2015 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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

	public DeleteIfContainsDosage(final Map<String, String[]> parameters) {
		this(parameters.get("day"));
	}
}
