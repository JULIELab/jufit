/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.rules;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import de.julielab.umlsfilter.delemmatizer.Delemmatizer;

public class TestTermContainer {

	@Test
	public void testModifiedByRule() {
		final TermContainer termContainer = new TermContainer("foo",
				Delemmatizer.LANGUAGE_ENLGLISH, false);
		termContainer
				.addTerms(
						Arrays.asList(new TermWithSource[] {
								new TermWithSource("bar", "ANG", true,
										Lists.newArrayList("DELTE_ME",
												"I_AM_BAD"),
										"BAR"),
								new TermWithSource("bar", "ANG", true,
										new ArrayList<String>(), "BAR"),
								new TermWithSource("bar", "ANG", true,
										Lists.newArrayList("NOT_ME"),
										"BAR") }));
		assertEquals(
				ImmutableSet.of(
						new TermWithSource("foo",
								Delemmatizer.LANGUAGE_ENLGLISH, false),
						new TermWithSource("bar", "ANG", true,
								new ArrayList<String>(), "BAR")),
				new HashSet<>(termContainer.getRawTerms()));
	}

	@Test
	public void testTermContainer() {
		TermContainer termContainer = new TermContainer("",
				Delemmatizer.LANGUAGE_ENLGLISH, false);
		assertEquals("", termContainer.getTermsAsString());
		assertEquals(Delemmatizer.LANGUAGE_ENLGLISH,
				termContainer.getLanguage());
		assertEquals(false, termContainer.getIsChem());

		termContainer = new TermContainer("bla", Delemmatizer.LANGUAGE_GERMAN,
				true);
		assertEquals("bla", termContainer.getTermsAsString());
		assertEquals(Delemmatizer.LANGUAGE_GERMAN, termContainer.getLanguage());
		assertEquals(true, termContainer.getIsChem());
	}
}
