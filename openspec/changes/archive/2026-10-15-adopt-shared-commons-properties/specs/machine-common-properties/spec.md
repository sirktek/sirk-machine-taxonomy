## ADDED Requirements

### Requirement: Machine carries the commercial monthly rent

The machine taxonomy SHALL opt `machine:Machine` into `common:monthlyRent`, so that the loaded
Machine category has a property `monthlyRent` whose `uri` is the commons IRI and whose
`privateSet` is `commercial`.

#### Scenario: Monthly rent on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has a property `monthlyRent` with `uri` `http://taxonomy.sirktek.no/common#monthlyRent`, `rangeType` `http://www.w3.org/2001/XMLSchema#decimal` and `privateSet` `commercial`

### Requirement: Machine carries no accounting properties

The machine taxonomy SHALL NOT opt any machine class into commons accounting properties.

#### Scenario: No accounting on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has none of the properties `ledgerAccount`, `depreciationGroup`, `valuations`, `bookValue`, `taxValue`, `wealthTaxValue`

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
