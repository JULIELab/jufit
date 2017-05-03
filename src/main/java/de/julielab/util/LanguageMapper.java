package de.julielab.util;

import java.util.HashMap;

public class LanguageMapper {

	private final static HashMap<String, String> umls2obo = new HashMap<String, String>();
	private final static HashMap<String, String> obo2umls = new HashMap<String, String>();

	static {
		umls2obo.put("BAQ", "EU"); // Basque
		umls2obo.put("CZE", "CS"); // Czech
		umls2obo.put("DAN", "DA"); // Danish
		umls2obo.put("DUT", "NL"); // Dutch
		umls2obo.put("ENG", "EN"); // English
		umls2obo.put("FIN", "FI"); // Finnish
		umls2obo.put("FRE", "FR"); // French
		umls2obo.put("GER", "DE"); // German
		umls2obo.put("HEB", "IW"); // Hebrew
		umls2obo.put("HUN", "HU"); // Hungarian
		umls2obo.put("ITA", "IT"); // Italian
		umls2obo.put("JPN", "JA"); // Japanese
		umls2obo.put("KOR", "KO"); // Korean
		umls2obo.put("LAV", "LV"); // Latvian
		umls2obo.put("NOR", "NO"); // Norwegian
		umls2obo.put("POL", "PL"); // Polish
		umls2obo.put("POR", "PT"); // Portuguese
		umls2obo.put("RUS", "RU"); // Russian
		umls2obo.put("SCR", "HR"); // Croatian
		umls2obo.put("SPA", "ES"); // Spanish
		umls2obo.put("SWE", "SV"); // Swedish;

		for (final String k : umls2obo.keySet())
			obo2umls.put(umls2obo.get(k), k);
	};

	/**
	 * converts language as language in UMLS is defined by 3-letter code, but
	 * 2-letter code is used in mantra obo
	 *
	 * @param lang
	 *            Language to convert
	 * @return converted language
	 */
	public static String convertLanguageUmls2Obo(final String lang) {
		return umls2obo.get(lang);

	}

	/**
	 * converts language as language in UMLS is defined by 3-letter code, but
	 * 2-letter code is used in mantra obo
	 *
	 * @param lang
	 *            Language to convert
	 * @return converted language
	 */
	public static String convertLanguageObo2Umls(final String lang) {
		return obo2umls.get(lang);

	}

}
