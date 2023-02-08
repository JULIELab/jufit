/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.delemmatizer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class TestDelematizer {

	@Test
	public void test() {
		assertEquals("hans", Delemmatizer.regularizeTerm("Hans!"));
	}

	@Test
	public void testPrepareRules() throws IOException {
		final Delemmatizer d = new Delemmatizer();
		for (final String language : "DUT ENG FRE GER SPA".split(" "))
			d.prepareRules(language);
	}
}
