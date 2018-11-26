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

/**
 * If term contains corresponding short and long form, delete long form and
 * return old and new version of term.
 */
public class RewriteShortFormLongForm extends Rule {

	private static final String RULENAME = "SFLF";

	static boolean containsAsToken(final String container,
			final String contained) {
		int start = 0;
		int found = container.indexOf(contained, start);
		while (found != -1) {
			if (((found == 0)
					|| !Character.isLetterOrDigit(container.charAt(found - 1)))
					&& ((container.length() == (found + contained.length()))
							|| !Character.isLetterOrDigit(container
									.charAt(found + contained.length()))))
				return true;
			start = found + contained.length();
			found = container.indexOf(contained, start);
		}
		return false;
	}

	//
	/**
	 * Method findBestLongForm takes as input a short-form and a long- form
	 * candidate (a list of words) and returns the best long-form that matches
	 * the short-form, or null if no match is found.
	 *
	 * see: A Simple Algorithm for Identifying Abbreviation Definitions in
	 * Biomedical Text, A.S. Schwartz, M.A. Hearst, Pacific Symposium on
	 * Biocomputing 8:451-462(2003)
	 *
	 * @author büchel
	 **/
	private static String findBestLongForm(final String shortForm,
			final String longForm) {
		int shortFormIndex;
		int longFormIndex;
		char currChar; // The current character to match
		shortFormIndex = shortForm.length() - 1;
		longFormIndex = longForm.length() - 1;
		// Scan the short form starting from end to start
		for (; shortFormIndex >= 0; --shortFormIndex) {
			currChar = Character.toLowerCase(shortForm.charAt(shortFormIndex));
			if (!Character.isLetterOrDigit(currChar))
				continue;
			/*
			 * Decrease longFormIndex while current character in the long form
			 * does not match the current character in the short form. If the
			 * current character is the first character in the short form,
			 * decrement longFormIndex until a matching character is found at
			 * the beginning of a word in the long form.
			 */
			while (((longFormIndex >= 0) && (Character
					.toLowerCase(longForm.charAt(longFormIndex)) != currChar))
					|| ((shortFormIndex == 0) && (longFormIndex > 0)
							&& (Character.isLetterOrDigit(
									longForm.charAt(longFormIndex - 1)))))
				--longFormIndex;

			while ((longFormIndex > 0) && (shortFormIndex == 0)
					&& !(longForm.charAt(longFormIndex - 1) == ' '))
				--longFormIndex;

			if ((shortFormIndex == 0) && (longFormIndex == 0) && (Character
					.toLowerCase(longForm.charAt(longFormIndex)) != currChar))
				return null;
			// If no match was found in the long form for the current
			// character, return null (no match).
			if (longFormIndex < 0)
				return null;
			// A match was found for the current character. Move to the
			// next character in the long form.
			--longFormIndex;
		}

		// Find the beginning of the first word (in case the first
		// character matches the beginning of a hyphenated word).
		longFormIndex = longForm.lastIndexOf(" ", longFormIndex) + 1;
		// Return the best long form, the substring of the original
		// long form, starting from lIndex up to the end of the original
		// long form.
		return longForm.substring(longFormIndex);
	}

	/**
	 * To increase precision, the algorithm discards long forms that are shorter
	 * than the short form, or that include the short form as one of the words
	 * in the long form.
	 *
	 * @param longForm
	 *            String to test
	 * @param shortForm
	 *            String to test
	 * @return true if conditions are met, false otherwise
	 */
	static boolean longAndShortFormCompatible(final String longForm,
			final String shortForm) {
		if ((shortForm.length() <= longForm.length())
				&& !containsAsToken(longForm, shortForm))
			return true;
		return false;
	}

	private final Matcher noParaMatcher = Pattern.compile("\\(.*\\)")
			.matcher("");

	private final Matcher letterMatcher = Pattern.compile("\\p{L}").matcher("");

	private final Matcher paraMatcherWithNames = Pattern
			.compile(IN_PARENTHESES_TEMPLATE.replace("OPENPAR", "(")
					.replace("CLOSEPAR", ")"))
			.matcher("");

	private final boolean destructive;

	public RewriteShortFormLongForm(final boolean destructive) {
		super(RULENAME);
		this.destructive = destructive;
	}

	public RewriteShortFormLongForm(final Map<String, String[]> parameters) {
		super(RULENAME);
		if (!parameters.containsKey(PARAMETER_DESTRUCTIVE)
				|| (parameters.get(PARAMETER_DESTRUCTIVE).length != 1))
			throw new IllegalArgumentException();
		destructive = Boolean
				.parseBoolean(parameters.get(PARAMETER_DESTRUCTIVE)[0]);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		ArrayList<TermWithSource> out = null;
		final String s = tws.getTerm();
		final String parenthesesContent = getParenthesesContent(s);
		if (parenthesesContent != null) {
			final String withoutParenthesesAndTheirContent = noParaMatcher
					.reset(s).replaceAll("").trim();

			if (Rule.countWords(parenthesesContent) > 2) {
				// Parentheses contain possible long form, now searching for
				// possible short form at the end of nonPara
				final String[] possibleShortForms = findPossibleShortForms(
						withoutParenthesesAndTheirContent);
				final ArrayList<String> possibleLongForms = new ArrayList<>();
				for (final String z : possibleShortForms)
					if (Rule.countWords(parenthesesContent) <= Math
							.min(z.length() + 5, z.length() * 2))
						possibleLongForms.add(RewriteShortFormLongForm
								.findBestLongForm(z, parenthesesContent));

				// exactly one short/long form found -> return
				if ((possibleLongForms.size() == 1)
						&& (possibleLongForms.get(0) != null)) {
					final String shortForm = withoutParenthesesAndTheirContent;
					final String longForm = possibleLongForms.get(0);
					if (longAndShortFormCompatible(longForm, shortForm)) {
						out = new ArrayList<>();
						out.add(new TermWithSource(shortForm, tws.getLanguage(),
								tws.getIsChem(), tws.getMdifiedByRulesList(),
								ruleName));
						out.add(new TermWithSource(longForm, tws.getLanguage(),
								tws.getIsChem(), tws.getMdifiedByRulesList(),
								ruleName));
						if (destructive)
							tws.supress();
						return out;
					}
				}
			} else if (meetShortFormConstrains(parenthesesContent)
					&& s.endsWith(parenthesesContent + ")") // TODO change if
					// other parentheses
					// supported
					&& (Rule.countWords(
							withoutParenthesesAndTheirContent) <= Math.min(
									parenthesesContent.length() + 5,
									parenthesesContent.length() * 2))) {
				/*
				 * tests if substring outside of parenthesis is short enough to
				 * be long from, ALTERNATIVE: if possible long form is too long,
				 * reduce length until it meets length constrain
				 */
				final String longForm = RewriteShortFormLongForm
						.findBestLongForm(parenthesesContent,
								withoutParenthesesAndTheirContent);
				if (longForm != null) {
					final String shortForm = parenthesesContent;
					if (longAndShortFormCompatible(longForm, shortForm)) {
						out = new ArrayList<>();
						out.add(new TermWithSource(shortForm, tws.getLanguage(),
								tws.getIsChem(), tws.getMdifiedByRulesList(),
								ruleName));
						out.add(new TermWithSource(longForm, tws.getLanguage(),
								tws.getIsChem(), tws.getMdifiedByRulesList(),
								ruleName));
						if (destructive)
							tws.supress();
						return out;
					}
				}
			}
		}
		return out;
	}

	/**
	 * takes string as input and returns a list of possible short form (meeting
	 * length constrain) at the end of the String
	 *
	 * @param s
	 * @return
	 */
	private String[] findPossibleShortForms(final String s) {
		final ArrayList<String> out = new ArrayList<>();
		// white spaces are searched for in string, starting at the end
		for (int z = s.length() - 1; z >= 0; z--) {
			if (s.charAt(z) == ' ') {
				final String sub = s.substring(z + 1);
				if (meetShortFormConstrains(sub))
					out.add(sub);
			}

			if ((z == 0) && meetShortFormConstrains(s))
				out.add(s);

		}
		final String[] outArray = out.toArray(new String[0]);
		return outArray;
	}

	// TODO: multiple parentheses
	String getParenthesesContent(final String s) {
		paraMatcherWithNames.reset(s);
		if (!paraMatcherWithNames.find())
			return null;
		return paraMatcherWithNames.group("inPar").trim();
	}

	/**
	 * Determines whether string meets length constrains for short forms.
	 *
	 * @param String
	 *            s
	 * @return boolean
	 */
	private boolean meetShortFormConstrains(final String s) {
		if ((Rule.countWords(s) <= 2) && (s.length() >= 2) && (s.length() <= 10)
				&& letterMatcher.reset(s).find()
				&& (Character.isDigit(s.charAt(0))
						|| Character.isLetter(s.charAt(0))))
			return true;
		return false;
	}
}
