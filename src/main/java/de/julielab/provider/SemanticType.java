/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2023 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel and Christina Lohr
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
	T001("LIVB_Organism",									"T001",	"LIVB"),
	T002("LIVB_Plant",										"T002",	"LIVB"),
	T004("LIVB_Fungus",										"T004",	"LIVB"),
	T005("LIVB_Virus",										"T005",	"LIVB"),
	T007("LIVB_Bacterium",									"T007",	"LIVB"),
	T008("LIVB_Animal",										"T008",	"LIVB"),
	T010("LIVB_Vertebrate",									"T010",	"LIVB"),
	T011("LIVB_Amphibian",									"T011",	"LIVB"),
	T012("LIVB_Bird",										"T012",	"LIVB"),
	T013("LIVB_Fish",										"T013",	"LIVB"),
	T014("LIVB_Reptile",									"T014",	"LIVB"),
	T015("LIVB_Mammal",										"T015",	"LIVB"),
	T016("LIVB_Human",										"T016",	"LIVB"),
	T017("ANAT_Anatomical_Structure",						"T017",	"ANAT"),
	T018("ANAT_Embryonic_Structure",						"T018",	"ANAT"),
	T019("DISO_Congenital_Abnormality",						"T019",	"DISO"),
	T020("DISO_Acquired_Abnormality",						"T020",	"DISO"),
	T021("ANAT_Fully_Formed_Anatomical_Structure",			"T021",	"ANAT"),
	T022("ANAT_Body_System",								"T022",	"ANAT"),
	T023("ANAT_Body_Part,_Organ,_or_Organ_Component",		"T023",	"ANAT"),
	T024("ANAT_Tissue",										"T024",	"ANAT"),
	T025("ANAT_Cell",										"T025",	"ANAT"),
	T026("ANAT_Cell_Component",								"T026",	"ANAT"),
	T028("GENE_Gene_or_Genome",								"T028",	"GENE"),
	T029("ANAT_Body_Location_or_Region",					"T029",	"ANAT"),
	T030("ANAT_Body_Space_or_Junction",						"T030",	"ANAT"),
	T031("ANAT_Body_Substance",								"T031",	"ANAT"),
	T032("PHYS_Organism_Attribute",							"T032",	"PHYS"),
	T033("DISO_Finding",									"T033",	"DISO"),
	T034("PHEN_Laboratory_or_Test_Result",					"T034",	"PHEN"),
	T037("DISO_Injury_or_Poisoning",						"T037",	"DISO"),
	T038("PHEN_Biologic_Function",							"T038",	"PHEN"),
	T039("PHYS_Physiologic_Function",						"T039",	"PHYS"),
	T040("PHYS_Organism_Function",							"T040",	"PHYS"),
	T041("PHYS_Mental_Process",								"T041",	"PHYS"),
	T042("PHYS_Organ_or_Tissue_Function",					"T042",	"PHYS"),
	T043("PHYS_Cell_Function",								"T043",	"PHYS"),
	T044("PHYS_Molecular_Function",							"T044",	"PHYS"),
	T045("PHYS_Genetic_Function",							"T045",	"PHYS"),
	T046("DISO_Pathologic_Function",						"T046",	"DISO"),
	T047("DISO_Disease_or_Syndrome",						"T047",	"DISO"),
	T048("DISO_Mental_or_Behavioral_Dysfunction",			"T048",	"DISO"),
	T049("DISO_Cell_or_Molecular_Dysfunction",				"T049",	"DISO"),
	T050("DISO_Experimental_Model_of_Disease",				"T050",	"DISO"),
	T051("ACTI_Event",										"T051",	"ACTI"),
	T052("ACTI_Activity",									"T052",	"ACTI"),
	T053("ACTI_Behavior",									"T053",	"ACTI"),
	T054("ACTI_Social_Behavior",							"T054",	"ACTI"),
	T055("ACTI_Individual_Behavior",						"T055",	"ACTI"),
	T056("ACTI_Daily_or_Recreational_Activity",				"T056",	"ACTI"),
	T057("ACTI_Occupational_Activity",						"T057",	"ACTI"),
	T058("PROC_Health_Care_Activity",						"T058",	"PROC"),
	T059("PROC_Laboratory_Procedure",						"T059",	"PROC"),
	T060("PROC_Diagnostic_Procedure",						"T060",	"PROC"),
	T061("PROC_Therapeutic_or_Preventive_Procedure",		"T061",	"PROC"),
	T062("PROC_Research_Activity",							"T062",	"PROC"),
	T063("PROC_Molecular_Biology_Research_Technique",		"T063",	"PROC"),
	T064("ACTI_Governmental_or_Regulatory_Activity",		"T064",	"ACTI"),
	T065("PROC_Educational_Activity",						"T065",	"PROC"),
	T066("ACTI_Machine_Activity",							"T066",	"ACTI"),
	T067("PHEN_Phenomenon_or_Process",						"T067",	"PHEN"),
	T068("PHEN_Human-caused_Phenomenon_or_Process",			"T068",	"PHEN"),
	T069("PHEN_Environmental_Effect_of_Humans",				"T069",	"PHEN"),
	T070("PHEN_Natural_Phenomenon_or_Process",				"T070",	"PHEN"),
	T071("OBJC_Entity",										"T071",	"OBJC"),
	T072("OBJC_Physical_Object",							"T072",	"OBJC"),
	T073("OBJC_Manufactured_Object",						"T073",	"OBJC"),
	T074("DEVI_Medical_Device",								"T074",	"DEVI"),
	T075("DEVI_Research_Device",							"T075",	"DEVI"),
	T077("CONC_Conceptual_Entity",							"T077",	"CONC"),
	T078("CONC_Idea_or_Concept",							"T078",	"CONC"),
	T079("CONC_Temporal_Concept",							"T079",	"CONC"),
	T080("CONC_Qualitative_Concept",						"T080",	"CONC"),
	T081("CONC_Quantitative_Concept",						"T081",	"CONC"),
	T082("CONC_Spatial_Concept",							"T082",	"CONC"),
	T083("GEOG_Geographic_Area",							"T083",	"GEOG"),
	T085("GENE_Molecular_Sequence",							"T085",	"GENE"),
	T086("GENE_Nucleotide_Sequence",						"T086",	"GENE"),
	T087("GENE_Amino_Acid_Sequence",						"T087",	"GENE"),
	T088("GENE_Carbohydrate_Sequence",						"T088",	"GENE"),
	T089("CONC_Regulation_or_Law",							"T089",	"CONC"),
	T090("OCCU_Occupation_or_Discipline",					"T090",	"OCCU"),
	T091("OCCU_Biomedical_Occupation_or_Discipline",		"T091",	"OCCU"),
	T092("ORGA_Organization",								"T092",	"ORGA"),
	T093("ORGA_Health_Care_Related_Organization",			"T093",	"ORGA"),
	T094("ORGA_Professional_Society",						"T094",	"ORGA"),
	T095("ORGA_Self-help_or_Relief_Organization",			"T095",	"ORGA"),
	T096("LIVB_Group",										"T096",	"LIVB"),
	T097("LIVB_Professional_or_Occupational_Group",			"T097",	"LIVB"),
	T098("LIVB_Population_Group",							"T098",	"LIVB"),
	T099("LIVB_Family_Group",								"T099",	"LIVB"),
	T100("LIVB_Age_Group",									"T100",	"LIVB"),
	T101("LIVB_Patient_or_Disabled_Group",					"T101",	"LIVB"),
	T102("CONC_Group_Attribute",							"T102",	"CONC"),
	T103("CHEM_Chemical",									"T103",	"CHEM"),
	T104("CHEM_Chemical_Viewed_Structurally",				"T104",	"CHEM"),
	T109("CHEM_Organic_Chemical",							"T109",	"CHEM"),
	T114("CHEM_Nucleic_Acid,_Nucleoside,_or_Nucleotide",	"T114",	"CHEM"),
	T116("CHEM_Amino_Acid,_Peptide,_or_Protein",			"T116",	"CHEM"),
	T120("CHEM_Chemical_Viewed_Functionally",				"T120",	"CHEM"),
	T121("CHEM_Pharmacologic_Substance",					"T121",	"CHEM"),
	T122("CHEM_Biomedical_or_Dental_Material",				"T122",	"CHEM"),
	T123("CHEM_Biologically_Active_Substance",				"T123",	"CHEM"),
	T125("CHEM_Hormone",									"T125",	"CHEM"),
	T126("CHEM_Enzyme",										"T126",	"CHEM"),
	T127("CHEM_Vitamin",									"T127",	"CHEM"),
	T129("CHEM_Immunologic_Factor",							"T129",	"CHEM"),
	T130("CHEM_Indicator,_Reagent,_or_Diagnostic_Aid",		"T130",	"CHEM"),
	T131("CHEM_Hazardous_or_Poisonous_Substance",			"T131",	"CHEM"),
	T167("OBJC_Substance",									"T167",	"OBJC"),
	T168("OBJC_Food",										"T168",	"OBJC"),
	T169("CONC_Functional_Concept",							"T169",	"CONC"),
	T170("CONC_Intellectual_Product",						"T170",	"CONC"),
	T171("CONC_Language",									"T171",	"CONC"),
	T184("DISO_Sign_or_Symptom",							"T184",	"DISO"),
	T185("CONC_Classification",								"T185",	"CONC"),
	T190("DISO_Anatomical_Abnormality",						"T190",	"DISO"),
	T191("DISO_Neoplastic_Process",							"T191",	"DISO"),
	T192("CHEM_Receptor",									"T192",	"CHEM"),
	T194("LIVB_Archaeon",									"T194",	"LIVB"),
	T195("CHEM_Antibiotic",									"T195",	"CHEM"),
	T196("CHEM_Element,_Ion,_or_Isotope",					"T196",	"CHEM"),
	T197("CHEM_Inorganic_Chemical",							"T197",	"CHEM"),
	T200("CHEM_Clinical_Drug",								"T200",	"CHEM"),
	T201("PHYS_Clinical_Attribute",							"T201",	"PHYS"),
	T203("DEVI_Drug_Delivery_Device",						"T203",	"DEVI"),
	T204("LIVB_Eukaryote",									"T204",	"LIVB"),
	;

	public final String longName;
	public final String semanticGroup;
	public final String semanticType;
	private final ImmutableSet<String> termIds;
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

	private SemanticType(final String... strings)
	{
		longName = strings[0];
		termIds = ImmutableSet.copyOf(Arrays.asList(strings).subList(1, strings.length));
		semanticGroup = strings[2];
		semanticType = strings[1];
	}
}