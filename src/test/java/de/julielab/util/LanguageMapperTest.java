package de.julielab.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LanguageMapperTest {

	@Test
	public void testConvertLanguageUmls2obo() {
		assertEquals("DE", LanguageMapper.convertLanguageUmls2Obo("GER"));
	}

	@Test
	public void testConvertLanguageObo2Umls() {
		assertEquals("GER", LanguageMapper.convertLanguageObo2Umls("DE"));
	}

}
