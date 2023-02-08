/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
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
public class TestLongTerm {

	TestPair[] pairsDeleteLongTerm = {
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("Head and Neck Squamous Cell Carcinomia",
					new String[0]),
			new TestPair("here are exactely five words",
					"here are exactely five words") };

	@Test
	public void testDeleteLongTerm() throws IOException {
		final Rule r = new DeleteLongTerm();
		for (final TestPair z : pairsDeleteLongTerm) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));// System.out.println(r.apply());
		}
	}

	@Test
	public void testDeleteLongTermSpanish() throws IOException {
		final Rule r = new DeleteLongTerm();
		final TestPair z = new TestPair(
				"reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión (procedimiento)",
				new String[0]);
		final TermContainer termContainer = new TermContainer(z.input,
				Delemmatizer.LANGUAGE_SPANISH, false);
		assertEquals(z.expected, new HashSet<>(
				r.apply(termContainer).getUnsuppressedTermStrings()));// System.out.println(r.apply());
	}
}
