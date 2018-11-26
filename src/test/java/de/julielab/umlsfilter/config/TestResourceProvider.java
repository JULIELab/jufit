/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.config;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Test;

public class TestResourceProvider {

	@Test
	public void testGetlanguageIndependentRegex() throws IOException {
		// System.out.print(ResourceProvider.getLanguageIndependendRegex(Delemmatizer.RULE_DELETEPARENTHETICALS));
		assertArrayEquals(
				new String[] { "^\\(.[^\\)]*?\\)", "^\\[.[^\\]]*?\\]",
						"\\(.[^\\)]*?\\)$", "\\[.[^\\]]*?\\]$" },
				ResourceProvider.getLanguageIndependentParentheticals());
	}

	@Test
	public void testNewFormat() throws IOException {
		final LanguageDependentResources ldr = ResourceProvider
				.readResourcesFile("test_resources.json", "src/test/resources/",
						LanguageDependentResources.class);
		assertArrayEquals(new String[] { "RewriteShortFormLongForm",
				"DeleteIfContainsDosage", "DeleteIfContainsAtSign",
				"DeleteIfContainsECNumber", "DeleteIfContainsClassification",
				"DeleteIfContainsUnderspecification", "DeleteIfContainsMisc",
				"DeleteShortToken", "RewriteParentheticals",
				"RewriteSemanticType", "RewriteSyntacticInversion",
				"RewriteApostropheS", "DeleteLongTerm" }, ldr.rules);
		assertArrayEquals(
				new String[] { ",\\s*NEC$", " \\(NEC\\)$", " \\[NEC\\]$",
						"(?i)not elsewhere classified", "(?i)unclassified",
						"(i?)without mention" },
				ldr.ruleParameters.get("DeleteIfContainsClassification")
						.get("pattern"));
	}
}
