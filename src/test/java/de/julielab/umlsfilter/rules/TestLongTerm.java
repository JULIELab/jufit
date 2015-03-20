/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015 JULIE LAB Authors:
 * Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));// System.out.println(r.apply());
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
		assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
				.getUnsuppressedTermStrings()));// System.out.println(r.apply());
	}
}
