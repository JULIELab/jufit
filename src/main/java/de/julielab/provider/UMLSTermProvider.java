package de.julielab.provider;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class UMLSTermProvider {
	private static final int CUI_INDEX = 0;
	private static final int TERM_ID_INDEX = 1;
	private static final int LANGUAGE_INDEX = 1;
	static final int TERM_INDEX = 14;
	static final int SUPPRESSIBLE_INDEX = 16;
	public static final int SUI_INDEX = 5;

	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final String... languages) throws IOException {
		return provideUMLSTerms(pathToMRCONSO, pathToMRSTY, false, false,
				languages);
	}

	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final boolean suppressSuppressable, final String... languages)
			throws IOException {
		return provideUMLSTerms(pathToMRCONSO, pathToMRSTY, suppressSuppressable, false,
				languages);
	}

	/*
	 * Sample MRCONSO:
	 *C1880521|ENG|P|L6576556|PF|S7670926|Y|A13035724||||MTH|PN|NOCODE|Enzyme Unit per Milliliter|0|N|256|
	 * C0000005|ENG|S|L0270109|PF|S0007491|Y|A0016458||M0019694|D012711|MSH|EN|
	 * D012711|(131)I-MAA|0|N||
	 */
	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final boolean suppressSuppressable, final boolean onlyPref,
			final String... languages) throws IOException {
		final Set<String> drugOrChemicalCUIs = getDrugOrChemicalCUIs(pathToMRSTY);
		final Set<String> legalLanguages = languages.length == 0 ? null
				: new HashSet<String>(languages.length);
		if (languages.length != 0)
			for (final String language : languages)
				legalLanguages.add(language.toUpperCase());

		return new Iterator<ProvidedTerm>() {
			private final LineIterator li;
			private ProvidedTerm next;

			{
				li = FileUtils.lineIterator(new File(pathToMRCONSO), "UTF-8");
				produceNext();
			}

			private void produceNext() {
				next = null;
				while (li.hasNext() && (null == next)) {
					final String line = (String) li.next();
					final String[] splitline = line.split("\\|");
					final String cui = splitline[CUI_INDEX];
					final String term = splitline[TERM_INDEX];
					final String language = splitline[LANGUAGE_INDEX];
					final boolean isChemicalOrDrug = drugOrChemicalCUIs
							.contains(splitline[CUI_INDEX]);
					final boolean isPref = splitline[2].equals("P")
							&& splitline[4].equals("PF")
							&& splitline[6].equals("Y");
					if (((legalLanguages == null) || legalLanguages
							.contains(language))
							&& (!suppressSuppressable || splitline[SUPPRESSIBLE_INDEX]
									.equals("N")) && (!onlyPref || isPref))
						next = new ProvidedTerm(cui, term, language,
								isChemicalOrDrug, line);
				}
			}

			@Override
			public boolean hasNext() {
				return next != null;
			}

			@Override
			public ProvidedTerm next() {
				final ProvidedTerm toReturn = next;
				produceNext();
				return toReturn;
			}

			@Override
			public void remove() {
				throw new RuntimeException();
			}
		};
	}

	/**
	 * C0000005|T116|A1.4.1.2.1.7|Amino Acid, Peptide, or Protein|AT17648347||
	 *
	 * @param pathToMRSTY
	 * @return
	 */
	static Set<String> getDrugOrChemicalCUIs(final String pathToMRSTY) {
		final Set<String> drugOrChemicalCUIs = new HashSet<String>();

		LineIterator li = null;
		try {
			li = FileUtils.lineIterator(new File(pathToMRSTY), "UTF-8");
			while (li.hasNext()) {
				final String line = (String) li.next();
				final String[] splitline = line.split("\\|");
				if (SemanticGroup.CHEM == SemanticGroup
						.getSemanticGroupForTermId(splitline[TERM_ID_INDEX]))
					drugOrChemicalCUIs.add(splitline[CUI_INDEX]);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			LineIterator.closeQuietly(li);
		}

		return drugOrChemicalCUIs;
	}
}
