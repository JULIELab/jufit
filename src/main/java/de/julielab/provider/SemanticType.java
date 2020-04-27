/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2020 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */

package de.julielab.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;

/**
 *
 * Based on executing the following shell script, tmp being the SemGroups.txt
 * file and thereafter deleting a trailing ,
 *
 * for tid in $(cut -d'|' -f"1" tmp | uniq); do echo $tid"("; echo \"$(grep $tid
 * tmp | cut -d'|' -f"2" | uniq)'",'; echo $(grep $tid tmp | cut -d'|' -f"3" ) |
 * sed 's/^/"/;s/ /", "/g;s/$/"),/' ; done
 *
 * First entry on each enum, i.e., short name, was then manually added
 * 
 * Values of Semantic Types derived from https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt
 *
 * @author hellrich, chlor
 *
 */
public enum SemanticType
{
	T001("LIVB_Organism","T001"),
	T002("LIVB_Plant","T002"),
	T004("LIVB_Fungus","T004"),
	T005("LIVB_Virus","T005"),
	T007("LIVB_Bacterium","T007"),
	T008("LIVB_Animal","T008"),
	T010("LIVB_Vertebrate","T010"),
	T011("LIVB_Amphibian","T011"),
	T012("LIVB_Bird","T012"),
	T013("LIVB_Fish","T013"),
	T014("LIVB_Reptile","T014"),
	T015("LIVB_Mammal","T015"),
	T016("LIVB_Human","T016"),
	T017("ANAT_Anatomical_Structure","T017"),
	T018("ANAT_Embryonic_Structure","T018"),
	T019("DISO_Congenital_Abnormality","T019"),
	T020("DISO_Acquired_Abnormality","T020"),
	T021("ANAT_Fully_Formed_Anatomical_Structure","T021"),
	T022("ANAT_Body_System","T022"),
	T023("ANAT_Body_Part,_Organ,_or_Organ_Component","T023"),
	T024("ANAT_Tissue","T024"),
	T025("ANAT_Cell","T025"),
	T026("ANAT_Cell_Component","T026"),
	T028("GENE_Gene_or_Genome","T028"),
	T029("ANAT_Body_Location_or_Region","T029"),
	T030("ANAT_Body_Space_or_Junction","T030"),
	T031("ANAT_Body_Substance","T031"),
	T032("PHYS_Organism_Attribute","T032"),
	T033("DISO_Finding","T033"),
	T034("PHEN_Laboratory_or_Test_Result","T034"),
	T037("DISO_Injury_or_Poisoning","T037"),
	T038("PHEN_Biologic_Function","T038"),
	T039("PHYS_Physiologic_Function","T039"),
	T040("PHYS_Organism_Function","T040"),
	T041("PHYS_Mental_Process","T041"),
	T042("PHYS_Organ_or_Tissue_Function","T042"),
	T043("PHYS_Cell_Function","T043"),
	T044("PHYS_Molecular_Function","T044"),
	T045("PHYS_Genetic_Function","T045"),
	T046("DISO_Pathologic_Function","T046"),
	T047("DISO_Disease_or_Syndrome","T047"),
	T048("DISO_Mental_or_Behavioral_Dysfunction","T048"),
	T049("DISO_Cell_or_Molecular_Dysfunction","T049"),
	T050("DISO_Experimental_Model_of_Disease","T050"),
	T051("ACTI_Event","T051"),
	T052("ACTI_Activity","T052"),
	T053("ACTI_Behavior","T053"),
	T054("ACTI_Social_Behavior","T054"),
	T055("ACTI_Individual_Behavior","T055"),
	T056("ACTI_Daily_or_Recreational_Activity","T056"),
	T057("ACTI_Occupational_Activity","T057"),
	T058("PROC_Health_Care_Activity","T058"),
	T059("PROC_Laboratory_Procedure","T059"),
	T060("PROC_Diagnostic_Procedure","T060"),
	T061("PROC_Therapeutic_or_Preventive_Procedure","T061"),
	T062("PROC_Research_Activity","T062"),
	T063("PROC_Molecular_Biology_Research_Technique","T063"),
	T064("ACTI_Governmental_or_Regulatory_Activity","T064"),
	T065("PROC_Educational_Activity","T065"),
	T066("ACTI_Machine_Activity","T066"),
	T067("PHEN_Phenomenon_or_Process","T067"),
	T068("PHEN_Human-caused_Phenomenon_or_Process","T068"),
	T069("PHEN_Environmental_Effect_of_Humans","T069"),
	T070("PHEN_Natural_Phenomenon_or_Process","T070"),
	T071("OBJC_Entity","T071"),
	T072("OBJC_Physical_Object","T072"),
	T073("OBJC_Manufactured_Object","T073"),
	T074("DEVI_Medical_Device","T074"),
	T075("DEVI_Research_Device","T075"),
	T077("CONC_Conceptual_Entity","T077"),
	T078("CONC_Idea_or_Concept","T078"),
	T079("CONC_Temporal_Concept","T079"),
	T080("CONC_Qualitative_Concept","T080"),
	T081("CONC_Quantitative_Concept","T081"),
	T082("CONC_Spatial_Concept","T082"),
	T083("GEOG_Geographic_Area","T083"),
	T085("GENE_Molecular_Sequence","T085"),
	T086("GENE_Nucleotide_Sequence","T086"),
	T087("GENE_Amino_Acid_Sequence","T087"),
	T088("GENE_Carbohydrate_Sequence","T088"),
	T089("CONC_Regulation_or_Law","T089"),
	T090("OCCU_Occupation_or_Discipline","T090"),
	T091("OCCU_Biomedical_Occupation_or_Discipline","T091"),
	T092("ORGA_Organization","T092"),
	T093("ORGA_Health_Care_Related_Organization","T093"),
	T094("ORGA_Professional_Society","T094"),
	T095("ORGA_Self-help_or_Relief_Organization","T095"),
	T096("LIVB_Group","T096"),
	T097("LIVB_Professional_or_Occupational_Group","T097"),
	T098("LIVB_Population_Group","T098"),
	T099("LIVB_Family_Group","T099"),
	T100("LIVB_Age_Group","T100"),
	T101("LIVB_Patient_or_Disabled_Group","T101"),
	T102("CONC_Group_Attribute","T102"),
	T103("CHEM_Chemical","T103"),
	T104("CHEM_Chemical_Viewed_Structurally","T104"),
	T109("CHEM_Organic_Chemical","T109"),
	T114("CHEM_Nucleic_Acid,_Nucleoside,_or_Nucleotide","T114"),
	T116("CHEM_Amino_Acid,_Peptide,_or_Protein","T116"),
	T120("CHEM_Chemical_Viewed_Functionally","T120"),
	T121("CHEM_Pharmacologic_Substance","T121"),
	T122("CHEM_Biomedical_or_Dental_Material","T122"),
	T123("CHEM_Biologically_Active_Substance","T123"),
	T125("CHEM_Hormone","T125"),
	T126("CHEM_Enzyme","T126"),
	T127("CHEM_Vitamin","T127"),
	T129("CHEM_Immunologic_Factor","T129"),
	T130("CHEM_Indicator,_Reagent,_or_Diagnostic_Aid","T130"),
	T131("CHEM_Hazardous_or_Poisonous_Substance","T131"),
	T167("OBJC_Substance","T167"),
	T168("OBJC_Food","T168"),
	T169("CONC_Functional_Concept","T169"),
	T170("CONC_Intellectual_Product","T170"),
	T171("CONC_Language","T171"),
	T184("DISO_Sign_or_Symptom","T184"),
	T185("CONC_Classification","T185"),
	T190("DISO_Anatomical_Abnormality","T190"),
	T191("DISO_Neoplastic_Process","T191"),
	T192("CHEM_Receptor","T192"),
	T194("LIVB_Archaeon","T194"),
	T195("CHEM_Antibiotic","T195"),
	T196("CHEM_Element,_Ion,_or_Isotope","T196"),
	T197("CHEM_Inorganic_Chemical","T197"),
	T200("CHEM_Clinical_Drug","T200"),
	T201("PHYS_Clinical_Attribute","T201"),
	T203("DEVI_Drug_Delivery_Device","T203"),
	T204("LIVB_Eukaryote","T204"),
	;

	private final static Map<String, SemanticType> termId2group = new HashMap<>();

	public static Stream<String> getNames()
	{
		return Stream.of(SemanticType.values()).map(SemanticType::name);
	}

	public static SemanticType getSemanticGroupForTermId(final String termId)
	{
		if (termId2group.containsKey(termId))
		{
			return termId2group.get(termId);
		}
		for (final SemanticType sg : SemanticType.values())
		{
			if (sg.termIds.contains(termId)) 
			{
				termId2group.put(termId, sg);
				return sg;
			}
		}
		throw new IllegalArgumentException("TermId " + termId + " not covered by this enumeration.");
	}

	public final String longName;

	private final ImmutableSet<String> termIds;

	private SemanticType(final String... strings)
	{
		longName = strings[0];
		termIds = ImmutableSet.copyOf(Arrays.asList(strings).subList(1, strings.length));
	}
}