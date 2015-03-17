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
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

}
