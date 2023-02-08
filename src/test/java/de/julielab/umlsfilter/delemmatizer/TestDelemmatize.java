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

public class TestDelemmatize {

	Tester[] testers = null;

	private void fillTesters() throws IOException {
		if (testers == null)
			testers = new Tester[] {
					new Tester(
							"reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión (procedimiento)",
							Delemmatizer.LANGUAGE_SPANISH, false,
							"reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión"),
					new Tester("Anyterm (NOS)", Delemmatizer.LANGUAGE_ENLGLISH,
							false),
					new Tester("(protein) methionine-R-sulfocide reductase",
							Delemmatizer.LANGUAGE_ENLGLISH, true,
							"(protein) methionine-R-sulfocide reductase"),
					new Tester("Unclassified sequences",
							Delemmatizer.LANGUAGE_ENLGLISH, false),
					new Tester("Heat shock transcription factor (HSF)",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Heat shock transcription factor (HSF)", "HSF",
							"Heat shock transcription factor"),
					new Tester("Failure, Renal", Delemmatizer.LANGUAGE_ENLGLISH,
							false, "Failure, Renal", "Renal Failure"),
					new Tester("Alzheimer's desease",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Alzheimer's desease", "Alzheimer desease"),
					new Tester("everything is fine",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"everything is fine"),
					new Tester("Chondria <beetle>",
							Delemmatizer.LANGUAGE_ENLGLISH, false, "Chondria"),
					new Tester("Surgical intervention (finding)",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Surgical intervention"),
					new Tester("Other and unspecified leukaemia",
							Delemmatizer.LANGUAGE_ENLGLISH, false),
					new Tester("Other cancer", Delemmatizer.LANGUAGE_ENLGLISH,
							false),
					new Tester("1-Carboxyglutamic Acid [Chemical/Ingredient]",
							Delemmatizer.LANGUAGE_ENLGLISH, true,
							"1-Carboxyglutamic Acid"),
					new Tester("ADHESIVE @@ Bandage",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							new String[0]),
					new Tester("Other and unspecified leukaemia",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							new String[0]),
					new Tester("here are exactely five words",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"here are exactely five words"),
					new Tester("Mito (cell) chondria",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Mito (cell) chondria"),
					new Tester("10*9/L", Delemmatizer.LANGUAGE_ENLGLISH, false,
							new String[0]),
					new Tester("and", Delemmatizer.LANGUAGE_ENLGLISH, false,
							new String[0]),
					new Tester("und", Delemmatizer.LANGUAGE_GERMAN, false,
							new String[0]),
					new Tester("y", Delemmatizer.LANGUAGE_FRENCH, false,
							new String[0]),
					new Tester("zijn", Delemmatizer.LANGUAGE_DUTCH, false,
							new String[0]),
					new Tester("Anyterm [NOS]", Delemmatizer.LANGUAGE_ENLGLISH,
							false, new String[0]),
					new Tester("Other and unspecified leukaemia",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							new String[0]),
					new Tester(
							"Selective Sorotonin Reuptake Inhabitors (SSRIs)",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Selective Sorotonin Reuptake Inhabitors (SSRIs)",
							"Selective Sorotonin Reuptake Inhabitors", "SSRIs"),
					new Tester("Enzyme, Branching",
							Delemmatizer.LANGUAGE_ENLGLISH, false,
							"Enzyme, Branching", "Branching Enzyme"),
					new Tester("5'saccharose", Delemmatizer.LANGUAGE_ENLGLISH,
							false, "5'saccharose"),
					new Tester("EC 2.7.1.112", Delemmatizer.LANGUAGE_ENLGLISH,
							false, new String[0]),
					new Tester(
							"Akute respiratorische Insuffizienz, anderenorts nicht klassifiziert",
							Delemmatizer.LANGUAGE_GERMAN, false, new String[0]),
					new Tester("carne sin clasificar",
							Delemmatizer.LANGUAGE_SPANISH, false,
							new String[0]),
					new Tester("carne sin cla", Delemmatizer.LANGUAGE_SPANISH,
							false, "carne sin cla"),
			};
	}

	@Test
	public void testDelemmatize() throws IOException {
		fillTesters();
		for (final Tester tester : testers)
			assertEquals(tester.expected, tester.output);
	}

}
