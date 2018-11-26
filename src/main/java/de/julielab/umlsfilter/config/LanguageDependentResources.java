/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
package de.julielab.umlsfilter.config;

import java.util.HashMap;

/**
 * Used to read json-files per gson
 *
 * @author hellrich
 *
 */
class LanguageDependentResources {
	String[] rules;
	HashMap<String, HashMap<String, String[]>> ruleParameters;
}
