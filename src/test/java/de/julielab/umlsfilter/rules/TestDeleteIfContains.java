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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import de.julielab.umlsfilter.config.ResourceProvider;
import de.julielab.umlsfilter.delemmatizer.Delemmatizer;
import de.julielab.umlsfilter.delemmatizer.TestPair;

@SuppressWarnings("deprecation")
public class TestDeleteIfContains {
	TestPair[] DeleteTermWithClassification = {
			new TestPair("everything is fine", "everything is fine"),
			new TestPair("Anyterm, NEC"), new TestPair("Antyterm,NEC"),
			new TestPair("Anyterm (NEC)"), new TestPair("Anyterm [NEC]"),
			new TestPair("(NEC) Anyterm", "(NEC) Anyterm") };

	TestPair[] Underspecification = {
			new TestPair("everything is fine",
					new String[] { "everything is fine" }),
					new TestPair("Other and unspecified leukaemia", new String[0]),
					new TestPair("leukaemia, NOS", new String[0]),
					new TestPair("Antyterm,NOS", new String[0]),
					new TestPair("Anyterm (NOS)", new String[0]),
					new TestPair("Anyterm [NOS]", new String[0]),
					new TestPair("(NOS) Anyterm", "(NOS) Anyterm"), };

	TestPair[] AtSign = {
			new TestPair("everything is fine",
					new String[] { "everything is fine" }),
					new TestPair("ADHESIVE @@ Bandage", new String[0]),
					new TestPair("No At-Sign contained",
							new String[] { "No At-Sign contained" })

	};

	TestPair[] ECNumber = {
			new TestPair("everything is fine",
					new String[] { "everything is fine" }),
					new TestPair("EC 2.7.1.112", new String[0]),
					new TestPair("EC 3.4.11.4 or my gun", new String[0]),
					new TestPair("any text EC 3.4.11.4", new String[0]),
					new TestPair("EC 34", "EC 34"),
					new TestPair("No EC-number contained",
							new String[] { "No EC-number contained" }),
							new TestPair("EC 1.6.3", new String[0]),

	};

	@Test
	public void tesDeleteTermWithAtSign() throws IOException {
		final Rule r = new DeleteIfContainsAtSign();
		for (final TestPair z : AtSign) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void tesDeleteTermWithECNumber() throws IOException {
		final Rule r = new DeleteIfContainsECNumber();
		for (final TestPair z : ECNumber) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void tesDeleteTermWithMisc() throws IOException {
		final TestPair[] Misc = {
				new TestPair("everything is fine",
						new String[] { "everything is fine" }),
						new TestPair("Other cancer", new String[0]),
						new TestPair("unknown cancer", new String[0]),
						new TestPair("Unknown cancer", new String[0]),
						new TestPair("Unknowncancer", "Unknowncancer"),
						new TestPair("no cancer", new String[0]),
						new TestPair("miscellaneous cancer", new String[0]),
						new TestPair(
								"Refractory anaemia with excess of blasts -RETIRED- ",
								new String[0]), };
		final Rule r = new DeleteIfContainsResidual(ResourceProvider
				.getRuleParameters(Delemmatizer.LANGUAGE_ENLGLISH,
						"DeleteIfContainsResidual")
						.get(Rule.PARAMETER_PATTERNS));
		for (final TestPair z : Misc) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void tesDeleteTermWithUnderspecification() throws IOException {
		final Rule r = new DeleteIfContainsResidual(ResourceProvider
				.getRuleParameters(Delemmatizer.LANGUAGE_ENLGLISH,
						"DeleteIfContainsResidual")
						.get(Rule.PARAMETER_PATTERNS));
		for (final TestPair z : Underspecification) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testDeleteDosage() throws IOException {
		final TestPair[] Dosage = {
				new TestPair("Oxygen 25", "Oxygen 25"),
				new TestPair("25gum", "25gum"),
				new TestPair("Oxygen 2%", new String[0]),
				new TestPair("Vitamine 25g", new String[0]),
				new TestPair("dust 10µg", new String[0]),
				new TestPair("milk 523ml", new String[0]),
				new TestPair("0.523l/nmol", new String[0]),
				new TestPair("100 IU/ ml", new String[0]),
				new TestPair("U", new String[0]),
				new TestPair("100 IU", new String[0]),
				new TestPair("10 mg/ 10 ml", new String[0]),
				new TestPair("g/kg", new String[0]),
				new TestPair("G/KG", new String[0]),
				new TestPair("5mm", new String[0]),
				new TestPair("2 MG/ML", new String[0]),
				new TestPair("Milligram/kilogram", new String[0]),
				new TestPair("25-hydroxy D2 im Blut", "25-hydroxy D2 im Blut"),
				new TestPair("50S-Archaea-Ribosomen-Untereinheit",
						"50S-Archaea-Ribosomen-Untereinheit"),
						new TestPair("milk 0.523l", new String[0]),
						new TestPair("day", new String[0]),
						new TestPair(" 5 dias ", new String[0]),
						new TestPair("/day", new String[0]),
						new TestPair("Haemophilia C", "Haemophilia C"),
						new TestPair("mg/ 24 h", new String[0]),
						new TestPair("24 h", new String[0]),
						new TestPair("U/ml", new String[0]),
						new TestPair("kilogram", new String[0]),
						new TestPair("copies/ ml", new String[0]),
						new TestPair("kg bodyweight", new String[0]),
						new TestPair("24 h", new String[0]),

		};
		final Rule r = new DeleteIfContainsDosage("day", "dias");
		for (final TestPair z : Dosage) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}

	@Test
	public void testDeleteFrenchTermWithClassification() throws IOException {
		final Rule r = new DeleteIfContainsResidual(ResourceProvider
				.getRuleParameters(Delemmatizer.LANGUAGE_FRENCH,
						"DeleteIfContainsResidual")
						.get(Rule.PARAMETER_PATTERNS));
		final TermContainer termContainer = new TermContainer(
				"Complications cardiaques, non classées ailleurs",
				Delemmatizer.LANGUAGE_FRENCH, false);
		assertTrue(r.apply(termContainer).getUnsuppressedTermStrings()
				.isEmpty());

	}

	@Test
	public void testDeleteTermWithClassification() throws IOException {
		final Rule r = new DeleteIfContainsResidual(ResourceProvider
				.getRuleParameters(Delemmatizer.LANGUAGE_ENLGLISH,
						"DeleteIfContainsResidual")
						.get(Rule.PARAMETER_PATTERNS));
		for (final TestPair z : DeleteTermWithClassification) {
			final TermContainer termContainer = new TermContainer(z.input,
					Delemmatizer.LANGUAGE_ENLGLISH, false);
			assertEquals(z.expected, new HashSet<String>(r.apply(termContainer)
					.getUnsuppressedTermStrings()));
		}
	}
}
