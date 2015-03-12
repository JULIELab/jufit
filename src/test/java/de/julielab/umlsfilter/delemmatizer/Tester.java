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
	String input;
	String language;
	boolean isChemicalOrDrug;
	HashSet<String> expected;
	HashSet<String> output;
	private static final Delemmatizer DELEMMATIZER = new Delemmatizer();

	/**
	 * Computes result of delemmatization from given arguments for testing
	 * purpose.
	 *
	 * @param input
	 * @param language
	 * @param isChemicalOrDrug
	 * @param expected
	 */
	public Tester(final String input, final String language,
			final boolean isChemicalOrDrug, final boolean isJulieVersion,
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
