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
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

@SuppressWarnings("deprecation")
public class TestRewriteSyntacticInversion {

	@Test
	public void testAddSyntacticInversion() throws IOException {
		final TestPair[] pairsSyntacticInversionOhneListe = {
				new TestPair("everything is fine", "everything is fine"),
				new TestPair("failure, renal", "failure, renal",
						"renal failure"),
						new TestPair("Enzyme, Branching", "Enzyme, Branching",
								"Branching Enzyme") };
		final Rule r = new RewriteSyntacticInversion(false, false);
		for (final TestPair z : pairsSyntacticInversionOhneListe) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testAddSyntacticInversionImproved() throws IOException {
		final TestPair[] pairsSyntacticInversionOhneListe = {
				new TestPair(true, "Furanon, tetrahydro-2-",
						"Furanon, tetrahydro-2-", "tetrahydro-2-Furanon"),
						new TestPair(false, "Azidose, Laktat-", "Azidose, Laktat-",
								"Laktatazidose"),
								new TestPair(false, "Reaktion, Akute-Phase-",
										"Reaktion, Akute-Phase-", "Akute-Phase-Reaktion"),
										new TestPair(true, "Rezeptoren, purinerge P1-",
												"Rezeptoren, purinerge P1-", "purinerge P1-Rezeptoren"),
												new TestPair(true, "ATPase, F1-", "ATPase, F1-", "F1-ATPase"),
												new TestPair(true, "Säuren, Aldehyd-", "Säuren, Aldehyd-",
														"Aldehydsäuren"),
														new TestPair(true, "ATPase, Magnesium-", "ATPase, Magnesium-",
																"Magnesium-ATPase"),
																new TestPair(false, "Blockade, vegetative Nerven-",
																		"Blockade, vegetative Nerven-",
																		"vegetative Nervenblockade"),
																		new TestPair(false, "Gelenk, atlanto-okzipital-",
																				"Gelenk, atlanto-okzipital-",
																				"Atlanto-Okzipital-Gelenk"),
																				new TestPair(false, "Mitochondriale ADP-, ATP- Translocasen",
																						"Mitochondriale ADP-, ATP- Translocasen"), };
		final Rule r = new RewriteSyntacticInversion(true, false);
		for (final TestPair z : pairsSyntacticInversionOhneListe) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_GERMAN, z.isChemical);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testDestructive() throws IOException {
		final TestPair[] pairsSyntacticInversionOhneListe = {
				new TestPair(true, "Furanon, tetrahydro-2-",
						"tetrahydro-2-Furanon"),
						new TestPair(false, "Azidose, Laktat-", "Laktatazidose"),
						new TestPair(false, "Reaktion, Akute-Phase-",
								"Akute-Phase-Reaktion"),
								new TestPair(true, "Rezeptoren, purinerge P1-",
										"purinerge P1-Rezeptoren"),
										new TestPair(true, "ATPase, F1-", "F1-ATPase"),
										new TestPair(true, "Säuren, Aldehyd-", "Aldehydsäuren"),
										new TestPair(true, "ATPase, Magnesium-", "Magnesium-ATPase"),
										new TestPair(false, "Blockade, vegetative Nerven-",
												"vegetative Nervenblockade"),
												new TestPair(false, "Gelenk, atlanto-okzipital-",
														"Atlanto-Okzipital-Gelenk"),
														new TestPair(false, "Mitochondriale ADP-, ATP- Translocasen",
																"Mitochondriale ADP-, ATP- Translocasen"), };
		final Rule r = new RewriteSyntacticInversion(true, true);
		for (final TestPair z : pairsSyntacticInversionOhneListe) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_GERMAN, z.isChemical);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testDualCommaFail() throws IOException {
		final TermContainer termContainer = new TermContainer("is, a, bit",
				Delemmatizer.LANGUAGE_GERMAN, false);
		assertEquals(Arrays.asList("is, a, bit"),
				new RewriteSyntacticInversion(true, false).apply(termContainer)
				.getUnsuppressedTermStrings());
	}
}