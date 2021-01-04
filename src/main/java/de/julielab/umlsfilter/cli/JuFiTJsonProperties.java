package de.julielab.umlsfilter.cli;

import java.util.ArrayList;
import java.util.List;

public class JuFiTJsonProperties
{
	public String pathToMRCONSO;
	public String pathToMRSTY;
	public String language;
	public String outFileName;
	public String outputFormat;
	
	public List<String> SemanticGroups = new ArrayList<>();
	public List<String> SemanticTypes = new ArrayList<>();
	
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
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public String getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	public List<String> getSemanticGroups() {
		return SemanticGroups;
	}
	public void setSemanticGroups(List<String> semanticGroups) {
		SemanticGroups = semanticGroups;
	}
	public List<String> getSemanticTypes() {
		return SemanticTypes;
	}
	public void setSemanticTypes(List<String> semanticTypes) {
		SemanticTypes = semanticTypes;
	}
}
