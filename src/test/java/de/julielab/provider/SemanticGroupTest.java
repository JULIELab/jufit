package de.julielab.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SemanticGroupTest {

	@Test
	public void testExistingTermId() {
		assertEquals(SemanticGroup.CHEM,
				SemanticGroup.getSemanticGroupForTermId("T116"));
	}

	@Test
	public void testNonexistantTermId() {
		boolean exception = false;
		try {
			SemanticGroup.getSemanticGroupForTermId("666");
		} catch (final IllegalArgumentException i) {
			exception = true;
		}
		assertTrue("Exception should be raised by illegal term id", exception);
	}

}
