# JuFiT - Jena UMLS Filter

This is the Jena UMLS Filter Tool (JuFiT), a rule-based open-source tool to filter and enhance entries of the Unified Medical Language System (UMLS). JuFiT uses rules similar to those in MetaMap and Casper, but extends these by adding support for multiple languages and their language-specific phenomena, e.g., German compounds. We provide rules for English, Spanish, French, German, and Dutch, yet the declarative implementation of these rules makes it possible to add support for further languages without changing JuFiT’s source code.

## Note before usage

### Input

You need files from the UMLS. After registration at [UTS](https:/uts.nlm.nih.gov), you can download the UMLS files from the [U.S. National Library of Medicine (NIH)](https://www.nlm.nih.gov/research/umls/). Unpack the ZIP file of a UMLS version (e.g. `umls-2017AA-full.zip`). Unpack the files `MRCONSO.RRF.aa` and `MRCONSO.RRF.ab` (files of Concept Names and Sources), concatenate these both to one file and unpack `MRSTY.RFF` (Semantic Types). Use these files for the input of JuFiT. 

More information on the UMLS can be found in the [UMLS® Reference Manual](https://www.ncbi.nlm.nih.gov/books/NBK9676/).

### License
BSD license (2-clause). 

Please cite [JuFiT: A Configurable Rule Engine for Filtering and Generating New Multilingual UMLS Terms](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4765630/) when using JuFiT in your research.

## Usage
```
java -jar <JuFiT-file.jar>
```
followed by (on the same line)
```
 <mrconso> <mrsty> <language> (--mrconso | --terms | --grounded | --complex) [--outFile=FILE] [--semanticType=TYPE]  ...  [--rules=JSON] [--noFilter]
 --help
 --version
Options:
--help  Show this screen
--version  Show the version number
--mrconso  MRCONSO output format (one format must be chosen)
--terms  terms only output (one format must be chosen)
--grounded  terms and CUIs output, separated with "|" (one format must be chosen)
--complex  complex output format providing applied rules, also writes removed terms to stderr (one format must be chosen)
--outFile=FILE  write output to this file instead of stdout
--semanticType=TYPE  Process only terms numbers belonging to a Semantic Type (values between T001 and T204) (repeat for multiple)
(Detailed Semantic Type values: https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt)
--rules=JSON  file with rules to use instead of defaults (probably not a good idea)
--noFilter  Do not filter output (incompatible with --mrconso as nothing would need to be done)
```
Languages must be given as three letter abbreviations, e.g., `ENG`, `GER`, `SPA`, `FRE` or `DUT`

We suggest updating your Java VM arguments to use at least 0.5GB of RAM, i.e., `-Xmx512M`

## Recent Changes:
  * 1.1 New output formats, new command line interface, change to license
  * 1.2 Semantic Group definition (from https://metamap.nlm.nih.gov/Docs/SemGroups_2018.txt)