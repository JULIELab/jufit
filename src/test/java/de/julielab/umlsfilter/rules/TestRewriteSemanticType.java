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

import de.julielab.umlsfilter.config.ResourceProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

@SuppressWarnings("deprecation")
public class TestRewriteSemanticType {

	TestPair[] pairsSemantic = {
			new TestPair("bla [Chemical/Ingredient]", "bla"),
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("Surgical intervention (finding)",
					"Surgical intervention"),
			new TestPair("Mitochondria (cell)", "Mitochondria"),
			new TestPair("methionine-R-sulfocide reductase (substance)",
					"methionine-R-sulfocide reductase"),
			new TestPair("bla (SMQ)", "bla"), new TestPair("blo <male>", "blo"),
			new TestPair("blu <SMQ>", "blu <SMQ>"), };

	TestPair[] pairsSemanticGerman = { new TestPair("bla [Dokumenttyp]", "bla"),
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("methionine-R-sulfocide reductase (substance)",
					"methionine-R-sulfocide reductase (substance)"),
			new TestPair("bla (SMQ)", "bla"),
			new TestPair("blu <SMQ>", "blu <SMQ>"), };

	TestPair[] pairsSemanticSpanish = { new TestPair(
			"reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión (procedimiento)",
			"reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión") };

	@Test
	public void testRewriteSemanticType() throws IOException {
		final Rule r = new RewriteSemanticType(
				ResourceProvider.getRuleParameters(
						Delemmatizer.LANGUAGE_ENLGLISH, "RewriteSemanticType"));
		for (final TestPair z : pairsSemantic) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, z.isChemical);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testRewriteSemanticTypeGerman() throws IOException {
		final Rule r = new RewriteSemanticType(
				ResourceProvider.getRuleParameters(Delemmatizer.LANGUAGE_GERMAN,
						"RewriteSemanticType"));
		for (final TestPair z : pairsSemanticGerman) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_GERMAN, z.isChemical);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testRewriteSemanticTypeSpanish() throws IOException {
		final Rule r = new RewriteSemanticType(
				ResourceProvider.getRuleParameters(
						Delemmatizer.LANGUAGE_SPANISH, "RewriteSemanticType"));
		for (final TestPair z : pairsSemanticSpanish) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_GERMAN, z.isChemical);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}
}
