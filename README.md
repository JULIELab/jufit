# JuFiT - Jena UMLS Filter

This is the **Jena UMLS Filter Tool (JuFiT)**, a rule-based open-source tool to filter and enhance entries of the [Unified Medical Language System (UMLS)](https://www.nlm.nih.gov/research/umls/index.html). JuFiT uses rules similar to those in [MetaMap](https://www.nlm.nih.gov/research/umls/implementation_resources/metamap.html) and [Casper](https://biosemantics.erasmusmc.nl/index.php/software/casper), but extends these by adding support for multiple languages and their language-specific phenomena, e.g., German compounds. We provide rules for English, Spanish, French, German, and Dutch, yet the declarative implementation of these rules makes it possible to add support for further languages without changing JuFiT’s source code.

### License
  * BSD license (2-clause).

## Recent Changes:
* 1.0 Initial versions
* 1.1 New output formats, new command line interface, change to license
* 1.2 More request options by definitions of Semantic Types and Semantic Groups derived from [https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt](https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt)
* 1.3 Updates of including packages, Json based configuration for input and Python based wrapper.

## Note before usage

### Input and prerequisites

* You need files from the [UMLS](https://www.nlm.nih.gov/research/umls/licensedcontent/umlsknowledgesources.html).
* After registration at [UTS](https:/uts.nlm.nih.gov), you can download the UMLS files from the [U.S. National Library of Medicine (NIH)](https://www.nlm.nih.gov/research/umls/).
* Unpack the UMLS files of a version (e.g., `umls-2022AB-full.zip`).
  * Unpack all `MRCONSO.RRF` files (files of Concept Names and Sources) concatenate these both to one file, e.g.: `cat MRCONSO.RRF.aa MRCONSO.RRF.ab MRCONSO.RRF.ac > MRCONSO.RRF`
  * Unpack `MRSTY.RFF`, the file of Semantic Types.
* Use these files for the input of JuFiT.

### Configuration
* Prepare the configuration file, e.g. [jufit_config.json](jufit_config.json):

```
{
  "pathToMRCONSO"  : "MRCONSO.RRF",
  "pathToMRSTY"    : "MRSTY.RRF",
  "language"       : "GER",
  "SemanticTypes"  : [],
  "SemanticGroups" : ["ANAT"],
  "applyFilters"   : "false",
  "rulesFileName"  : "resources_GER.json",
  "outputFormat"   : "grounded",
  "outFileName"    : "JuFiT_dictionary.txt"
}
```

* Usage of `SemanticTypes` and `SemanticGroups`:
  * For detailed definitions look into [https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt](https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt)
  * If the definitions of `SemanticTypes` and `SemanticGroups` are empty all definitions of `SemanticTypes` and `SemanticGroups` will be processed, hence the whole vocabulary of a language.
  * If the definition of `language` is empty, the routine is aborted.
* Languages must be given as three letter abbreviations, e.g.:
  * `BAQ` (Basque)
  * `CZE` (Czech)
  * `DAN` (Danish)
  * `DUT` (Dutch)
  * `ENG` (English)
  * `FIN` (Finnish)
  * `FRE` (French)
  * `GER` (German)
  * `HEB` (Hebrew)
  * `HUN` (Hungarian)
  * `ITA` (Italian)
  * `JPN` (Japanese)
  * `KOR` (Korean)
  * `LAV` (Latvian)
  * `NOR` (Norwegian)
  * `POL` (Polish)
  * `POR` (Portuguese)
  * `RUS` (Russian)
  * `SCR` (Croatian)
  * `SPA` (Spanish)
  * `SWE` (Swedish)
* Output formats:
  * `gounded`
    * result in format of the following order: Unique identifier for concept (CUI), | as delimiter, word form entry per line
  * `mrconso`
    * result in format *mrconso*, for details look into the [documentation of NCBI](https://www.ncbi.nlm.nih.gov/books/NBK9685/table/ch03.T.concept_names_and_sources_file_mr/)
    * only useable, if a filter rule file is defined, examples under [src/main/resources](src/main/resources)
  * `terms`
    * a list of word forms of all resulted entries without more information
  * `complex`
    * providing applied rules, also writes removed terms to stderr
    * result in format of the following order: term, UMLS@@, Unique identifier for concept (CUI), rule

### Usage

* Creating Jar-file by Maven:
    * Command line: `mvn package`
    * Eclipse: *Run Configurations* &rarr; *Maven Build* &rarr; *Goals* `assembly:single`
* Open a command line and type on the same line `java -jar <JuFiT-file.jar> jufit_config.json`
* We suggest updating your Java VM arguments to use at least 0.5GB of RAM, i.e., `-Xmx512M`
* More information on the UMLS can be found in the [UMLS® Reference Manual](https://www.ncbi.nlm.nih.gov/books/NBK9676/).


### Citation

## Scientific publication

Please cite [JuFiT: A Configurable Rule Engine for Filtering and Generating New Multilingual UMLS Terms](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4765630/) when using JuFiT in your research ([PMID: 26958195](https://pubmed.ncbi.nlm.nih.gov/26958195/)) ([PMCID: PMC4765630](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4765630/)).

```
@inproceedings{hellrich2015jufit,
  title={JuFiT: A configurable rule engine for filtering and generating new multilingual UMLS terms},
  author={Hellrich, Johannes and Schulz, Stefan and Buechel, Sven and Hahn, Udo},
  booktitle={AMIA Annual Symposium Proceedings},
  volume={2015},
  pages={604},
  year={2015},
  organization={American Medical Informatics Association}
}
```

## Code

* Version 1.3: Johannes Hellrich, Christina Lohr, Benjamin, Erik Fäßler, & Elena Grygorova. (2022). JULIELab/jufit: Zenodo Release (v1.3). Zenodo. https://doi.org/10.5281/zenodo.7442589

