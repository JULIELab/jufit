/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

@SuppressWarnings("deprecation")
public class TestRewriteApostropheS {

	TestPair[] pairsDeletePossesivS = {
			new TestPair("Alzheimer's Desease", "Alzheimer's Desease",
					"Alzheimer Desease"),
			new TestPair("5'saccharose", "5'saccharose"), // fiktiv,
			// muss
			// gleich
			// bleiben
			new TestPair("Desease, Alzheimer's", "Desease, Alzheimer's",
					"Desease, Alzheimer"), // fiktiv
			new TestPair("everything is fine", "everything is fine") };

	@Test
	public void testAddPossessivS() throws IOException {
		final Rule r = new RewriteApostropheS();
		for (final TestPair z : pairsDeletePossesivS) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}

}
