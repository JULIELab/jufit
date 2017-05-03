package de.julielab.provider;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProvidedTermTest {

	@Test
	public void test() {
		final ProvidedTerm wt = new ProvidedTerm(
				"C0000039",
				"foo",
				"any",
				false,
				"C0000039|CZE|P|L6742182|PF|S7862052|Y|A13042554||M0023172|D015060|MSHCZE|MH|D015060|foo|3|N||");
		assertEquals(
				"C0000039|CZE|P|L6742182|PF|S7862052+SEM|Y|A13042554||M0023172|D015060|MSHCZE|MH|D015060|bar|3|N||",
				wt.getUpdatedMRCONSO("bar", "SEM"));
	}

}
