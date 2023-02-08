/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.delemmatizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class for testing purpose. Has attributes according to input and expected
 * output of method delemmatize.
 *
 *
 */
public class Tester {
	private static final Delemmatizer DELEMMATIZER = new Delemmatizer();
	String input;
	String language;
	boolean isChemicalOrDrug;
	HashSet<String> expected;
	HashSet<String> output;

	/**
	 * Computes result of delemmatization from given arguments for testing
	 * purpose.
	 *
	 * @param input
	 * @param language
	 * @param isChemicalOrDrug
	 * @param expected
	 */
	@SuppressWarnings("deprecation")
	public Tester(final String input, final String language,
			final boolean isChemicalOrDrug, 
			final String... expected) throws IOException {
		this.input = input;
		this.language = language;
		this.isChemicalOrDrug = isChemicalOrDrug;
		this.expected = new HashSet<>(Arrays.asList(expected));
		output = new HashSet<>(
				DELEMMATIZER.delemmatizeTermProducingUnsuppressedStrings(
						this.input, this.language, this.isChemicalOrDrug));
	}

}
