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


/* Für das Testen der Rewrite-Rules werden Paare aus String-Eingaben und String-Array-Ausgaben benötigt.
 * Um die Handhabbarkeit zu gewährleisten werden diese Paare als Objekte mit den Attributen eingabe und ausgabe
 * umgesetzt.
 */

package de.julielab.umlsfilter.delemmatizer;

import java.util.Arrays;
import java.util.HashSet;

public class TestPair {
	@Override
	public String toString() {
		return "TestPair [input=" + input + ", output=" + expected + "]";
	}

	public final String input;
	public final HashSet<String> expected = new HashSet<>();
	public final boolean isChemical;

	public TestPair(final String in, final String... out) {
		input = in;
		expected.addAll(Arrays.asList(out));
		isChemical = false;
	}

	public TestPair(final boolean isChemical, final String in,
			final String... out) {
		input = in;
		expected.addAll(Arrays.asList(out));
		this.isChemical = isChemical;
	}
}
