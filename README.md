# Sirktek Machine Taxonomy Library

[![Maven Central Version](https://img.shields.io/maven-central/v/no.sirktek/machine-taxonomy)](https://search.maven.org/search?q=g:%22no.sirktek%22%20AND%20a:%22machine-taxonomy%22)

![Build, tag and release Java project ](https://github.com/sirktek/sirk-machine-taxonomy/actions/workflows/build_tag_and_release.yml/badge.svg)


A Java library that provides access to a standardized machine [taxonomy defined in RDF-S](src/main/resources/taxonomy/machine-base.ttl) (Resource Description Framework Schema) with Turtle serialization format.

## Features

- **RDF-S based taxonomy**: Formal ontology structure using W3C standards
- **English URIs**: Standardized English class names for international compatibility
- **Bilingual labels**: Norwegian and English labels stored in RDF-S for frontend use
- **Java API**: Easy-to-use service for loading and querying taxonomy
- **Caching**: Efficient in-memory caching of parsed taxonomy
- **Apache Jena**: Robust RDF processing using industry-standard library

## Quick Start

### Maven Dependency

```xml
<dependency>
    <groupId>no.sirktek</groupId>
    <artifactId>machine-taxonomy</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```

### Usage

```java
// Create service instance
MachineTaxonomyService taxonomyService = new MachineTaxonomyService();

// Load the complete taxonomy tree
TaxonomyTree taxonomy = taxonomyService.loadBaseTaxonomy();

// Find category by English class name
Optional<CategoryInfo> cnc = taxonomyService.getCategoryByClassName("CNCMachine");
Optional<CategoryInfo> sewing = taxonomyService.getCategoryByClassName("SewingMachine");

// Check if English class name exists in taxonomy
boolean exists = taxonomyService.isBaseTaxonomyClass("FiveAxisCNCMachine"); // -> true

// Get taxonomy statistics
TaxonomyService.TaxonomyStats stats = taxonomyService.getStats();
System.out.println("Total categories: " + stats.totalCategories());
System.out.println("Root categories: " + stats.rootCategories());

// Access bilingual labels from CategoryInfo
if (sewing.isPresent()) {
    CategoryInfo info = sewing.get();
    String englishName = info.englishName();   // "Sewing Machine"
    String norwegianName = info.norwegianName(); // "Symaskin"
}
```

## Taxonomy Structure

The machine taxonomy is organized hierarchically and grouped by domain so additional
domains (e.g. computers, lab equipment) can be added as new level-1 siblings later:

```
Machine
├── Woodshop Machine
│   ├── Handheld Tool
│   ├── CNC Machine
│   │   ├── 2-Axis CNC Machine
│   │   └── 5-Axis CNC Machine
│   └── Hydraulic Saw
└── Textile Machine
    ├── Sewing Machine
    └── Fabric Cutting Machine
```

Additional global categories:
- **Manufacturer**: Machine suppliers / producers
- **Model**: Specific machine models
- **Resource**: Materials or consumables

Enumerations:
- **PowerSource**: Electric, Hydraulic, Pneumatic, BatteryPowered, Manual
- **OperationalStatus**: Operational, NeedsService, Broken, Retired

## RDF-S Schema

The taxonomy is defined using RDF-S in Turtle format with:

- **Classes**: Machine categories with `rdfs:subClassOf` relationships
- **Properties**: Attributes like dimensions, power, voltage, operating hours, emissions
- **Labels**: Bilingual labels in English (`@en`) and Norwegian (`@no`)
- **Domains/Ranges**: Proper typing for all properties

## Architecture

- **Model Layer**: `CategoryInfo`, `TaxonomyTree`, `PropertyDefinition` POJOs (from commons)
- **Loader Layer**: `MachineRdfsTaxonomyLoader` extending the commons base loader (uses Apache Jena)
- **Service Layer**: `MachineTaxonomyService` providing a cached high-level API

## Testing

Run tests with:
```bash
mvn test
```

The test suite verifies:
- RDF-S parsing and taxonomy loading
- Category lookup by English class names
- Property definition type detection
- Taxonomy statistics and caching
- All PropertyDefinition functionality

## Related Projects

This library is part of the Sirktek taxonomy suite:
- **sirk-furniture-taxonomy**: Furniture classification taxonomy
- **sirk-logistics-taxonomy**: Logistics and location taxonomy
- **sirk-machine-taxonomy**: Machine and equipment taxonomy (this project)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
