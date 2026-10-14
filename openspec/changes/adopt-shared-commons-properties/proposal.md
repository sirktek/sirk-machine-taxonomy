# Proposal — adopt-shared-commons-properties

## Why

taxonomy-commons 3.14 (sirk-taxonomy-commons PR #2, change `shared-commons-properties`) makes
`schema:domainIncludes` opt-ins work: every domain loader now reads `common-base.ttl` and
attaches commons properties a class opts into. Until now the four opt-ins in `machine-base.ttl`
were dead triples. 3.14 also deletes `common:bookValue` / `common:taxValue` /
`common:wealthTaxValue`, marks accounting properties `common:privateSet "accounting"` and adds
the commercial terms with `common:privateSet "commercial"`.

Product owner decisions (2026-10-13, round 3): accounting opt-ins on Machine are deleted (they may
come back later, in accounting); machines carry a monthly rent, like leased real estate. Central's
monthly publishing limit is reached, so Maven Central becomes a manual step, as in commons PR #2.

GitHub issue: sirktek/sirk-machine-taxonomy#2.

## What changes

- **Bump** `no.sirktek:taxonomy-commons` 3.13 → 3.14.
- **`machine-base.ttl`:** opt `machine:Machine` into `common:monthlyRent`; delete the four
  accounting opt-ins (`ledgerAccount`, `bookValue`, `taxValue`, `depreciationGroup`) and the
  "no wealthTaxValue" comment; correct the `machine:controlChecklist` comment that says the loader
  only attaches own-namespace properties (no longer true; the redeclaration stays).
- **Tests:** `CommonPropertyOptInTest` asserts through `MachineTaxonomyService` instead of a raw
  Jena model: Machine has `monthlyRent` (common IRI, `privateSet` `commercial`) and none of
  `ledgerAccount`, `depreciationGroup`, `valuations`.
- **CI:** drop the Maven Central steps from `build_tag_and_release.yml`; new
  `publish_maven_central.yml` (`workflow_dispatch`, input `tag`), adapted from commons; README
  "Releases" section.
- **README:** "Accounting and Tax Properties" becomes "Commercial properties".

Release: next 3.x on merge. The bump and the TTL edit ship in one release: the bump alone would
attach `ledgerAccount` / `depreciationGroup` to Machine.

## Impact

- sirk-orgadmin-backend (the only consumer, via GitHub Packages): after it bumps, the Machine
  category tree shows `monthlyRent` (private, commercial) and no accounting properties. Its
  commons bump must land with or after this release (commons design "Risks").
- No Java API change in this repo.

## Not in scope

- **Consolidating `machine:controlChecklist`** with `logistics:controlChecklist`: needs a
  `common:controlChecklist` term in commons first; follow-up.
- **Other same-named redeclarations** across namespaces (width, length, height, weight, volume,
  color, material, variant, serialNumber, model, operationsAndMaintenanceManual, manufacturer,
  address): follow-up, each needs a commons term.
- Other commercial terms (`marketValue`, `marketValueDate`, `monthlyServiceCharge`) on Machine:
  not requested.
- Publishing this or any earlier release to Maven Central.

## Open questions for the product owner

None.
