/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.config.ResourceProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

@SuppressWarnings("deprecation")
public class TestDeleteShortToken {

	TestPair[] pairsDeleteShortToken = {
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("10*9/L", new String[0]),
			new TestPair("IV", new String[0]),
			new TestPair("IV injection", "IV injection"),
			new TestPair("123a5342", new String[0]),
			new TestPair("and", new String[0]),
			new TestPair("and cancer", "and cancer"),
			new TestPair("Chinchilla<genus>", "Chinchilla<genus>"),
			new TestPair("II", new String[0]),
			new TestPair("h 3", new String[0]),
			new TestPair("I.Q.; 20-34", new String[0]), };

	@Test
	public void testDeleteShortToken() throws IOException {
		final Rule r = new DeleteShortToken(
				ResourceProvider
						.getRuleParameters(Delemmatizer.LANGUAGE_ENLGLISH,
								"DeleteShortToken")
						.get(Rule.PARAMETER_STOPWORDS));
		for (final TestPair z : pairsDeleteShortToken) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testDeleteShortTokenGerman() throws IOException {
		final Rule r = new DeleteShortToken(
				ResourceProvider
						.getRuleParameters(Delemmatizer.LANGUAGE_GERMAN,
								"DeleteShortToken")
						.get(Rule.PARAMETER_STOPWORDS));
		final TermContainer termContainer = new TermContainer("Ödem",
				Delemmatizer.LANGUAGE_GERMAN, false);
		assertFalse(
				r.apply(termContainer).getUnsuppressedTermStrings().isEmpty());

	}

	@Test
	public void testRomanNumeral() throws IOException {
		final DeleteShortToken rule = new DeleteShortToken(new String[0]);
		assertFalse(rule.romanNumeral.reset("").find());
		assertFalse(rule.romanNumeral.reset("ewjfbl").find());
		assertTrue(rule.romanNumeral.reset("I").find());
		assertTrue(rule.romanNumeral.reset("VI").find());
	}

}
// C0041119