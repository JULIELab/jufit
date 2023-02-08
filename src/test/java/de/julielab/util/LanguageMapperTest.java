/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

package de.julielab.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LanguageMapperTest {

	@Test
	public void testConvertLanguageObo2Umls() {
		assertEquals("GER", LanguageMapper.convertLanguageObo2Umls("DE"));
	}

	@Test
	public void testConvertLanguageUmls2obo() {
		assertEquals("DE", LanguageMapper.convertLanguageUmls2Obo("GER"));
	}

}
