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
public class TestRewriteParentheses {

	TestPair[] pairsDeleteParentheticals = {
			new TestPair("a (test) for greediness (test)",
					"a (test) for greediness (test)",
					"a (test) for greediness"),
			new TestPair("flagellar filament (sensu Bacteria)",
					"flagellar filament (sensu Bacteria)",
					"flagellar filament"),
			new TestPair("18-Hydroxycorticosterone (substance)",
					"18-Hydroxycorticosterone (substance)",
					"18-Hydroxycorticosterone"),
			new TestPair("Gluten-free food [generic 1]",
					"Gluten-free food [generic 1]", "Gluten-free food"),
			new TestPair("1-Carboxyglutamic Acid [Chemical/Ingredient]",
					"1-Carboxyglutamic Acid [Chemical/Ingredient]",
					"1-Carboxyglutamic Acid"),
			new TestPair("(protein) methionine-R-sulfocide reductase",
					"(protein) methionine-R-sulfocide reductase",
					"methionine-R-sulfocide reductase"),
			new TestPair("[V] Alcohol use", "[V] Alcohol use", "Alcohol use"),
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("[V] Alcohol use [V]", "[V] Alcohol use [V]",
					"Alcohol use"), };

	TestPair[] paareDeleteAngularBrackets = {
			new TestPair("Chondria <beetle>", "Chondria <beetle>", "Chondria"),
			new TestPair("<bla> Rose <Flower>", "<bla> Rose <Flower>", "Rose"),
			new TestPair("<beetle> Chondria", "<beetle> Chondria", "Chondria"),
			new TestPair("Chondria <beetle> Beetle", "Chondria <beetle> Beetle",
					"Chondria Beetle") };

	@Test
	public void testDeleteParentheticals() throws IOException {
		final Rule r = new RewriteParentheticals();
		for (final TestPair z : pairsDeleteParentheticals) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<>(
					r.apply(termContainer).getUnsuppressedTermStrings()));
		}
	}
}
