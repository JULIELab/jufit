/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

package de.julielab.umlsfilter.cli;

import java.util.ArrayList;
import java.util.List;

public class JuFiTJsonProperties
{
	public String pathToMRCONSO;
	public String pathToMRSTY;
	public String language;
	public List<String> SemanticGroups = new ArrayList<>();
	public List<String> SemanticTypes = new ArrayList<>();
	public Boolean applyFilters;
	public String rulesFileName;
	public String outFileName;
	public String outputFormat;


	public String getPathToMRCONSO() {
		return pathToMRCONSO;
	}
	public void setPathToMRCONSO(String pathToMRCONSO) {
		this.pathToMRCONSO = pathToMRCONSO;
	}

	public String getPathToMRSTY() {
		return pathToMRSTY;
	}
	public void setPathToMRSTY(String pathToMRSTY) {
		this.pathToMRSTY = pathToMRSTY;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getSemanticTypes() {
		return SemanticTypes;
	}
	public void setSemanticTypes(List<String> semanticTypes) {
		SemanticTypes = semanticTypes;
	}

	public List<String> getSemanticGroups() {
		return SemanticGroups;
	}
	public void setSemanticGroups(List<String> semanticGroups) {
		SemanticGroups = semanticGroups;
	}

	public Boolean getApplyFilters() {
		return applyFilters;
	}
	public void setApplyFilters(Boolean applyFilters) {
		this.applyFilters = applyFilters;
	}

	public String getRulesFileName() {
		return rulesFileName;
	}
	public void setRulesFileName(String rulesFileName) {
		this.rulesFileName = rulesFileName;
	}

	public String getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

}
