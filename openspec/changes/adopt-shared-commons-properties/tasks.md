# Tasks — adopt-shared-commons-properties

One group, one implementer in a fresh context with this file, design.md and
`specs/machine-common-properties/spec.md`. Touch only the listed files, run only the listed
tests, tick the tasks, append a short `## Handoff — Group 1` note (what changed, what is
unverified) to `handoff.md` in this directory, and stop when the tests pass. Maven through `mvnq`
(`~/.local/bin/mvnq`, same arguments as `./mvnw`); never `clean`. No Java code specific to a
taxonomy class or property.

Suggested model: sonnet (design pins every name, line and workflow step).

### Group 0: Issue and branch

- [x] 0.1 Open a GitHub issue in sirktek/sirk-machine-taxonomy ("Adopt shared commons
      properties: monthlyRent on Machine, drop accounting opt-ins, manual Maven Central") linking
      sirktek/sirk-taxonomy-commons#1; put its number in proposal.md "GitHub issue".
- [x] 0.2 Branch `feature/#<id>-adopt-shared-commons-properties` from `main`.

### Group 1: Commons 3.14, monthlyRent, manual Central

Design D1–D4.

Files: `pom.xml`, `src/main/resources/taxonomy/machine-base.ttl`,
`src/test/java/no/sirktek/taxonomy/CommonPropertyOptInTest.java`,
`src/test/java/no/sirktek/taxonomy/TaxonomyServiceTest.java` and
`src/test/java/no/sirktek/taxonomy/model/PropertyDefinitionTest.java` (only if the bump breaks
them), `.github/workflows/build_tag_and_release.yml`,
new `.github/workflows/publish_maven_central.yml`, `README.md`.
Tests: `mvnq test -Dtest='CommonPropertyOptInTest,ControlChecklistPropertyTest'`

- [x] 1.1 Test first: rewrite `CommonPropertyOptInTest` on `MachineTaxonomyService` (D2:
      `machineOptsIntoMonthlyRent`, `machineHasNoAccountingProperties`; delete the two raw-Jena
      tests). Bump `taxonomy-commons` to `3.14` in `pom.xml`. Run: `monthlyRent` fails and the
      accounting test fails on `ledgerAccount` / `depreciationGroup`.
- [x] 1.2 `machine-base.ttl`: replace the accounting opt-ins and the wealthTaxValue comment with
      the D1 block; rewrite the `machine:controlChecklist` comment (D1). Group tests pass.
- [x] 1.3 CI (D3): remove the Central steps from `build_tag_and_release.yml` and fix the
      SNAPSHOT message; add `publish_maven_central.yml`. Diff both against
      `/home/mkf/dev/sirk-taxonomy-commons/.github/workflows/` and keep only the D3 differences;
      `actionlint` if installed.
- [x] 1.4 README: "Commercial properties" (D4) and "Releases" (D3).
- [x] 1.5 `mvnq test` once (full suite).

## Not in scope

`machine:controlChecklist` consolidation and other same-named redeclarations (need commons
terms first); see proposal.md "Not in scope". No commit, push or PR inside the group unless asked.
