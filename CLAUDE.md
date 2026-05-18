# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
This is a Java library that provides access to a standardized machine taxonomy defined in RDF-S. It extends the `sirk-taxonomy-commons` library and provides machine-specific category information with English class names and bilingual labels stored in the RDF-S file. The project follows the same architectural patterns as the sibling `sirk-furniture-taxonomy` and `sirk-logistics-taxonomy` projects.

## Common Commands

### Build and Test
```bash
# Using system Maven
mvn compile          # Compile source code
mvn test             # Run unit tests
mvn package          # Build JAR artifact
mvn install          # Install to local Maven repository
mvn clean            # Clean build artifacts

# Using Maven wrapper (recommended)
./mvnw compile       # Compile source code
./mvnw test          # Run unit tests
./mvnw package       # Build JAR artifact
./mvnw install       # Install to local Maven repository
./mvnw clean         # Clean build artifacts
```

### Code Quality
```bash
./mvnw clean -U -Dpmd.printFailingErrors=true pmd:check    # PMD static analysis
./mvnw clean -DskipITs=false -U verify                     # Full build with integration tests
```

### Running Single Tests
```bash
mvn test -Dtest=TaxonomyServiceTest
mvn test -Dtest=TaxonomyServiceTest#shouldLoadBaseTaxonomy
```

## Architecture

### Layered Structure
- **Commons Dependency**: Extends `sirk-taxonomy-commons` for shared model, loader, and service
- **Loader Layer** (`loader/`): `MachineRdfsTaxonomyLoader` extends abstract base loader
- **Service Layer**: `MachineTaxonomyService` extends `TaxonomyService`
- **Property Types** (`model/`): `MachinePropertyDefinition` with machine-specific type detection

### Data Flow
1. RDF-S Turtle file (`machine-base.ttl`) defines machine hierarchy with bilingual labels
2. `MachineRdfsTaxonomyLoader` extends base loader and specifies namespace + resource path
3. Base loader (from commons) parses RDF-S using Apache Jena
4. `MachineTaxonomyService` provides caching and high-level API
5. Clients consume via simple Java API (English class names)

### Key Files
- `/src/main/resources/taxonomy/machine-base.ttl` - RDF-S taxonomy definition
  - Defines `Machine` root class with level-1 domain groupings (`WoodshopMachine`, `TextileMachine`)
  - Initial leaf classes: `HandheldTool`, `CNCMachine` (`TwoAxisCNCMachine`, `FiveAxisCNCMachine`), `HydraulicSaw`, `SewingMachine`, `FabricCuttingMachine`
  - Enumerations: `PowerSource`, `OperationalStatus`
  - Global classes: `Manufacturer`, `Model`, `Resource`
- `MachineTaxonomyService.java` - Extends base service with machine loader
- `MachineRdfsTaxonomyLoader.java` - Specifies machine namespace and resource path
- `MachinePropertyDefinition.java` - Machine-specific property type detection (incl. kW/V/RPM)

## Development Notes

### Technology Stack
- Java 17 (CI uses Java 21)
- Apache Maven 3.9.9+ (includes Maven wrapper)
- **sirk-taxonomy-commons** for shared base classes
- Apache Jena 5.5.0 (via commons dependency)
- Lombok 1.18.36 for code generation
- JUnit Jupiter 5.11.3 for testing

### Threading and Caching
- Uses thread-safe singleton pattern for taxonomy loading
- Double-checked locking for cache initialization
- Cache invalidation via `reloadBaseTaxonomy()`

### Language Support
- English URIs and class names for API compatibility (`http://taxonomy.sirktek.no/machine#`)
- Bilingual labels (English/Norwegian) stored in RDF-S file for frontend consumption
- Frontend handles translation - Java library only provides English class names

### Extending the Taxonomy
- Additional level-1 domain groupings (e.g. `Computer`, `LabEquipment`) should be added as direct subclasses of `Machine` next to `WoodshopMachine` and `TextileMachine`.
- New leaf categories belong under the appropriate level-1 grouping.
- When introducing new property types (e.g. new units), add the enum value to `MachinePropertyDefinition.PropertyType` and the detection logic in `getPropertyType`, then update the enum count assertion in `PropertyDefinitionTest`.

### CI/CD Pipeline
- **Pull Requests**: PMD analysis + full test suite
- **Main Branch**: Auto-build, version tagging (2.{build_number}), deploy to Maven Central
- **Maven Wrapper**: Available for development (./mvnw, ./mvnw.cmd)
- **Versioning**: Uses `${revision}` property for dynamic versioning

### Related Projects
This project is part of the Sirktek taxonomy suite:
- **sirk-furniture-taxonomy**: Furniture classification taxonomy
- **sirk-logistics-taxonomy**: Logistics and location taxonomy
- **sirk-machine-taxonomy**: Machine and equipment taxonomy (this project)

All projects share the same architectural patterns, technology stack, and development practices.
