/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SemanticGroupTest {

//	@Test
//	public void testExistingTermId() {
//		assertEquals(SemanticGroup.CHEM,
//				SemanticGroup.getSemanticGroupForTermId("T116"));
//		assertEquals(SemanticGroup.CHEM,
//				SemanticGroup.getSemanticGroupForTermId("T121"));
//		assertEquals(SemanticGroup.PHYS,
//				SemanticGroup.getSemanticGroupForTermId("T042"));
//	}

	@Test
	public void testNonexistantTermId() {
		boolean exception = false;
		try {
			SemanticType.getSemanticGroupForTermId("666");
		} catch (final IllegalArgumentException i) {
			exception = true;
		}
		assertTrue("Exception should be raised by illegal term id", exception);
	}

}
