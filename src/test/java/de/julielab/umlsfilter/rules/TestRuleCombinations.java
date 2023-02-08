/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;

@SuppressWarnings("deprecation")
public class TestRuleCombinations {

	@Test
	public void testParenthesesAndInversion() throws IOException {
		final TermContainer termContainer = new TermContainer(
				"Verzerrung, Häufungs- (Epidemiologie)",
				Delemmatizer.LANGUAGE_GERMAN, false);
		for (final Rule r : new Rule[] { new RewriteParentheticals(),
				new RewriteSyntacticInversion(true, false) })
			r.apply(termContainer);
		assertEquals(
				new HashSet<>(Arrays.asList(
						new String[] { "Verzerrung, Häufungs- (Epidemiologie)",
								"Verzerrung, Häufungs-",
								"Häufungs-(Epidemiologie) Verzerrung",
								"Häufungsverzerrung" })),
				new HashSet<>(
						termContainer.getUnsuppressedTermStrings()));
	}
}
