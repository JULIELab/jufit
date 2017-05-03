package de.julielab.provider;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

public class UMLSTermProviderTest {

	@Test
	public void testGetDrugOrChemicalCUIs() {
		final Set<String> expectedCuis = ImmutableSet.of("C0000005");
		assertEquals(expectedCuis,
				UMLSTermProvider
						.getDrugOrChemicalCUIs("src/test/resources/MRSTY.test"));
	}

	@Test
	public void testCorrectNumberOfUMLSTerms() throws IOException {
		final List<ProvidedTerm> terms = Lists.newArrayList(UMLSTermProvider
				.provideUMLSTerms("src/test/resources/MRCONSO.test",
						"src/test/resources/MRSTY.test"));
		assertEquals(8, terms.size());
	}

	@Test
	public void testSuppressing() throws IOException {
		final List<ProvidedTerm> terms = Lists.newArrayList(UMLSTermProvider
				.provideUMLSTerms("src/test/resources/MRCONSO.test",
						"src/test/resources/MRSTY.test", true));
		assertEquals(7, terms.size());
	}

	@Test
	public void testProvideUMLSTermForLanguage() throws IOException {
		final ProvidedTerm expected = new ProvidedTerm(
				"C0000039",
				"1,2-dipalmitoylfosfatidylcholin",
				"CZE",
				false,
				"C0000039|CZE|P|L6742182|PF|S7862052|Y|A13042554||M0023172|D015060|MSHCZE|MH|D015060|1,2-dipalmitoylfosfatidylcholin|3|N||");
		final List<ProvidedTerm> terms = Lists.newArrayList(UMLSTermProvider
				.provideUMLSTerms("src/test/resources/MRCONSO.test",
						"src/test/resources/MRSTY.test", "CZE"));
		assertEquals(1, terms.size());
		assertEquals(expected, terms.get(0));
	}

	@Test
	public void testProvideUMLSTermsNoDrugOrChemical() throws IOException {
		final ProvidedTerm expected = new ProvidedTerm(
				"C0000039",
				"1,2-dipalmitoylfosfatidylcholin",
				"CZE",
				false,
				"C0000039|CZE|P|L6742182|PF|S7862052|Y|A13042554||M0023172|D015060|MSHCZE|MH|D015060|1,2-dipalmitoylfosfatidylcholin|3|N||");
		final List<ProvidedTerm> terms = Lists.newArrayList(UMLSTermProvider
				.provideUMLSTerms("src/test/resources/MRCONSO.test",
						"src/test/resources/MRSTY.test"));
		assertEquals(expected, terms.get(5));
	}

	@Test
	public void testProvideUMLSTermsIsDrugOrChemical() throws IOException {
		final ProvidedTerm expected = new ProvidedTerm(
				"C0000005",
				"(131)I-Macroaggregated Albumin",
				"ENG",
				true,
				"C0000005|ENG|P|L0000005|PF|S0007492|Y|A7755565||M0019694|D012711|MSH|PEN|D012711|(131)I-Macroaggregated Albumin|0|N||");
		final List<ProvidedTerm> terms = Lists.newArrayList(UMLSTermProvider
				.provideUMLSTerms("src/test/resources/MRCONSO.test",
						"src/test/resources/MRSTY.test"));
		assertEquals(expected, terms.get(0));
	}
}
