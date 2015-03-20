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

package de.julielab.umlsfilter.config;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Test;

public class TestResourceProvider {

	@Test
	public void testGetlanguageIndependentRegex() throws IOException {
		// System.out.print(ResourceProvider.getLanguageIndependendRegex(Delemmatizer.RULE_DELETEPARENTHETICALS));
		assertArrayEquals(new String[] { "^\\(.[^\\)]*?\\)",
				"^\\[.[^\\]]*?\\]", "\\(.[^\\)]*?\\)$", "\\[.[^\\]]*?\\]$" },
				ResourceProvider.getLanguageIndependentParentheticals());
	}

	@Test
	public void testNewFormat() throws IOException {
		final LanguageDependentResources ldr = ResourceProvider
				.readResourcesFile("test_resources.json",
						"src/test/resources/", LanguageDependentResources.class);
		assertArrayEquals(new String[] { "RewriteShortFormLongForm",
				"DeleteIfContainsDosage", "DeleteIfContainsAtSign",
				"DeleteIfContainsECNumber", "DeleteIfContainsClassification",
				"DeleteIfContainsUnderspecification", "DeleteIfContainsMisc",
				"DeleteShortToken", "RewriteParentheticals",
				"RewriteSemanticType", "RewriteSyntacticInversion",
				"RewriteApostropheS", "DeleteLongTerm" }, ldr.rules);
		assertArrayEquals(new String[] { ",\\s*NEC$", " \\(NEC\\)$",
				" \\[NEC\\]$", "(?i)not elsewhere classified",
				"(?i)unclassified", "(i?)without mention" }, ldr.ruleParameters
				.get("DeleteIfContainsClassification").get("pattern"));
	}
}
