/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2020 JULIE LAB
 * Authors: Johannes Hellrich, Sven Buechel, Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

package de.julielab.provider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;

public class UMLSTermProvider {
	private static final int CUI_INDEX = 0;
	private static final int TERM_ID_INDEX = 1;
	private static final int LANGUAGE_INDEX = 1;
	static final int TERM_INDEX = 14;
	static final int SUPPRESSIBLE_INDEX = 16;
	public static final int SUI_INDEX = 5;

	/**
	 * C0000005|T116|A1.4.1.2.1.7|Amino Acid, Peptide, or Protein|AT17648347||
	 *
	 * @param pathToMRSTY
	 * @return
	 * @throws IOException
	 */
	static Set<String> getSemanticGroupCUIs(
			final String pathToMRSTY, final Set<SemanticType> semanticGroups) throws IOException
	{
		final Set<String> cuis = new HashSet<>();
		Files.lines(Paths.get(pathToMRSTY), Charset.forName("UTF-8")).forEach(line ->
				{
					final String[] splitline = line.split("\\|");
					if (semanticGroups.contains(SemanticType.getSemanticGroupForTermId(splitline[TERM_ID_INDEX])))
					{
						cuis.add(splitline[CUI_INDEX]);
					}
				});
		
		return cuis;
	}

	/*
	 * Sample MRCONSO:
	 * C1880521|ENG|P|L6576556|PF|S7670926|Y|A13035724||||MTH|PN|NOCODE|Enzyme
	 * Unit per Milliliter|0|N|256|
	 * C0000005|ENG|S|L0270109|PF|S0007491|Y|A0016458||M0019694|D012711|MSH|EN|
	 * D012711|(131)I-MAA|0|N||
	 */
	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final boolean suppressSuppressable, final boolean onlyPref,
			final Set<SemanticType> onlyTheseSemanticGroups,
			final String... languages) throws IOException
	{
		final Set<String> drugOrChemicalCUIs = getSemanticGroupCUIs(
				pathToMRSTY,
				Sets.newHashSet(SemanticType.T116)//.add(SemanticGroup.ANAT)
				);

		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T116)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T195)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T123)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T122)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T103)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T120)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T104)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T200)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T196)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T126)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T131)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T125)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T129)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T130)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T197)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T114)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T109)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T121)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T192)));
		drugOrChemicalCUIs.addAll(getSemanticGroupCUIs(pathToMRSTY, Sets.newHashSet(SemanticType.T127)));

		final Set<String> legalLanguages = languages.length == 0 ? null
				: new HashSet<>(languages.length);

		if (languages.length != 0){
			for (final String language : languages)
			{
				legalLanguages.add(language.toUpperCase());
			}
		}

		final Set<String> legalCUIs = ((onlyTheseSemanticGroups == null) || onlyTheseSemanticGroups.isEmpty()) ? null
						: getSemanticGroupCUIs(pathToMRSTY, onlyTheseSemanticGroups);

		return new Iterator<ProvidedTerm>(){
			private final Iterator<String> li = Files
					.lines(Paths.get(pathToMRCONSO), Charset.forName("UTF-8"))
					.iterator();
			private ProvidedTerm next;{
				produceNext();
			}

			@Override
			public boolean hasNext(){
				return next != null;
			}

			@Override
			public ProvidedTerm next(){
				final ProvidedTerm toReturn = next;
				produceNext();
				return toReturn;
			}

			private void produceNext(){
				next = null;
				while (li.hasNext() && (null == next))
				{
					final String line = li.next();
					final String[] splitline = line.split("\\|");
					final String cui = splitline[CUI_INDEX];
					final String term = splitline[TERM_INDEX];
					final String language = splitline[LANGUAGE_INDEX];
					final boolean isChemicalOrDrug = drugOrChemicalCUIs.contains(splitline[CUI_INDEX]);
					final boolean isPref = splitline[2].equals("P")	&& splitline[4].equals("PF") && splitline[6].equals("Y");

					if (
							((legalCUIs == null) || legalCUIs.contains(cui))
							&& ((legalLanguages == null) || legalLanguages.contains(language))
							&& (!suppressSuppressable || splitline[SUPPRESSIBLE_INDEX].equals("N"))
							&& (!onlyPref || isPref)
						)
						next = new ProvidedTerm(cui, term, language, isChemicalOrDrug, line);
				}
			}

			@Override
			public void remove(){
				throw new RuntimeException();
			}
		};
	}

	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final boolean suppressSuppressable,
			final Set<SemanticType> onlyTheseSemanticGroups,
			final String... languages) throws IOException
	{
		return provideUMLSTerms(pathToMRCONSO, pathToMRSTY, suppressSuppressable, false, onlyTheseSemanticGroups, languages);
	}

	public static Iterator<ProvidedTerm> provideUMLSTerms(
			final String pathToMRCONSO, final String pathToMRSTY,
			final Set<SemanticType> onlyTheseSemanticGroups,
			final String... languages) throws IOException
	{
		return provideUMLSTerms(pathToMRCONSO, pathToMRSTY, false, false, onlyTheseSemanticGroups, languages);
	}
}