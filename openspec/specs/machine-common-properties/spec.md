# machine-common-properties Specification

## Purpose
TBD - created by archiving change adopt-shared-commons-properties. Update Purpose after archive.

## Requirements

### Requirement: Machine carries the commercial monthly rent

The machine taxonomy SHALL opt `machine:Machine` into `common:monthlyRentExVAT`, so that the loaded
Machine category has a property `monthlyRentExVAT` whose `uri` is the commons IRI and whose
`privateSet` is `commercial`, and no property `monthlyRent`.

#### Scenario: Monthly rent on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has a property `monthlyRentExVAT` with `uri` `http://taxonomy.sirktek.no/common#monthlyRentExVAT`, `rangeType` `http://www.w3.org/2001/XMLSchema#decimal` and `privateSet` `commercial`, and no property `monthlyRent`

### Requirement: Maven Central publishing is manual

A merge to `main` SHALL tag `3.<run number>` and publish to GitHub Packages only. Publishing a
release tag to Maven Central SHALL be a separate `workflow_dispatch` workflow taking the tag as
input.

#### Scenario: Merge to main
- **WHEN** a commit is pushed to `main`
- **THEN** `build_tag_and_release.yml` tags and deploys to GitHub Packages and runs no Maven Central step

#### Scenario: Manual Central release
- **WHEN** "Publish release to Maven Central" is run with input `tag` `3.17`
- **THEN** the workflow checks out `3.17`, resolves taxonomy-commons from GitHub Packages, sets version `3.17` and deploys with the `deployment` profile to Maven Central

### Requirement: Machine carries the commons age properties

The machine taxonomy SHALL opt `machine:Machine` into `common:yearOfManufacture` and
`common:installationYear`, and SHALL NOT declare `machine:yearOfManufacture`.

#### Scenario: Age properties on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has one property `yearOfManufacture` with `uri` `http://taxonomy.sirktek.no/common#yearOfManufacture` and one `installationYear` with `uri` `http://taxonomy.sirktek.no/common#installationYear`, both with `rangeType` `http://www.w3.org/2001/XMLSchema#integer` and no `privateSet`

### Requirement: Machine carries the accounting set

The machine taxonomy SHALL opt `machine:Machine` into `common:ledgerAccount`,
`common:depreciationGroup` and `common:valuations`, each loaded with `privateSet` `accounting`, and
SHALL NOT carry `bookValue`, `taxValue` or `wealthTaxValue`.

#### Scenario: Accounting on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has `ledgerAccount`, `depreciationGroup` and `valuations` once each with their `common#` IRIs and `privateSet` `accounting`, and none of `bookValue`, `taxValue`, `wealthTaxValue`

### Requirement: Machine property typing has no year heuristic

`MachinePropertyDefinition.getPropertyType` SHALL type an `xsd:integer` property as `INTEGER`
whatever its name, and `MachinePropertyDefinition.PropertyType` SHALL have no `YEAR` constant.

#### Scenario: A property named yearOfManufacture
- **WHEN** `getPropertyType` is called for a property named `yearOfManufacture` with range `xsd:integer`
- **THEN** it returns `INTEGER`
