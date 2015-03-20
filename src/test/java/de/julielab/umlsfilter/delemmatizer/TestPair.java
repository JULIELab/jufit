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
