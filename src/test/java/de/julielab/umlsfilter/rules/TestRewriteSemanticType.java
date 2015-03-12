/**
 * This is JUF, the Jena UMLS Filter
 * Copyright (C) 2015 Johannes HellrichJULIE LAB
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

import de.julielab.umlsfilter.config.ResourceProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

public class TestRewriteSemanticType {

	TestPair[] pairsSemantic = {
			new TestPair("bla [Chemical/Ingredient]",
					 "bla"),
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("Surgical intervention (finding)",
					"Surgical intervention"),
			new TestPair("Mitochondria (cell)",
					"Mitochondria"),
			new TestPair("methionine-R-sulfocide reductase (substance)",
					"methionine-R-sulfocide reductase"),
			new TestPair("bla (SMQ)", "bla"), 
			new TestPair("blo <male>",  "blo"),
			new TestPair("blu <SMQ>", "blu <SMQ>"),
			};
	
	TestPair[] pairsSemanticGerman = {
			new TestPair("bla [Dokumenttyp]",
					 "bla"),
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("methionine-R-sulfocide reductase (substance)",
					"methionine-R-sulfocide reductase (substance)"),
			new TestPair("bla (SMQ)", "bla"), 
			new TestPair("blu <SMQ>", "blu <SMQ>"),
			};

	@Test
	public void testRewriteSemanticType() throws IOException {
		final Rule r = new RewriteSemanticType(
				ResourceProvider.getRuleParameters(
						Delemmatizer.LANGUAGE_ENLGLISH, "RewriteSemanticType"));
		for (final TestPair z : pairsSemantic) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, z.isChemical);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}
	
	@Test
	public void testRewriteSemanticTypeGerman() throws IOException {
		final Rule r = new RewriteSemanticType(
				ResourceProvider.getRuleParameters(
						Delemmatizer.LANGUAGE_GERMAN, "RewriteSemanticType"));
		for (final TestPair z : pairsSemanticGerman) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_GERMAN, z.isChemical);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}
}
