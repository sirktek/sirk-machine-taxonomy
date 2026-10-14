# Design — adopt-shared-commons-properties

## Precedent

- **Opt-ins:** a domain opts a class into a commons property with one
  `common:<p> schema:domainIncludes machine:<Class> .` triple in its own TTL, in the
  "Cross-cutting property domain extensions" section of `machine-base.ttl` (lines 504-521 today).
  The property, its range, labels and `privateSet` live in commons. This change follows it: one
  triple added, four removed.
- **Tests through the service:** `ControlChecklistPropertyTest` asserts properties through
  `MachineTaxonomyService.getCategoryByClassName("Machine").properties()`.
  `CommonPropertyOptInTest` checked a raw Jena model only because the loader did not attach
  commons properties; with 3.14 it follows `ControlChecklistPropertyTest`.
- **CI:** commons PR #2 (commit `7edfcc7`): Central steps removed from
  `build_tag_and_release.yml`, `publish_maven_central.yml` on `workflow_dispatch` with input
  `tag`, README "Releases". Copied, with one adaptation (D3).

No departure.

## Inventory of everything new or removed

| Kind | Item | Reader |
| --- | --- | --- |
| pom | `taxonomy-commons` 3.13 → 3.14 | build |
| TTL | `common:monthlyRent schema:domainIncludes machine:Machine` | loader, test |
| TTL removed | opt-ins `common:ledgerAccount`, `bookValue`, `taxValue`, `depreciationGroup` → Machine; "no wealthTaxValue" comment | — |
| Workflow | `.github/workflows/publish_maven_central.yml` | run by hand |
| Workflow removed | Central steps in `build_tag_and_release.yml` | — |
| Docs | README "Releases", "Commercial properties" | — |

No new class, dependency, config or Java constant.

## Inventory: what Machine gains and loses

`common:` opt-ins in `machine-base.ttl` today are only the four accounting ones (lines 518-521);
there is no `wealthTaxValue` opt-in (a comment explains why). `machine:model` /
`machine:manufacturer` are machine properties whose range is `common:Model` /
`common:Manufacturer`; commons declares no individuals of those classes, so their `enumValues`
stay empty and their type mapping is unchanged. Commons properties with a commons
domain (`address`, `emissionPerUnit` on Model, …) attach only to commons classes, which the
machine tree excludes.

| Machine with | Gains from commons |
| --- | --- |
| commons 3.13 (today) | nothing (loader ignored opt-ins) |
| commons 3.14, TTL unchanged | `ledgerAccount`, `depreciationGroup` (privateSet `accounting`, ten saldogrupper); `bookValue`, `taxValue` inert (deleted in commons) |
| commons 3.14, this change | `monthlyRent` only (`uri` `common#monthlyRent`, `xsd:decimal`, privateSet `commercial`) |

Subclasses inherit as for any Machine property. `MachinePropertyDefinition.getPropertyType`
maps `monthlyRent` (`xsd:decimal`, no name rule matches) to `DECIMAL`; no change needed.
No machine property is named `monthlyRent`, so no key clash (commons design D1 constraint).

## Decisions

### D1 — TTL

Replace the block after the "Cross-cutting property domain extensions" header (the
"No wealthTaxValue" comment, the accounting comment and the four triples) with:

```turtle
# Commercial terms (private set "commercial"): a machine let to a holder
# (leietaker) carries its monthly rent, as leased real estate does.
common:monthlyRent schema:domainIncludes machine:Machine .
```

Rewrite the comment above `machine:controlChecklist` (lines 334-338) to keep the contract and drop
the stale reason: same local name and value contract as `logistics:controlChecklist`; declared
in the machine namespace until commons has a shared `controlChecklist` term. The declaration
itself is unchanged.

### D2 — Tests

`CommonPropertyOptInTest` is rewritten on `MachineTaxonomyService` (the
`ControlChecklistPropertyTest` helper pattern):

- `machineOptsIntoMonthlyRent`: Machine has a property `monthlyRent` with `uri`
  `http://taxonomy.sirktek.no/common#monthlyRent`, `rangeType` `xsd:decimal`, `privateSet`
  `commercial`.
- `machineHasNoAccountingProperties`: Machine has none of `ledgerAccount`, `depreciationGroup`,
  `valuations`, `bookValue`, `taxValue`, `wealthTaxValue`.

The two raw-Jena tests (`wealthTaxValueIsNotStoredForOperatingAssets`,
`assetClassesOptIntoAccountingProperties`) are deleted. `TaxonomyServiceTest` /
`PropertyDefinitionTest` change only if a property count or builder call breaks on the bump
(`PropertyDefinition` gained `privateSet`; the tests use the builder).

### D3 — CI

- `build_tag_and_release.yml`: delete the "Publish to Maven Central" comment, the second
  `setup-java` (server `central`) and the Central deploy step; the SNAPSHOT guard message becomes
  "Cannot release a SNAPSHOT version" (as commons). The rest stays, including its GitHub-server
  `setup-java`, which commons does not have (commons has no GitHub Packages dependency).
- `publish_maven_central.yml`: commons' file, adapted because this repo resolves
  taxonomy-commons from GitHub Packages and `setup-java` writes one server into settings.xml:
  1. checkout `ref: ${{ inputs.tag }}`;
  2. `setup-java` with `server-id: github` (as the release workflow);
  3. "Resolve dependencies from GitHub Packages": `./mvnw -U compile --batch-mode
     --no-transfer-progress` with `GITHUB_ACTOR` / `GITHUB_TOKEN`;
  4. `setup-java` with `server-id: central` and GPG (commons' step verbatim; the local
     repository keeps taxonomy-commons);
  5. "Set Release Version" and "Publish to Maven Central" as commons.
  Header comment and the input description (`e.g. 3.17`) as commons; `permissions: contents:
  read, packages: read`.
- README "Releases": commons' text, naming sirk-orgadmin-backend as the GitHub Packages consumer,
  plus: a Central release needs the taxonomy-commons version it depends on published to Central
  first.

### D4 — README

"Accounting and Tax Properties" becomes "Commercial properties": Machine opts into
`common:monthlyRent` (Månedsleie (eks. mva), private set `commercial`); definitions in the
taxonomy-commons README; `CommonPropertyOptInTest` pins it through the loader.

## Risks

- Shipping the bump without the TTL edit would expose accounting on Machine in orgadmin's tree:
  one group, one PR, one release.
- `publish_maven_central.yml` cannot be exercised before merge; a dry check is `actionlint` if
  installed, else YAML review against commons.
