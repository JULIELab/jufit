/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

/**
 * If term contains syntactic Inversion, reestablish normal word order and
 * return old and new version of term.
 *
 * @author hellrich
 *
 */
public class RewriteSyntacticInversion extends Rule {

	private static final Joiner SPACE_JOINER = Joiner.on(" ");
	private static final Joiner DASH_JOINER = Joiner.on("-");
	private static final String RULENAME = "SYN";
	private final Matcher containsDash = Pattern.compile("\\P{Z}-\\p{Z}")
			.matcher("");
	private final Matcher upperThenLowerFirst = Pattern
			.compile("^(.* )*\\p{Lu}\\p{javaLowerCase}\\p{javaLowerCase}+-$")
			.matcher("");
	private final Matcher upperThenLowerSecond = Pattern
			.compile("^(.* )*\\p{Lu}\\p{Ll}\\p{Ll}\\p{Ll}+$").matcher("");
	private final Matcher lowerDashLower = Pattern
			.compile("^\\p{Ll}+-\\p{Ll}+-$").matcher("");
	private final Matcher doubleDash = Pattern.compile("-.*-").matcher("");

	private final boolean compound;
	private final boolean destructive;

	public RewriteSyntacticInversion(final boolean compound,
			final boolean destructive) {
		super(RULENAME);
		this.compound = compound;
		this.destructive = destructive;
	}

	public RewriteSyntacticInversion(final Map<String, String[]> parameters) {
		super(RULENAME);
		if (!parameters.containsKey(PARAMETER_COMPOUND)
				|| (parameters.get(PARAMETER_COMPOUND).length != 1)
				|| !parameters.containsKey(PARAMETER_DESTRUCTIVE)
				|| (parameters.get(PARAMETER_DESTRUCTIVE).length != 1))
			throw new IllegalArgumentException();
		compound = Boolean.parseBoolean(parameters.get(PARAMETER_COMPOUND)[0]);
		destructive = Boolean
				.parseBoolean(parameters.get(PARAMETER_DESTRUCTIVE)[0]);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		ArrayList<TermWithSource> out = null;
		final String s1 = tws.getTerm();
		if (s1.contains(", ")
				&& !s1.substring(s1.indexOf(", ") + 2).contains(", ")
				&& !s1.contains("-, ")) {
			final String[] strings = s1.split(", ");
			for (int i = 0; i < (strings.length / 2); i++) {
				final String temp = strings[i];
				final int toSwitch = strings.length - 1 - i;
				strings[i] = strings[toSwitch];
				strings[toSwitch] = temp;
			}
			String s2 = SPACE_JOINER.join(strings).trim();

			if (containsDash.reset(s2).find())
				if (!compound)
					s2 = s2.replaceAll("- +", "-");
				else if (!doubleDash.reset(strings[0]).find()
						&& upperThenLowerFirst.reset(strings[0]).matches()
						&& upperThenLowerSecond.reset(strings[1]).matches())
					s2 = strings[0].substring(0, strings[0].length() - 1)
							+ strings[1].toLowerCase();
				else if (!tws.getIsChem()
						&& lowerDashLower.reset(strings[0]).matches()
						&& upperThenLowerSecond.reset(strings[1]).matches()) {
					final String[] splits2 = strings[0]
							.substring(0, strings[0].length() - 1).split("-");
					for (int i = 0; i < splits2.length; ++i)
						splits2[i] = Character.toUpperCase(splits2[i].charAt(0))
								+ splits2[i].substring(1, splits2[i].length());
					s2 = DASH_JOINER.join(splits2) + "-" + strings[1];
				} else
					s2 = s2.replaceAll("- +", "-");
			if (!s1.equals(s2) && !s2.equals("")) {
				out = new ArrayList<>();
				out.add(new TermWithSource(s2, tws.getLanguage(),
						tws.getIsChem(), tws.getMdifiedByRulesList(),
						ruleName));
			}
		}
		if ((out != null) && destructive)
			tws.supress();
		return out;
	}

}
