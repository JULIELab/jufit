/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.delemmatizer;

import java.util.Arrays;
import java.util.HashSet;

public class TestPair {
	public final String input;

	public final HashSet<String> expected = new HashSet<>();
	public final boolean isChemical;

	/**
	 * Used to ease testing by providing container for input and expected output
	 *
	 * @param isChemical
	 *            flag to indicate if term is chemical
	 * @param in
	 *            Term to process
	 * @param out
	 *            Expected result(s)
	 */
	public TestPair(final boolean isChemical, final String in,
			final String... out) {
		input = in;
		expected.addAll(Arrays.asList(out));
		this.isChemical = isChemical;
	}

	/**
	 * Used to ease testing by providing container for input and expected output
	 *
	 * @param in
	 *            Term to process
	 * @param out
	 *            Expected result(s)
	 */
	public TestPair(final String in, final String... out) {
		input = in;
		expected.addAll(Arrays.asList(out));
		isChemical = false;
	}

	@Override
	public String toString() {
		return "TestPair [input=" + input + ", output=" + expected + "]";
	}
}
