/**
 * This is JUFIT, the Jena UMLS Filter Copyright (C) 2015-2018 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software, see the accompanying LICENSE file for details.
 */
// TODO make it possible to give delematizer path to file and use textfile
// instead of regular one, re-enabling all tests

// package de.julielab.umlsfilter.delemmatizer;
//
// import static org.junit.Assert.assertEquals;
//
// import java.io.IOException;
//
// import org.junit.Test;
//
// public class TestDelemmatize {
//
// Tester[] testers = null;
//
// private void fillTesters() throws IOException {
// if (testers == null)
// testers = new Tester[] {
// new Tester(
// "reparación de múltiples desgarros de manguito rotador del hombro durante procedimiento de revisión (procedimiento)",
// Delemmatizer.LANGUAGE_SPANISH, false, true,
// new String[0]),
// new Tester("Anyterm (NOS)", Delemmatizer.LANGUAGE_ENLGLISH,
// false, true, new String[0]),
// new Tester("[chemical] bla",
// Delemmatizer.LANGUAGE_ENLGLISH, true, true,
// "[chemical] bla", "bla"),
// new Tester("(protein) methionine-R-sulfocide reductase",
// Delemmatizer.LANGUAGE_ENLGLISH, true, true,
// "(protein) methionine-R-sulfocide reductase",
// "methionine-R-sulfocide reductase"),
// new Tester("Unclassified sequences",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// new Tester("Heat shock transcription factor (HSF)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Heat shock transcription factor (HSF)", "HSF",
// "Heat shock transcription factor"),
// new Tester("Failure, Renal",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Failure, Renal", "Renal Failure"),
// new Tester("Alzheimer's desease",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Alzheimer's desease", "Alzheimer desease"),
// new Tester("everything is fine",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "everything is fine"),
// new Tester("Chondria <beetle>",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Chondria <beetle>", "Chondria"),
// new Tester("Surgical intervention (finding)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Surgical intervention (finding)",
// "Surgical intervention"),
// new Tester("[V] Alcohol use",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "[V] Alcohol use", "Alcohol use"),
// new Tester("Gluten-free food [generic 1]",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Gluten-free food [generic 1]", "Gluten-free food"),
// new Tester("Other and unspecified leukaemia",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// new Tester("Other cancer", Delemmatizer.LANGUAGE_ENLGLISH,
// false, true, new String[0]),
// new Tester("Head and Neck Squamous Cell Carcinomia",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// /* Ingredient not semantic type! */new Tester(
// "1-Carboxyglutamic Acid [Chemical/Ingredient]",
// Delemmatizer.LANGUAGE_ENLGLISH, true, true,
// "1-Carboxyglutamic Acid [Chemical/Ingredient]",
// "1-Carboxyglutamic Acid"),
// new Tester("bla [bla/blabla/Chemical] bla",
// Delemmatizer.LANGUAGE_ENLGLISH, true, true,
// "bla [bla/blabla/Chemical] bla", "bla bla"),
// new Tester("<bla> Rose <Flower>",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "<bla> Rose <Flower>", "Rose"),
// new Tester("ADHESIVE @@ Bandage",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// new Tester("Other and unspecified leukaemia",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// new Tester("here are exactely five words",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "here are exactely five words"),
// new Tester("Mito (Cell) chondria",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Mito (Cell) chondria", "Mito chondria"),
// new Tester("10*9/L", Delemmatizer.LANGUAGE_ENLGLISH, false,
// true, new String[0]),
// new Tester("and", Delemmatizer.LANGUAGE_ENLGLISH, false,
// true, new String[0]),
// new Tester("und", Delemmatizer.LANGUAGE_GERMAN, false,
// true, new String[0]),
// new Tester("y", Delemmatizer.LANGUAGE_FRENCH, false, true,
// new String[0]),
// new Tester("zijn", Delemmatizer.LANGUAGE_DUTCH, false,
// true, new String[0]),
// new Tester("Mitochondria (Cell)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Mitochondria (Cell)", "Mitochondria"),
// new Tester("Anyterm [NOS]", Delemmatizer.LANGUAGE_ENLGLISH,
// false, true, new String[0]),
// new Tester("Other and unspecified leukaemia",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// new String[0]),
// new Tester(
// "Selective Sorotonin Reuptake Inhabitors (SSRIs)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Selective Sorotonin Reuptake Inhabitors (SSRIs)",
// "Selective Sorotonin Reuptake Inhabitors", "SSRIs"),
// new Tester("Enzyme, Branching",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Enzyme, Branching", "Branching Enzyme"),
// new Tester("5'saccharose", Delemmatizer.LANGUAGE_ENLGLISH,
// false, true, "5'saccharose"),
// new Tester("flagellar filament (sensu Bacteria)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "flagellar filament (sensu Bacteria)",
// "flagellar filament"),
// new Tester("EC 2.7.1.112", Delemmatizer.LANGUAGE_ENLGLISH,
// false, true, new String[0]),
// new Tester("(test) for greediness (test)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, false,
// "(test) for greediness (test)", "for greediness"),
// new Tester("a (test) for greediness (test)",
// Delemmatizer.LANGUAGE_ENLGLISH, false, false,
// "a (test) for greediness (test)",
// "a (test) for greediness"),
// new Tester(
// "1-Alkyl-2- (disorder) Acylphosphatidates (disorder)",
// Delemmatizer.LANGUAGE_SPANISH,
// false,
// false,
// "1-Alkyl-2- (disorder) Acylphosphatidates (disorder)",
// "1-Alkyl-2- (disorder) Acylphosphatidates"),
// new Tester("Test (Drosophila) test",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "Test (Drosophila) test", "Test test"),
//
// // tests for
//
// // multilanguage
// // semantic types
// new Tester("Test (producto) test",
// Delemmatizer.LANGUAGE_SPANISH, false, true,
// "Test (producto) test", "Test test"),
// // tests for
// // multilanguage
// // semantic types
// new Tester("and", Delemmatizer.LANGUAGE_GERMAN, false,
// false, "and"), // checks if
// // deleteShortToken
// // doesnt check for
// // stopwords in non EN
// // terms when using old
// // version
// new Tester(
// "Akute respiratorische Insuffizienz, anderenorts nicht klassifiziert",
// Delemmatizer.LANGUAGE_GERMAN, false, true,
// new String[0]),
// new Tester("carne sin clasificar",
// Delemmatizer.LANGUAGE_SPANISH, false, true,
// new String[0]),
// new Tester("carne sin cla", Delemmatizer.LANGUAGE_SPANISH,
// false, true, "carne sin cla"),
// new Tester("<protein> methionine-R-sulfocide reductase",
// Delemmatizer.LANGUAGE_ENLGLISH, false, true,
// "methionine-R-sulfocide reductase",
// "<protein> methionine-R-sulfocide reductase"),
// // new Tester("carne sin clasificar", Delemmatizer.LANGUAGE_SPANISH,
// // false, false, new
// // String[]{"carne sin clasificar"}), //soll auf Julilab-Version
// // testen; Problem: Flag nicht implementiert, die inputs bei einer
// // Sprache zu vernachlässigen.
//
// };
// }
//
// @Test
// public void testDelemmatize() throws IOException {
// fillTesters();
// for (final Tester tester : testers)
// assertEquals(tester.expected, tester.output);
// }
//
// }
