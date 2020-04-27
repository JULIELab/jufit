/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
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
 * Update from 04/27/2020:
 * The definition of the Semantic Groups and the Semantic Types changed.
 * We updated the Semantic Types of the Semantic Groups from
 * https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt
 *
 * @author hellrich, chlor
 *
 */

public enum SemanticGroup {
	ACTI("Activities & Behaviors", "T052", "T053", "T056", "T051", "T064", "T055", "T066", "T057", "T054"),
	ANAT("Anatomy", "T017", "T029", "T023", "T030", "T031", "T022", "T025", "T026", "T018",    "T021", "T024"),
	CHEM("Chemicals & Drugs", "T116", "T195", "T123", "T122", "T118", "T103", "T120", "T104", "T200", "T111", "T196", "T126", "T131", "T125", "T129", "T130", "T197", "T119", "T124", "T114", "T109", "T115", "T121", "T192", "T110", "T127"),
	CONC("Concepts & Ideas", "T185", "T077", "T169", "T102", "T078", "T170", "T171", "T080", "T081", "T089", "T082", "T079"),
	DEVI("Devices", "T203", "T074", "T075"),
	DISO("Disorders", "T020", "T190", "T049", "T019", "T047", "T050", "T033", "T037", "T048", "T191", "T046", "T184"),
	GENE("Genes & Molecular Sequences", "T087", "T088", "T028", "T085", "T086"),
	GEOG("Geographic Areas", "T083"),
	LIVB("Living Beings", "T100", "T011", "T008", "T194", "T007", "T012", "T204", "T099", "T013", "T004", "T096", "T016", "T015", "T001", "T101", "T002", "T098", "T097", "T014", "T010", "T005"),
	OBJC("Objects", "T071", "T168", "T073", "T072", "T167"),
	OCCU("Occupations", "T091", "T090"),
	ORGA("Organizations", "T093", "T092", "T094", "T095"),
	PHEN("Phenomena", "T038", "T069", "T068", "T034", "T070", "T067"),
	PHYS("Physiology", "T043", "T201", "T045", "T041", "T044", "T032", "T040", "T042", "T039"),
	PROC("Procedures", "T060", "T065", "T058", "T059", "T063", "T062", "T061");

	private final static Map<String, SemanticGroup> termId2group = new HashMap<>();

	public static Stream<String> getNames() {
		return Stream.of(SemanticGroup.values()).map(SemanticGroup::name);
	}

	public static SemanticGroup getSemanticGroupForTermId(final String termId) {
		if (termId2group.containsKey(termId))
			return termId2group.get(termId);
		for (final SemanticGroup sg : SemanticGroup.values())
			if (sg.termIds.contains(termId)) {
				termId2group.put(termId, sg);
				return sg;
			}
		throw new IllegalArgumentException(
				"TermId " + termId + " not covered by this enumeration.");
	}

	public final String longName;

	private final ImmutableSet<String> termIds;

	private SemanticGroup(final String... strings) {
		longName = strings[0];
		termIds = ImmutableSet
				.copyOf(Arrays.asList(strings).subList(1, strings.length));
	}
}