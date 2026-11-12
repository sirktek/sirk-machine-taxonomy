## ADDED Requirements

### Requirement: Machine carries the commons expected service life, new price and emission

The machine taxonomy SHALL opt `machine:Machine` into `common:expectedServiceLife`,
`common:newPriceExVAT` and `common:emissionPerUnit`, and SHALL NOT declare machine-specific
properties of those names.

#### Scenario: Service life, new price and emission on Machine
- **WHEN** `MachineTaxonomyService` loads the taxonomy and the Machine category is read
- **THEN** it has one property `expectedServiceLife` with `uri` `http://taxonomy.sirktek.no/common#expectedServiceLife`, `rangeType` `http://www.w3.org/2001/XMLSchema#integer` and no `privateSet`, one `newPriceExVAT` with its `common#` IRI, `rangeType` `http://www.w3.org/2001/XMLSchema#decimal` and `privateSet` `commercial`, and one `emissionPerUnit` with its `common#` IRI, `rangeType` `http://taxonomy.sirktek.no/common#EmissionEntry` and no `privateSet`
