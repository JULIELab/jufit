# JuFiT - Jena UMLS Filter

This is the Jena UMLS Filter Tool (JuFiT), a rule-based open-source tool to filter and enhance entries of the Unified Medical Language System (UMLS). JuFiT uses rules similar to those in MetaMap and Casper, but extends these by adding support for multiple languages and their language-specific phenomena, e.g., German compounds. We provide rules for English, Spanish, French, German, and Dutch, yet the declarative implementation of these rules makes it possible to add support for further languages without changing JuFiT’s source code.

## Note before usage

### Input

You need files from the UMLS. After registration at [UTS] (https:/uts.nlm.nih.gov), you can download the UMLS files from the [U.S. National Library of Medicine (NIH)] (https://www.nlm.nih.gov/research/umls/). Unpack the ZIP file of a UMLS version (e.g. `umls-2017AA-full.zip`). Unpack the files `MRCONSO.RRF.aa` and `MRCONSO.RRF.ab` (files of Concept Names and Sources), concatenate these both to one file and unpack `MRSTY.RFF` (Semantic Types). Use this files for the input of JuFiT.

### Gazetteer format

### Further information
  * [UMLS® Reference Manual (https://www.ncbi.nlm.nih.gov/books/NBK9676/)]
  * publication [JuFiT: A Configurable Rule Engine for Filtering and Generating New Multilingual UMLS Terms (https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4765630/)]

## Usage
```
java -jar <JuFiT-JAR> <MODE> <PARAMETERS>
```

### Additional hit, update your Java VM arguments with `-Xmx512M`

Need at least 2 arguments, got 
* 1: MRCONSO file
* 2: MRSTY file
* 3: language to process, 3 letter code (`ENG`, `GER`, `SPA`, `FRE`, `DUT`)
* 4: optional json rule file used instead of the default configuration

### Modes

* `mr` - filter UMLS, output MRCONSO
* `ga` - generate gazetteer file (idiosyncratic format), without any filtering 
* `gf` - generate gazetteer file (idiosyncratic format), applying filter


### Example Usage
```
ga MRCONSO.RRF MRSTY.RRF GER
```
### Parameters
```
Depend on mode, call a mode to see its parameters as a help message.
```

